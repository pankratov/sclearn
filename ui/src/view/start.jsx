import React from 'react'
import { Container } from 'semantic-ui-react'

import MainStats from './mainstats'

class Start extends React.Component {

	render() {
		return (
			<Container fluid style={{ height: 500 }} >
				<MainStats />
			</Container>
		)
	}

}

export default Start
