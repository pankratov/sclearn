import { combineReducers } from 'redux'
import * as EV from '../api/base'

import dsIris from '../ds/iris.json'

const app = {
	ds: {
		iris: dsIris
	}
}

const dsIrisReducer = (state = app.ds.iris, action) => {
	return state
}

export default combineReducers({
	ds: combineReducers({
		iris: dsIrisReducer
	})
})
