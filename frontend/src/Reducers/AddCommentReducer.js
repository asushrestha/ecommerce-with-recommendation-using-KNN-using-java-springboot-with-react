import { ADD_COMMENT_FAIL, ADD_COMMENT_REQUEST, ADD_COMMENT_SUCCESS } from '../Constants/AddCommentConstants';

export const AddCommentReducer = (state = { comment: [ {} ] }, action) => {
	switch (action.type) {
		case ADD_COMMENT_REQUEST:
			return { loading: true };
		case ADD_COMMENT_SUCCESS:
			return {
				loading: false,
				comment: action.payload,
			};
		case ADD_COMMENT_FAIL:
			return {
				loading: false,
				error: action.payload,
			};
		default:
			return state;
	}
};
