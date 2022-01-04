import { Button, Grid } from '@material-ui/core';
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { AddProductAction } from '../Actions/AddProductAction';
import Loader from '../Components/Loader';
import Message from '../Components/Message';

const AddProduct = () => {
	const [ name, setName ] = useState();
	const [ colour, setColour ] = useState();
	const [ price, setPrice ] = useState();
	const [ quantity, setQuantity ] = useState();
	const [ categoryId, setCategoryId ] = useState();
	const [ subCategoryId, setSubCategoryId ] = useState();
	const [ subSubCategoryId, setSubSubCategoryId ] = useState();
	const [ productImage, setProductImage ] = useState();

	const dispatch = useDispatch();

	const adminLogin = useSelector((state) => state.adminLogin);
	const { error, loading, adminInfo } = adminLogin;
	const addProduct = useSelector((state) => state.addProduct);
	const { product } = addProduct;

	const submitHandler = (e) => {
		e.preventDefault();
		console.log('Successfully Added');
		dispatch(
			AddProductAction(productImage, quantity, subCategoryId, subSubCategoryId, categoryId, price, colour, name)
		);
	};
	return (
		<div style={{ marginTop: 100 }}>
			<Link to='/admindashboard' style={{ textDecoration: 'none' }}>
				<Button variant='outlined' color='primary'>
					Back
				</Button>
			</Link>
			{product && <Message severity='success'>{product.status}</Message>}
			{loading ? (
				<Loader />
			) : error ? (
				<Message severity='error'>{error}</Message>
			) : adminInfo ? (
				<form onSubmit={submitHandler} noValidate>
					<Grid container>
						<Grid
							item
							md={4}
							style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}
						>
							<h4>Name </h4>{' '}
							<input
								type='text'
								id='name'
								label='name'
								value={name}
								onChange={(e) => setName(e.target.value)}
							/>
						</Grid>
						<Grid
							item
							md={4}
							style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}
						>
							<h4>Colour </h4>
							<input
								type='text'
								id='colour'
								label='colour'
								value={colour}
								onChange={(e) => setColour(e.target.value)}
							/>
						</Grid>
						<Grid
							item
							md={4}
							style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}
						>
							<h4>Price(Rs)</h4>
							<input
								type='number'
								id='price'
								label='price'
								value={price}
								onChange={(e) => setPrice(e.target.value)}
							/>
						</Grid>
						<Grid
							item
							md={4}
							style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}
						>
							<h4>Quantity</h4>
							<input
								type='number'
								id='quantity'
								label='quantity'
								value={quantity}
								onChange={(e) => setQuantity(e.target.value)}
							/>
						</Grid>
						<Grid
							item
							md={4}
							style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}
						>
							<h4>Category Id</h4>
							<input
								type='number'
								id='Category Id'
								label='category Id'
								value={categoryId}
								onChange={(e) => setCategoryId(e.target.value)}
							/>
						</Grid>
						<Grid
							item
							md={4}
							style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}
						>
							<h4>SubCategory Id</h4>
							<input
								type='number'
								id='Sub Category'
								label='Sub Category'
								value={subCategoryId}
								onChange={(e) => setSubCategoryId(e.target.value)}
							/>
						</Grid>
						<Grid
							item
							md={4}
							style={{ display: 'flex', justifyContent: 'flex-start', alignItems: 'center' }}
						>
							<h4>SubSubCategory</h4>
							<input
								type='number'
								id='Sub Sub Category'
								label='Sub Sub Category'
								value={subSubCategoryId}
								onChange={(e) => setSubSubCategoryId(e.target.value)}
							/>
						</Grid>
						<Grid item md={5} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
							<h4>Image</h4>
							<input
								type='file'
								id='name'
								label='name'
								onChange={(e) => setProductImage(e.target.files[0])}
							/>
						</Grid>
						<Button type='submit' variant='contained' color='secondary'>
							ADD PRODUCT
						</Button>
					</Grid>
				</form>
			) : (
				<Message severity='error'>Not Authorized!</Message>
			)}
		</div>
	);
};

export default AddProduct;
