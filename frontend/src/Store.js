import { AdminLoginReducer, AdminStatReducer, userListReducer } from './Reducers/AdminReducers';
import { ProductDetailsReducer, ProductListReducer } from './Reducers/ProductReducers';
import { UserDetailsReducer, UserLoginReducer, UserRegisterReducer } from './Reducers/UserReducers';
import { AddProductReducer } from './Reducers/AddProductReducer';
import { AddRatingReducer } from './Reducers/RatingReducer';
import { AddCommentReducer } from './Reducers/AddCommentReducer';
import { GetReviewReducer } from './Reducers/GetReviewReducer';
import { GetRecommendationReducer } from './Reducers/RecommendationReducer';
import { AddToCartReducer } from './Reducers/AddToCartReducer';
import { ClearCartReducer, GetCartItemsReducer, RemoveItemFromCartReducer,CheckoutFromCartReducer } from './Reducers/GetCartItemsReducer';
import { TopFiveReducer } from './Reducers/TopFiveReducer';
import { CategoryReducer } from './Reducers/CategoryReducer';

const { createStore, combineReducers, applyMiddleware } = require('redux');
const { composeWithDevTools } = require('redux-devtools-extension');
const { default: thunk } = require('redux-thunk');

const reducer = combineReducers({
	productList: ProductListReducer,
	productDetails: ProductDetailsReducer,
	userLogin: UserLoginReducer,
	userRegister: UserRegisterReducer,
	userDetails: UserDetailsReducer,
	adminLogin: AdminLoginReducer,
	userList: userListReducer,
	addProduct: AddProductReducer,
	addRating: AddRatingReducer,
	addComment: AddCommentReducer,
	getReview: GetReviewReducer,
	getRecommendation: GetRecommendationReducer,
	addToCart: AddToCartReducer,
	getCartItems: GetCartItemsReducer,
	removeItemFromCart: RemoveItemFromCartReducer,
	clearCart: ClearCartReducer,
	topFive: TopFiveReducer,
	getCategory: CategoryReducer,
	getNewCart:CheckoutFromCartReducer,
	adminStat:AdminStatReducer,
});

const userInfoFromStorage = localStorage.getItem('userInfo') ? JSON.parse(localStorage.getItem('userInfo')) : null;
const adminInfoFromStorage = localStorage.getItem('adminInfo') ? JSON.parse(localStorage.getItem('adminInfo')) : null;

const initialState = {
	userLogin: { userInfo: userInfoFromStorage },
	adminLogin: { adminInfo: adminInfoFromStorage },
};

const middleware = [ thunk ];

const store = createStore(reducer, initialState, composeWithDevTools(applyMiddleware(...middleware)));

export default store;
