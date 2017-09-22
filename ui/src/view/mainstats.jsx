import _ from 'lodash'
import React from 'react'
import { connect } from 'react-redux'
import { Container, Table, Grid } from 'semantic-ui-react'
import linal from 'linear-algebra'
import {
	XYPlot, XAxis, YAxis, HorizontalGridLines
	, LineSeries
	, MarkSeries
} from 'react-vis'

const Matrix = linal().Matrix

const color = {
	"setosa"		: "#8884d8",
	"virginica"		: "#82ca9d",
	"versicolor"	: "#f5f5f5"
}

function nextColor(col,amt) {
	var usePound = false;
	if ( col[0] == "#" ) {
		col = col.slice(1);
		usePound = true;
	}

	var num = parseInt(col,16);

	var r = (num >> 16) + amt;

	if ( r > 255 ) r = 255;
	else if  (r < 0) r = 0;

	var b = ((num >> 8) & 0x00FF) + amt;

	if ( b > 255 ) b = 255;
	else if  (b < 0) b = 0;

	var g = (num & 0x0000FF) + amt;

	if ( g > 255 ) g = 255;
	else if  ( g < 0 ) g = 0;

	return (usePound?"#":"") + (g | (b << 8) | (r << 16)).toString(16);
}

function gendata(n) {
	const items = []
	for (var i = 0, t = 0; i < 100; ++i, t += 0.1) {
		items.push({ x: t, y: Math.sin(t) / 3 })
	}
	return { items: items } 
}

function linreg(ds) {

	const data = gendata(100)

	const threshold = 0.001
	const X = xmat(data)
	const y = yvec(data)
	var alpha = 0.01
	var theta = new Matrix([ [0], [0] ])

	function yvec(data) {
		const d = data.items.map(point => [ point.y ])
		return new Matrix(d)
	}

	function xmat(data) {
		const d = data.items.map(point => [ 1, point.x ])
		return new Matrix(d)
	}

	function gradient(theta, X, y) {
		return y.minus(X.dot(theta)).trans().dot(X).trans().mulEach(-1)
	}

	function len(v) {
		return v.trans().dot(v).data[0][0]
	}

	function line(theta) {
		const max = 100
		return [
			{ x: 0, y: theta.data[0][0] }
			, { x: max, y: theta.data[0][0] + max * theta.data[1][0] }
		]
	}

	var gradlines = []
	var grad = []

	function gd(theta, _X, _y, threshold) {
		const X = new Matrix(_X.data.map(row => [ row[0], row[1] / 100 ]))
		const y = new Matrix(_y.data.map(row => [ row[0] / 100 ]))

		var g = gradient(theta, X, y)

		grad.push([ theta.data[0][0], theta.data[1][0] ])
		gradlines.push(line(theta))

		var i = 0
		while (len(g) > threshold && i < 40) {
			theta = theta.minus(g.mulEach(alpha))

			grad.push([ theta.data[0][0], theta.data[1][0] ])
			gradlines.push(line(theta))

			g = gradient(theta, X, y)
			i += 1
		}
	}

	gd(theta, X, y, threshold)

	return {
		points: data.items
		, X: X
		, y: y
		, theta: theta
		, grad: new Matrix(grad)
		, gradgraph: grad.map(row => ({ x: row[0], y: row[1] }))
		, gradlines: gradlines
	}
}

class MatrixViewer extends React.Component {

	render() {
		const mat = this.props.mat
		return (
			<Container fluid >
				<Table>
					<Table.Body>
						{mat.data.map( (row, idx) => 
							<Table.Row key={idx}>
								{row.map( (col, idx) =>
									<Table.Cell key={idx}>{col}</Table.Cell>
								)}
							</Table.Row>
						)}
					</Table.Body>
				</Table>
			</Container>
		)
	}

}

class MainStats extends React.Component {
	render() {
		const ds = linreg(ds)
		const last = ds.gradlines[ds.gradlines.length - 1]
		return (
			<Container fluid >
				<Grid padded >
					<Grid.Column width={3}><MatrixViewer mat={ds.X} /></Grid.Column>
					<Grid.Column width={3}><MatrixViewer mat={ds.y} /></Grid.Column>
					<Grid.Column width={3}><MatrixViewer mat={ds.theta} /></Grid.Column>
					<Grid.Column width={16} style={ { height: "500px" } } >
						<XYPlot width={900} height={600} style={{ width: "100%" }} >
							<HorizontalGridLines />
							{ds.gradlines.map( (line, idx) =>
								<LineSeries key={idx} data={line} stroke={nextColor("#79C7E3", -idx)} />
							)}
							<LineSeries data={last} stroke={"blue"} />
							<MarkSeries data={ds.points} stroke="#1A3177" fill="#1A3177" />
							<XAxis />
							<YAxis />
						</XYPlot>
					</Grid.Column>
					{ /* <Grid.Column width={3}><MatrixViewer mat={ds.grad} /></Grid.Column> */ }
				</Grid>
			</Container>
		)
	}

}

function mapper(state, props) {
	return {
		ds: {
			iris: state.ds.iris
		}
	}
}

export default connect(mapper)(MainStats)
