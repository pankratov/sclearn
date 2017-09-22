import React from 'react'
import { Container } from 'semantic-ui-react'

import Stats from './stats'
import MainStats from './mainstats'

class Start extends React.Component {

	render() {
		return (
			<Container fluid >
				<Stats />
			</Container>
		)
	}

}

export default Start
