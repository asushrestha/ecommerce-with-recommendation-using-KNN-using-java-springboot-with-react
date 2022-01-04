import { CATEGORY_FAIL, CATEGORY_REQUEST, CATEGORY_SUCCESS } from '../Constants/CategoryConstants';

export const CategoryReducer = (state = { category: [ {} ] }, action) => {
	switch (action.type) {
		case CATEGORY_REQUEST:
			return { loading: true };
		case CATEGORY_SUCCESS:
			return {
				loading: false,
				category: action.payload,
			};
		case CATEGORY_FAIL:
			return {
				loading: false,
				error: action.payload,
			};
		default:
			return state;
	}
};
