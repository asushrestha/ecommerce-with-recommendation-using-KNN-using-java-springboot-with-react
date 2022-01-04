// import Axios from 'axios';
// import { DISABLE_USER_REQUEST,DISABLE_USER_RESPONSE } from '../Constants/TestConstants';

// export const Test = () => async (dispatch, getState) => {
// 	try {
// 		dispatch({
// 			type: DISABLE_USER_REQUEST,
// 		});

// 		// const { userLogin: { userInfo } } = getState();

// 		const config = {
// 			headers: {
// 				'Content-Type': 'application/json',
// 				// Authorization: `Bearer ${userInfo.accessToken}`,
//                 Authorization: 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBnbWFpbC5jb20iLCJpZCI6MSwidXNlck5hbWUiOiJhZG1pbkBnbWFpbC5jb20iLCJleHAiOjE2NDM5NTkzMzQsImlhdCI6MTYxMjQwMjM4Mn0.wIAnIV61MjHKRGslV0Q7m9lDmN6CxpmjdVHtBW2NHQZVgSdOMl7JpLDWHsP_3gUlgBi6i3V77Q8dDteQDbDn9w'
// 			},
// 		};

// 		const { data } = await Axios.get('https://app.ikonfess.com:8443/ikonfessv2/v1/api/post-category/admin/delete/13', config);

// 		dispatch({
// 			type: DISABLE_USER_RESPONSE,
// 			payload: data,
// 		});
// 	} 
//     catch (error) {
// 		dispatch({
// 			// type: DISABLE_USER_RESPONSE,
// 			// payload: error.response && error.response.data.message ? error.response.data.message : error.message,
// 		});
// 	}
// };
