import React from 'react'
import { Container } from 'semantic-ui-react'

import Note from './note'
import Stats from './stats'
import MainStats from './mainstats'

class Start extends React.Component {

	render() {
		return (
			<Container fluid >
				<Note />
			</Container>
		)
	}

}

export default Start
