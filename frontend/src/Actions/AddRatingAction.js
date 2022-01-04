import Axios from 'axios';
import { ADD_RATING_FAIL, ADD_RATING_REQUEST, ADD_RATING_SUCCESS } from '../Constants/RatingConstants';

export const AddRatingAction = (rating, reviewDoneTo) => async (dispatch, getState) => {
	try {
		dispatch({
			type: ADD_RATING_REQUEST,
		});

		const { userLogin: { userInfo } } = getState();

		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${userInfo.accessToken}`,
			},
		};
		console.log(rating);

		const { data } = await Axios.post('/v1/api/review-rating', { rating, reviewDoneTo }, config);

		dispatch({
			type: ADD_RATING_SUCCESS,
			payload: data,
		});
	} catch (error) {
		dispatch({
			type: ADD_RATING_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};
