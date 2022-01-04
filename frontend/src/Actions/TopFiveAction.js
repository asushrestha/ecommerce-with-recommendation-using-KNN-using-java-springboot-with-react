import Axios from 'axios';
import { TOP_FIVE_FAIL, TOP_FIVE_REQUEST, TOP_FIVE_SUCCESS } from '../Constants/TopFiveConstants';

export const TopFiveAction = (category, subCategory) => async (dispatch, getState) => {
	try {
		dispatch({
			type: TOP_FIVE_REQUEST,
		});

		const { data } = await Axios.get('/v1/api/product/top-5');
		dispatch({
			type: TOP_FIVE_SUCCESS,
			payload: data,
		});
	} catch (error) {
		dispatch({
			type: TOP_FIVE_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};
