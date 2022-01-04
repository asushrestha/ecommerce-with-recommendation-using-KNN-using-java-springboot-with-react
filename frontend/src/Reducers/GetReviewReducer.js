import { REVIEW_FAIL, REVIEW_REQUEST, REVIEW_SUCCESS } from '../Constants/GetReviewsConstants';

export const GetReviewReducer = (state = { reviews: [] }, action) => {
	switch (action.type) {
		case REVIEW_REQUEST:
			return { ...state, loading: true };
		case REVIEW_SUCCESS:
			return {
				loading: false,
				reviews: action.payload,
			};
		case REVIEW_FAIL:
			return {
				loading: false,
				error: action.payload,
			};
		default:
			return state;
	}
};
