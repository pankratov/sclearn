import _ from 'lodash'
import React from 'react'
import { connect } from 'react-redux'
import { Container, Table, Grid } from 'semantic-ui-react'
import linal from 'linear-algebra'
import JsonV from 'react-json-viewer'
import {
	ResponsiveContainer,
	XAxis, YAxis,
	CartesianGrid,
	Legend, Tooltip,
	LineChart, Line,
	ScatterChart, Scatter
} from 'recharts'

const Matrix = linal().Matrix

const color = {
	"setosa"		: "#8884d8",
	"virginica"		: "#82ca9d",
	"versicolor"	: "#f5f5f5"
}

function gendata(n) {
	const items = _.range(100).map( x => ({ x: x, y: x * 2 }) )
	return { items: items } 
}

function linreg(ds) {

	const data = { items: [
		{ x: 0, y: 0 }
		, { x: 1, y: 2 }
		, { x: 2, y: 4 }
		, { x: 3, y: 6 }
		, { x: 4, y: 8 }
		, { x: 5, y: 10 }
	] }

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
		return [
			{ x: 0, y: theta[0] }
			, { x: 100, y: theta[0] + 100 * theta[1] }
		]
	}

	var gradlines = []
	var grad = []

	function gd(theta, X, y, threshold) {
		var g = gradient(theta, X, y)

		grad.push([ theta.data[0][0], theta.data[1][0] ])
		gradlines.push(line(theta))

		var i = 0
		while (len(g) > threshold) {
			theta = theta.minus(g.mulEach(alpha))

			grad.push([ theta.data[0][0], theta.data[1][0] ])
			gradlines.push(line(theta))

			g = gradient(theta, X, y)
			i += 1
		}
	}

	gd(theta, X, y, threshold)

	return {
		X: X
		, y: y
		, theta: theta
		, grad: new Matrix(grad)
		, gradgraph: grad.map(row => ({ x: row[0], y: row[1] }))
	}
}

class MatrixViewer extends React.Component {
	render() {
		console.log(this.props)
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
		const ds = gendata(10)
		const data = linreg(ds)
		return (
			<Container fluid >
				<Grid padded >
					<Grid.Column width={3}><MatrixViewer mat={data.X} /></Grid.Column>
					<Grid.Column width={3}><MatrixViewer mat={data.y} /></Grid.Column>
					<Grid.Column width={3}><MatrixViewer mat={data.theta} /></Grid.Column>
					<Grid.Column width={4} style={ { height: "500px" } } >
						<ScatterChart
								data={data.gradgraph}
								width={600} height={200}
								margin={{top: 20, right: 30, left: 20, bottom: 5}}
							>
							<Scatter key="x" fill="green" />
							<CartesianGrid strokeDasharray="3 3" />
							<YAxis dataKey="x" type="number" />
							<XAxis dataKey="y" type="number" />
							<Legend />
							<Tooltip />
						</ScatterChart>
					</Grid.Column>
					<Grid.Column width={16} style={ { height: "500px" } } >
						<ResponsiveContainer>
							<ScatterChart
									data={ds.items}
									width={600} height={200}
									margin={{top: 20, right: 30, left: 20, bottom: 5}}
								>
								<Scatter key="x" data={ds.items} fill="green" />
								<CartesianGrid strokeDasharray="3 3" />
								<YAxis dataKey={"x"} type="number" />
								<XAxis dataKey={"y"} type="number" />
								<Legend />
								<Tooltip />
							</ScatterChart>
						</ResponsiveContainer>
					</Grid.Column>
					<Grid.Column width={3}><MatrixViewer mat={data.grad} /></Grid.Column>
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
