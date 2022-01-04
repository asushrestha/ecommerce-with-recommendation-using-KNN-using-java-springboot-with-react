import Axios from 'axios';
import { ADD_TO_CART, REMOVE_FROM_CART } from '../Constants/AddToCartConstants';

export const AddToCart = (product_id, quantity) => async (dispatch, getState) => {
	const { userLogin: { userInfo } } = getState();
	const config = {
		headers: {
			'Content-Type': 'application/json',
			Authorization: `Bearer ${userInfo.accessToken}`,
		},
	};

	const { data } = await Axios.post(`/v1/api/cart/add`, { product_id, quantity }, config);

	dispatch({
		type: ADD_TO_CART,
		payload: data,
	});
};

export const removeFromCart = (id) => (dispatch, getState) => {
	dispatch({
		type: REMOVE_FROM_CART,
		payload: id,
	});
};
