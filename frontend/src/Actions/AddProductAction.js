import Axios from 'axios';
import { ADD_PRODUCT_FAIL, ADD_PRODUCT_REQUEST, ADD_PRODUCT_SUCCESS } from '../Constants/AddProductConstants';

export const AddProductAction = (
	productImage,
	quantity,
	subCategoryId,
	subSubCategoryId,
	categoryId,
	price,
	colour,
	name
) => async (dispatch, getState) => {
	try {
		dispatch({
			type: ADD_PRODUCT_REQUEST,
		});

		const { adminLogin: { adminInfo } } = getState();

		const formData = new FormData();
		formData.append('productImage', productImage);
		formData.append('quantity', quantity);
		formData.append('subCategoryId', subCategoryId);
		formData.append('subSubCategoryId', subSubCategoryId);
		formData.append('categoryId', categoryId);
		formData.append('price', price);
		formData.append('colour', colour);
		formData.append('name', name);

		const config = {
			headers: {
				'Content-Type': 'application/json',
				Authorization: `Bearer ${adminInfo.accessToken}`,
			},
		};

		const { data } = await Axios.post('/v1/api/product/add-product', formData, config);

		dispatch({
			type: ADD_PRODUCT_SUCCESS,
			payload: data,
		});
	} catch (error) {
		dispatch({
			type: ADD_PRODUCT_FAIL,
			payload: error.response && error.response.data.message ? error.response.data.message : error.message,
		});
	}
};
