import Axios from 'axios';
import {
	CLEAR_CART,
	GET_CART_ITEMS_FAIL,
	GET_CART_ITEMS_REQUEST,
	GET_CART_ITEMS_SUCCESS,
	REMOVE_ITEM_FROM_CART,
	CHECKOUT_FROM_LIST,
	CHECKOUT_REQUEST,
	CLEAR_CART_REQUEST
} from '../Constants/GetCartItemsConstant';

export const GetCartItemsAction = () => async (dispatch, getState) => {
	try {
		dispatch({ type: GET_CART_ITEMS_REQUEST });

		const { userLogin: { userInfo } } = getState();

		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${userInfo.accessToken}`,
			},
		};

		const { data } = await Axios.get('/v1/api/cart/show', config);
		dispatch({
			type: GET_CART_ITEMS_SUCCESS,
			payload: data,
		});
	} catch (error) {
		dispatch({
			type: GET_CART_ITEMS_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};

export const RemoveItemFromCartAction = (cartid, userid) => async (dispatch, getState) => {
	try {
		const { userLogin: { userInfo } } = getState();

		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${userInfo.accessToken}`,
			},
		};

		const { data } = await Axios.post('/v1/api/cart/remove', { cartid, userid }, config);
		dispatch({
			type: REMOVE_ITEM_FROM_CART,
			payload: data,
		});
	} catch (error) {}
};

export const ClearCartAction = () => async (dispatch, getState) => {
	try {
		const { userLogin: { userInfo } } = getState();
		dispatch({
			type: CLEAR_CART_REQUEST
		}
		)
		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${userInfo.accessToken}`,
			},
		};

		const { data } = await Axios.get('/v1/api/cart/remove-all', config);
		dispatch({
			type: CLEAR_CART,
			payload: data,
		});
	} catch (error) {}
};

export const CheckoutFromCart = () => async (dispatch, getState) => {
	
	try {
		console.log("saurav")
		dispatch({
			type: CHECKOUT_REQUEST
		}
		)
		const { userLogin: { userInfo } } = getState();

		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${userInfo.accessToken}`,
			},
		};

		const { data } = await Axios.get('/v1/api/cart/checkout-all', config);
		dispatch({
			type: CHECKOUT_FROM_LIST,
			payload: data,
		});
	} catch (error) {}
};