import { createStore, applyMiddleware } from 'redux'
import { createLogger } from 'redux-logger'

import * as EV from './api/base'
import api from './api'
import rootReducer from './reducer/root'

const logger = createLogger()
const Store = applyMiddleware(logger)(createStore)(rootReducer)

export default Store
