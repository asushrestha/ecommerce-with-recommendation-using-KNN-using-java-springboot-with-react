import { ADD_TO_CART, REMOVE_FROM_CART } from '../Constants/AddToCartConstants';

export const AddToCartReducer = (state = { addToCartRes: [] }, action) => {
	switch (action.type) {
		case ADD_TO_CART:
			return {
				...state,
				addToCartRes: action.payload,
			};
		case REMOVE_FROM_CART:
			return {
				...state,
				addToCartRes: state.addToCartRes.filter((x) => x.product !== action.payload),
			};
		default:
			return state;
	}
};
