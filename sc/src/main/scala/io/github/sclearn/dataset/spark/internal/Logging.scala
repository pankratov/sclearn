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

package io.github.sclearn.dataset.spark.internal


trait Logging {

  // Method to get the logger name for this object
  protected def logName = {
    // Ignore trailing $'s in the class names for Scala objects
    this.getClass.getName.stripSuffix("$")
  }

  // Method to get or create the logger for this object
  protected def log: java.util.logging.Logger = null

  // Log methods that take only a String
  protected def logInfo(msg: => String) {
  }

  protected def logDebug(msg: => String) {
  }

  protected def logTrace(msg: => String) {
  }

  protected def logWarning(msg: => String) {
  }

  protected def logError(msg: => String) {
  }

  // Log methods that take Throwables (Exceptions/Errors) too
  protected def logInfo(msg: => String, throwable: Throwable) {
  }

  protected def logDebug(msg: => String, throwable: Throwable) {
  }

  protected def logTrace(msg: => String, throwable: Throwable) {
  }

  protected def logWarning(msg: => String, throwable: Throwable) {
  }

  protected def logError(msg: => String, throwable: Throwable) {
  }

  protected def isTraceEnabled(): Boolean = {
	  false
  }

  protected def initializeLogIfNecessary(isInterpreter: Boolean): Unit = {
  }

  protected def initializeLogIfNecessary(isInterpreter: Boolean, silent: Boolean = false): Boolean = {
    false
  }

  private def initializeLogging(isInterpreter: Boolean, silent: Boolean): Unit = {
  }
}
