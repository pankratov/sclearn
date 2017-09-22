import React from 'react'
import Measure from 'react-measure'
import { XYPlot } from 'react-vis'


class Plot extends React.Component {
	constructor(props) {
		super(props)
		this.state = {
			dims: { w: 0, h: 0 }
		}
	}
	resize(rect) {
		this.setState({
			dims: {
				w: rect.bounds.width
				, h: rect.bounds.height
			}
		})
	}
	render() {
		const { w, h } = this.state.dims
		return (
			<Measure bounds onResize={::this.resize}>
				{({ measureRef }) =>
				<div ref={measureRef} style={{ height: "100%" }} >
					<XYPlot width={w} height={h} xDomain={[0, w]} yDomain={[0, h]}>
						{this.props.children}
					</XYPlot>
				</div>
				}
			</Measure>
		)
	}
}

export default Plot
