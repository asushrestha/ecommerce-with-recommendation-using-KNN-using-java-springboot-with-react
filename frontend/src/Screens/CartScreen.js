import { Button,  Grid, List, ListItem } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import { ClearCartAction, GetCartItemsAction, RemoveItemFromCartAction } from '../Actions/GetCartItemsAction';
import { getRecommendations } from '../Actions/GetRecommendationAction';
import Message from '../Components/Message';
import './Slider.css';

const CartScreen = ({ match, location, history }) => {
	const [ localDateTime] = useState("2020-11-17 00:00:00");
	const [ view] = useState('VIEW');
	const [cart] = useState('CART');
	const [ userid ] = useState('1');
	const [ refetch, setRefetch ] = useState(false);

	const productId = match.params.id;

	const dispatch = useDispatch();


	const getRecommendation = useSelector((state) => state.getRecommendation);
	const { recommendations } = getRecommendation;

	const userLogin = useSelector((state) => state.userLogin);
	const { userInfo } = userLogin;

	const getCartItems = useSelector((state) => state.getCartItems);
	const {cartItems} = getCartItems;
	
	useEffect(
		() => {
			dispatch(GetCartItemsAction());	
		},
		[ dispatch, productId,localDateTime,view,refetch]
	);
	useEffect(
		() => {
		if (productId) 
		{
			dispatch(getRecommendations(productId, localDateTime, cart));					
		}
	}, [ dispatch, productId,localDateTime,view])

	const checkoutHandler = () => {
		console.log('checkout');
		history.push('/login?redirect=placeorder');
	};
	const removeHandler = (cartid) => {
		dispatch(RemoveItemFromCartAction(cartid,userid));
		setRefetch(!refetch);
	};

	const clearCartHandler = () => {	
		dispatch(ClearCartAction());	
		setRefetch(!refetch);	
	}
	const settings = {
		dots: true,
		className: 'center',
		infinite: true,
		centerPadding: '60px',
		slidesToShow: 4,
		swipeToSlide: true,

		afterChange: function(index) {
			console.log(`Slider Changed to: ${index + 1}, background: #222; color: #bada55`);
		},
	};
	return (
		<div style={{ marginTop: 100 }}>
			<Button variant='outlined' color='primary'>
				{' '}
				<Link to='/' style={{ textDecoration: 'none', color: 'black' }}>
					Back
				</Link>{' '}
			</Button>
			<h2>Shopping Cart</h2>
			{userInfo?cartItems.length === 0 ? (
				<Message severity='error'>Your cart is empty.</Message>
			) : (
				<div style={{ marginTop: -20 }}>
					<Grid container>
						<Grid item md={8}>
							<Grid container>
								<Grid item md={2} style={{ textAlign: 'center' }}>
									<h3>Product</h3>
								</Grid>
								<Grid item md={4} style={{ textAlign: 'center' }}>
									<h3>Product Name</h3>
								</Grid>
								<Grid item md={2} style={{ textAlign: 'center' }}>
									<h3>Price</h3>
								</Grid>
								<Grid item md={2} style={{ textAlign: 'center' }}>
									<h3>Quantity</h3>
								</Grid>
								<Grid item md={2} style={{ textAlign: 'center' }}>
									<h3>Remove</h3>
								</Grid>
							</Grid>
							
								{cartItems.map((item,index) => (
									
										<Grid container key={index}>
											<Grid item md={2} style={{ textAlign: 'center' }}>
												<img
													src={`data:image/jpeg;base64,${item.photo}`}
													alt={item.product}
													style={{ height: 50, width: 'auto' }}
												/>
											</Grid>
											<Grid item md={4} style={{ textAlign: 'center' }}>
												{item.productName}
											</Grid>
											<Grid item md={2} style={{ textAlign: 'center' }}>
												Rs. {item.price}
											</Grid>
											<Grid item md={2} style={{ textAlign: 'center' }}>
											  {item.quantity}										
											</Grid>
											<Grid item md={2} style={{ textAlign: 'center' }}>
												<Button
													variant='contained'
													color='secondary'
													disableElevation
													onClick={() => removeHandler(item.cartid)}
												>
													<i className='fas fa-trash' />
												</Button>
											</Grid>
										</Grid>
									
								))}
							
						</Grid>
						<Grid item md={4}>
							<List style={{ marginTop: -50 }}>
								<ListItem style={{ display: 'block' }}>
									<h3 style={{ textAlign: 'right' }}>
										Subtotal ({cartItems.reduce((acc, item) => Number(acc + item.quantity), 0)}) items
									</h3>
									<h3 style={{ textAlign: 'right' }}>
										Subtotal: Rs.{' '}
										{cartItems
											.reduce((acc, item) => Number(acc + item.quantity * item.price), 0)
											.toFixed(2)}
									</h3>
									<div style={{ display: 'flex', justifyContent: 'flex-end' }}>
										
										<Button
											disableElevation
											variant='contained'
											color='primary'
											
											onClick={checkoutHandler}
											style={{ textAlign: 'end', padding: '10px' }}
										>
											Proceed to Checkout
										</Button>
										
										
									</div>
									<div style={{display:'flex', justifyContent:'flex-end'}}>
									<Button
											disableElevation
											variant='contained'
											color='secondary'
										
											onClick={clearCartHandler}
											style={{ textAlign: 'end', padding: '10px', marginTop:10 }}
										>
											Clear Cart
										</Button>
										</div>
								</ListItem>
							</List>
						</Grid>
					</Grid>
					<Grid item md={12} style={{marginTop:50}}>
						<h1>You may also Like</h1>
							<Slider {...settings}>
								{recommendations?.recommendedItemsList?.map((Example, index) => {
									return (
										<div key={index}>
											<div style={{
													display: 'flex',
													justifyContent: 'center',
													alignItems: 'center',												
												}}>
													<Link to={`/product/${Example.id}`}>
											 <img
											src={`data:image/jpeg;base64,${Example.img}`}
											alt={Example.name}
											style={{ height: 150, width: 'auto' }}
										/>
										</Link>
										</div>
										<Link to={`/product/${Example.id}`} style={{fontWeight:'bold', textDecoration:'none', color:'black'}}> 
											<p
												style={{
													display: 'flex',
													justifyContent: 'center',
													alignItems: 'center',
													color: 'black',
													
												}}
											>
												{Example.name}
											</p>
											</Link>
										</div>
									);
								})}
							</Slider>
						</Grid>
				</div>
			) : <Message severity="info">Please Log In</Message>}
			
		</div>
	);
};

export default CartScreen;
