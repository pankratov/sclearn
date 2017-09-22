import _ from 'lodash'
import React from 'react'
import { connect } from 'react-redux'
import { Container, Table, Grid } from 'semantic-ui-react'
import Plot from './plot'
import {
	VerticalGridLines, HorizontalGridLines, CircularGridLines
	, XAxis, YAxis
	, LineSeries
	, MarkSeries
} from 'react-vis'


function sin(n) {
	const p = []
	for (var i = 0, t = 0; i < 100; ++i, t += 0.1) {
		p.push({ x: t, y: Math.sin(t) / 3 })
	}
	return { points: p }
}

class Stats extends React.Component {
	render() {
		const points = sin(100).points
		return (
			<Container fluid >
				<Grid padded >
					<Grid.Column width={16} style={{ height: 300 }} >
						<Plot>
							<VerticalGridLines />
							<HorizontalGridLines />
							<XAxis />
							<YAxis />
							<LineSeries color="red" data={points} />
						</Plot>
					</Grid.Column>
				</Grid>
			</Container>
		)
	}

}

function mapper(state, props) {
	return {
	}
}

export default connect(mapper)(Stats)
