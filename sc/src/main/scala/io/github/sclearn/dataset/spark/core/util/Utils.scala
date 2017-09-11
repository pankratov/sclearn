/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.spark.util

import java.io._
import java.lang.management.{LockInfo, ManagementFactory, MonitorInfo, ThreadInfo}
import java.math.{MathContext, RoundingMode}
import java.net._
import java.nio.ByteBuffer
import java.nio.channels.{Channels, FileChannel}
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}
import java.util.{Locale, Properties, Random, UUID}
import java.util.concurrent._
import java.util.concurrent.atomic.AtomicBoolean
import java.util.zip.GZIPInputStream
import javax.net.ssl.HttpsURLConnection

import scala.annotation.tailrec
import scala.collection.JavaConverters._
import scala.collection.Map
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.reflect.ClassTag
import scala.util.Try
import scala.util.control.{ControlThrowable, NonFatal}
import scala.util.matching.Regex

/**
 * Various utility methods used by Spark.
 */
private[spark] object Utils {

  /** Whether we have warned about plan string truncation yet. */
  private val truncationWarningPrinted = new AtomicBoolean(false)

  /**
   * Format a sequence with semantics similar to calling .mkString(). Any elements beyond
   * maxNumToStringFields will be dropped and replaced by a "... N more fields" placeholder.
   *
   * @return the trimmed and formatted string.
   */
  def truncatedString[T](
      seq: Seq[T],
      start: String,
      sep: String,
      end: String,
      maxNumFields: Int = 25): String = {
    if (seq.length > maxNumFields) {
      if (truncationWarningPrinted.compareAndSet(false, true)) {
      }
      val numFields = math.max(0, maxNumFields - 1)
      seq.take(numFields).mkString(
        start, sep, sep + "... " + (seq.length - numFields) + " more fields" + end)
    } else {
      seq.mkString(start, sep, end)
    }
  }

  /** Shorthand for calling truncatedString() without start or end strings. */
  def truncatedString[T](seq: Seq[T], sep: String): String = truncatedString(seq, "", sep, "")

  /**
   * Get the Context ClassLoader on this thread or, if not present, the ClassLoader that
   * loaded Spark.
   *
   * This should be used whenever passing a ClassLoader to Class.ForName or finding the currently
   * active loader when setting up ClassLoader delegation chains.
   */
  def getContextOrSparkClassLoader: ClassLoader =
    Option(Thread.currentThread().getContextClassLoader).getOrElse(getClass.getClassLoader)

  /** Determines whether the provided class is loadable in the current thread. */
  def classIsLoadable(clazz: String): Boolean = {
    // scalastyle:off classforname
    Try { Class.forName(clazz, false, getContextOrSparkClassLoader) }.isSuccess
    // scalastyle:on classforname
  }

  // scalastyle:off classforname
  /** Preferred alternative to Class.forName(className) */
  def classForName(className: String): Class[_] = {
    Class.forName(className, true, getContextOrSparkClassLoader)
    // scalastyle:on classforname
  }

  /**
   * NaN-safe version of `java.lang.Double.compare()` which allows NaN values to be compared
   * according to semantics where NaN == NaN and NaN is greater than any non-NaN double.
   */
  def nanSafeCompareDoubles(x: Double, y: Double): Int = {
    val xIsNan: Boolean = java.lang.Double.isNaN(x)
    val yIsNan: Boolean = java.lang.Double.isNaN(y)
    if ((xIsNan && yIsNan) || (x == y)) 0
    else if (xIsNan) 1
    else if (yIsNan) -1
    else if (x > y) 1
    else -1
  }

  /**
   * NaN-safe version of `java.lang.Float.compare()` which allows NaN values to be compared
   * according to semantics where NaN == NaN and NaN is greater than any non-NaN float.
   */
  def nanSafeCompareFloats(x: Float, y: Float): Int = {
    val xIsNan: Boolean = java.lang.Float.isNaN(x)
    val yIsNan: Boolean = java.lang.Float.isNaN(y)
    if ((xIsNan && yIsNan) || (x == y)) 0
    else if (xIsNan) 1
    else if (yIsNan) -1
    else if (x > y) 1
    else -1
  }
}

