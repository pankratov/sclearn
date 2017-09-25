import { combineReducers } from 'redux'
import * as EV from '../api/base'

import dsIris from '../ds/iris.json'

const app = {
	ds: {
		iris: dsIris
	}
	, compilation: {
		result: {}
		, waiting: false
		, failure: false
	}
}

const dsIrisReducer = (state = app.ds.iris, action) => {
	return state
}

const compileReducer = (state = app.compilation, action) => {
	switch(action.type) {
		case EV.COMPILE_INIT:
			return { result: {}, waiting: true, failure: false }
		case EV.COMPILE_SUCC:
			return { result: action.result.data, waiting: false, failure: false }
		case EV.COMPILE_FAIL:
			return { result: action.result.data, waiting: false, failure: true }
	}
	return state
}

export default combineReducers({
	ds: combineReducers({
		iris: dsIrisReducer
	})
	, compilation: compileReducer
})
