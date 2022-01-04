import Axios from 'axios';
import { REVIEW_FAIL, REVIEW_REQUEST, REVIEW_SUCCESS } from '../Constants/GetReviewsConstants';

export const getReviews = (id) => async (dispatch, getState) => {
	try {
		dispatch({ type: REVIEW_REQUEST });

		const { userLogin: { userInfo } } = getState();
		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${userInfo.accessToken}`,
			},
		};

		const { data } = await Axios.get(`/v1/api/comment/getComment/${id}`, config);

		dispatch({ type: REVIEW_SUCCESS, payload: data });
	} catch (error) {
		dispatch({
			type: REVIEW_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};
