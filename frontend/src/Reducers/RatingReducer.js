import { ADD_RATING_FAIL, ADD_RATING_REQUEST, ADD_RATING_SUCCESS } from '../Constants/RatingConstants';

export const AddRatingReducer = (state = {}, action) => {
	switch (action.type) {
		case ADD_RATING_REQUEST:
			return {
				loading: true,
			};
		case ADD_RATING_SUCCESS:
			return {
				loading: false,
				rating: action.payload,
			};
		case ADD_RATING_FAIL:
			return {
				loading: true,
				error: action.payload,
			};
		default:
			return state;
	}
};
