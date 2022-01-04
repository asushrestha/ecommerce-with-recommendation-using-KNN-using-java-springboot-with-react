import { ADD_PRODUCT_FAIL, ADD_PRODUCT_REQUEST, ADD_PRODUCT_SUCCESS } from '../Constants/AddProductConstants';

export const AddProductReducer = (state = {}, action) => {
	switch (action.type) {
		case ADD_PRODUCT_REQUEST:
			return { loading: true };
		case ADD_PRODUCT_SUCCESS:
			return {
				loading: false,
				product: action.payload,
			};
		case ADD_PRODUCT_FAIL:
			return {
				loading: false,
				error: action.payload,
			};
		default:
			return state;
	}
};
