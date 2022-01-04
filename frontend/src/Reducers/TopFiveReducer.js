import { TOP_FIVE_FAIL, TOP_FIVE_REQUEST, TOP_FIVE_SUCCESS } from '../Constants/TopFiveConstants';

export const TopFiveReducer = (state = {}, action) => {
	switch (action.type) {
		case TOP_FIVE_REQUEST:
			return { loading: true };
		case TOP_FIVE_SUCCESS:
			return {
				loading: false,
				five: action.payload,
			};
		case TOP_FIVE_FAIL:
			return {
				loading: false,
				error: action.payload,
			};
		default:
			return state;
	}
};
