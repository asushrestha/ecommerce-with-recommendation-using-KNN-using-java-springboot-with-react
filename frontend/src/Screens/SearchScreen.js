import { Grid } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { listProducts } from '../Actions/ProductActions';
import Loader from '../Components/Loader';
import Message from '../Components/Message';
import Product from '../Components/Product';

const SearchScreen = ({ location }) => {
	const dispatch = useDispatch();

	const keyword = location.search.split('=')[1];
	console.log(keyword);

	const productList = useSelector((state) => state.productList);
	const { loading, products, error } = productList;

	useEffect(
		() => {
			dispatch(listProducts());				
		},
		[ dispatch, keyword]
	);
		const filteredProducts = products.filter((product) => {					
			return( product.name.toLowerCase().includes(keyword.toLowerCase()));
		})

	return (
		<div style={{ marginTop: 100 }}>
			{loading ? (
				<Loader />
			) : error ? (
				<Message severity='error'>{error}</Message>
			) : (
				<Grid container style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
					{filteredProducts?.map((product, i) => {
						return (
							<Grid item key={i} xs={12} sm={6} md={4} lg={3} style={{ padding: 20 }}>
								<Product product={product} />
							</Grid>
						);
					})}
				</Grid>
			)}
		</div>
	);
};

export default SearchScreen;
