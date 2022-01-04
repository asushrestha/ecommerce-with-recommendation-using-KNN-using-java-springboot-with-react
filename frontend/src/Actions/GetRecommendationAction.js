import Axios from 'axios';
import {
	RECOMMENDATION_FAIL,
	RECOMMENDATION_REQUEST,
	RECOMMENDATION_SUCCESS,
} from '../Constants/GetRecommendationConstants';

export const getRecommendations = (productId, localDateTime, selectionParam) => async (dispatch, getState) => {
	try {
		dispatch({ type: RECOMMENDATION_REQUEST });

		const { userLogin: { userInfo } } = getState();
		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${userInfo.accessToken}`,
			},
		};

		const { data } = await Axios.post(
			'/v1/api/recommendation/track-user',
			{ productId, localDateTime, selectionParam },
			config
		);
		console.log(data)
		dispatch({ type: RECOMMENDATION_SUCCESS, payload: data });
	} catch (error) {
		dispatch({
			type: RECOMMENDATION_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};
