import {
	RECOMMENDATION_FAIL,
	RECOMMENDATION_REQUEST,
	RECOMMENDATION_SUCCESS,
} from '../Constants/GetRecommendationConstants';

export const GetRecommendationReducer = (state = { recommendations: {} }, action) => {
	switch (action.type) {
		case RECOMMENDATION_REQUEST:
			return { ...state, loading: true };
		case RECOMMENDATION_SUCCESS:
			return {
				loading: false,
				recommendations: action.payload,
			};
		case RECOMMENDATION_FAIL:
			return {
				loading: false,
				error: action.payload,
			};
		default:
			return state;
	}
};
