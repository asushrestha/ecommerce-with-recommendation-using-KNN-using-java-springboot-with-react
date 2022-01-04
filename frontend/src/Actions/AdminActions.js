import Axios from 'axios';
import {
	ADMIN_LOGIN_FAIL,
	ADMIN_LOGIN_REQUEST,
	ADMIN_LOGIN_SUCCESS,
	ADMIN_LOGOUT,
	ADMIN_STAT_FAIL,
	ADMIN_STAT_REQUEST,
	ADMIN_STAT_SUCCESS,
	USER_LIST_FAIL,
	USER_LIST_REQUEST,
	USER_LIST_SUCCESS,
} from '../Constants/AdminConstants';

export const Admin = (userName, password) => async (dispatch) => {
	try {
		dispatch({
			type: ADMIN_LOGIN_REQUEST,
		});

		const config = {
			headers: {
				'Content-Type': 'application/json',
			},
		};

		const { data } = await Axios.post('/v1/api/auth/admin', { userName, password }, config);

		dispatch({
			type: ADMIN_LOGIN_SUCCESS,
			payload: data,
		});

		localStorage.setItem('adminInfo', JSON.stringify(data));
	} catch (error) {
		dispatch({
			type: ADMIN_LOGIN_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};
export const getAdminStat = () => async(dispatch,getState)=>{
		try {
			dispatch({
				type: ADMIN_STAT_REQUEST,
			});
	
			const {adminLogin:{adminInfo}} = getState();
			const config = {
				headers: {
					'Content-Type': 'application/json',
					Authorization: `Bearer ${adminInfo.accessToken}`,

				},
			};
	
			const { data } = await Axios.get('/v1/api/admin/count-stat', config);
	
			dispatch({
				type: ADMIN_STAT_SUCCESS,
				payload: data,
			});
	
		} catch (error) {
			dispatch({
				type: ADMIN_STAT_FAIL,
				payload: error.response && error.response.data.message ? error.response.data.message : error.message,
			});
		}
	};
export const adminLogout = () => (dispatch) => {
	localStorage.removeItem('adminInfo');
	localStorage.removeItem('cartItems');
	localStorage.removeItem('_paypal_storage');
	localStorage.removeItem('userInfos');
	document.location.href = '/login';

	dispatch({ type: ADMIN_LOGOUT });
};

export const getUserList = () => async (dispatch, getState) => {
	try {
		dispatch({
			type: USER_LIST_REQUEST,
		});

		const { adminLogin: { adminInfo } } = getState();

		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${adminInfo.accessToken}`,
			},
		};
		const { data } = await Axios.get('/v1/api/admin/get-registered-users', config);

		dispatch({
			type: USER_LIST_SUCCESS,
			payload: data,
		});
	} catch (error) {
		dispatch({
			type: USER_LIST_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};
