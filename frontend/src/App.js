import Header from './Components/Header';
import Footer from './Components/Footer';
import HomeScreen from './Screens/HomeScreen';
import { StylesProvider } from '@material-ui/core';
import { BrowserRouter, Route } from 'react-router-dom';
import ProductScreen from './Screens/ProductScreen';
import CartScreen from './Screens/CartScreen';
import LoginScreen from './Screens/LoginScreen';
import RegisterScreen from './Screens/RegisterScreen';
import ProfileScreen from './Screens/ProfileScreen';
import PlaceOrder from './Screens/PlaceOrder';
import AdminLogin from './Screens/AdminLogin';
import AdminDashboard from './Screens/AdminDashboard';
import UserList from './Screens/UserList';
import ProductList from './Screens/ProductList';
import AddProduct from './Screens/AddProduct';
import FilterScreen from './Screens/FilterScreen';
import SearchScreen from './Screens/SearchScreen';

function App() {
	return (
		<BrowserRouter>
			<Header />
			<div className='App' style={{ fontFamily: 'Poppins', width: '80%', margin: 'auto' }}>
				<StylesProvider injectFirst>
					<Route path='/' exact component={HomeScreen} />
					<Route path='/search/:keyword?' exact component={SearchScreen} />
					<Route path='/product/:id' component={ProductScreen} />
					<Route path='/cart/:id?' component={CartScreen} />
					<Route path='/login' component={LoginScreen} />
					<Route path='/admin' component={AdminLogin} />
					<Route path='/register' component={RegisterScreen} />
					<Route path='/profile' component={ProfileScreen} />
					<Route path='/placeorder' component={PlaceOrder} />
					<Route path='/admindashboard' component={AdminDashboard} />
					<Route path='/userlist' component={UserList} />
					<Route path='/productlist' component={ProductList} />
					<Route path='/addproduct' component={AddProduct} />
					<Route path='/filter/:category?' component={FilterScreen} />
				</StylesProvider>
			</div>
			<Footer />
		</BrowserRouter>
	);
}

export default App;
