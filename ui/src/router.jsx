import React from 'react'
import { Router, Route, browserHistory, IndexRoute } from 'react-router'

import Start from './view/start'

export default (
	<Router history={browserHistory} >
		<Route path="/" component={Start} />
	</Router>
)
