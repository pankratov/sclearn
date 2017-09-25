import _ from 'lodash'
import React from 'react'
import { connect } from 'react-redux'
import {
	Container, Table, Grid
	, Button
} from 'semantic-ui-react'
import brace from 'brace'
import AceEditor from 'react-ace'
 
import 'brace/mode/scala'
import 'brace/theme/github'
import 'brace/theme/solarized_light'

import api from '../../api'


class Note extends React.Component {

	constructor(props) {
		super(props)
		this.state = {
			code: '',
		}
	}

	onChange(newValue) {
		this.setState({
			code: newValue
		})
	}

	compile() {
		this.props.dispatch(api.compile(this.state.code))
	}

	render() {
		const wait = this.props.compilation.waiting
		const res = this.props.compilation.result
		return (
			<Container fluid >
				<Grid padded >
					<Grid.Column width={8} style={{ height: 300 }} >
						<AceEditor
							width="100%"
							height="100%"
							mode="scala"
							theme="solarized_light"
							name="UNIQUE_ID_OF_DIV"
							fontSize={26}
							value={this.state.code}
							onChange={::this.onChange}
							editorProps={{$blockScrolling: true}}
							commands={[
								{
									name: 'compile'
									, bindKey: { win: 'Shift+Enter', mac: 'Shift+Enter' }
									, exec: ::this.compile
								}
							]}
						/>
					</Grid.Column>
					<Grid.Column width={8} style={{ height: 300 }} >
						<AceEditor
							width="100%"
							height="100%"
							mode="scala"
							theme="solarized_light"
							name="UNIQUE_ID_OF_DIV"
							fontSize={26}
							editorProps={{$blockScrolling: true}}
						/>
					</Grid.Column>
					<Grid.Column width={1} style={{ height: 300 }} >
						{!wait &&
							<Button primary onClick={::this.compile}>Compile</Button>
						}
					</Grid.Column>
					<Grid.Column width={16} style={{ height: 300 }} >
						<p>{JSON.stringify(res)}</p>
					</Grid.Column>
				</Grid>
			</Container>
		)
	}

}

function mapper(state, props) {
	return {
		compilation: state.compilation
	}
}

export default connect(mapper)(Note)
