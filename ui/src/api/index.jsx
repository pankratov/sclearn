import axios from 'axios'
import * as EV from './base'

const MATRIX_BASE_URL = EV.MATRIX_BASE_URL

function compile(code) {
	return {
		types: [
			EV.COMPILE_INIT,
			EV.COMPILE_SUCC,
			EV.COMPILE_FAIL
		]
		, promise: () => {
			return axios.get("/compile", {
				baseURL: MATRIX_BASE_URL
				, timeout: 50000
				, withCredentials: true
				, params: {
					code: code
				}
			})
		}
	}
}

export default {
	compile: compile
}
