import Axios from 'axios';
import { CATEGORY_FAIL, CATEGORY_REQUEST, CATEGORY_SUCCESS } from '../Constants/CategoryConstants';

export const CategoryAction = (category, subCategory) => async (dispatch) => {
	try {
		dispatch({
			type: CATEGORY_REQUEST,
		});

		const config = {
			headers: {
				'Content-Type': 'application/json',
			},
		};

		const { data } = await Axios.post('/v1/api/product/filter', { category, subCategory }, config);

		dispatch({
			type: CATEGORY_SUCCESS,
			payload: data,
		});
	} catch (error) {
		dispatch({
			type: CATEGORY_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};
