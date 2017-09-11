import _ from 'lodash'
import React from 'react'
import { connect } from 'react-redux'
import { Container } from 'semantic-ui-react'
import {
	ResponsiveContainer,
	XAxis, YAxis,
	CartesianGrid,
	Legend, Tooltip,
	LineChart, Line,
	ScatterChart, Scatter
} from 'recharts'


const color = {
	"setosa"		: "#8884d8",
	"virginica"		: "#82ca9d",
	"versicolor"	: "#f5f5f5"
}

class MainStats extends React.Component {

	render() {
		const ds = _.groupBy(this.props.ds.iris.items, "class")
		return (
			<ResponsiveContainer>
				<ScatterChart
						data={ds.items}
						width={600} height={300}
						margin={{top: 20, right: 30, left: 20, bottom: 5}}
					>

					{ Object.entries(ds).map(([k, v], i) => (
						<Scatter key={i} data={v} fill={color[k]} />
					))}

					<CartesianGrid strokeDasharray="3 3" />

					<YAxis dataKey={"sepal_width"} type="number" />
					<XAxis dataKey={"petal_width"} type="number" />

					<Legend />
					<Tooltip />

				</ScatterChart>
			</ResponsiveContainer>
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
