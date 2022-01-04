import { Grid } from '@material-ui/core';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { CategoryAction } from '../Actions/CategoryAction';
import Loader from '../Components/Loader';
import Message from '../Components/Message';
import Product from '../Components/Product';

const FilterScreen = ({ match, location }) => {
	
	console.log(match.params.category);
	const subCategory = location.search.split('=')[1];
	console.log(subCategory);

	const dispatch = useDispatch();

	const getCategory = useSelector((state) => state.getCategory);
	const { loading, category, error } = getCategory;
	console.log(category);
	console.log(loading);
	useEffect(
		() => {
			dispatch(CategoryAction(match.params.category, subCategory));
		},
		[ dispatch, match, subCategory ]
	);

	return (
		<div style={{ marginTop: 100 }}>
			{loading && <Loader />}
			{error && <Message severity='error'>Something went wrong!</Message>}
			<div style={{ display: 'flex', justifyContent: 'flex-start' }}>
				{match.params.category && !subCategory ? (
					<div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
						<p>
							<Link to='/'>Home</Link>
						</p>{' '}
						&gt;{' '}
						<p>
							<Link to={`/filter/${match.params.category}`}>{match.params.category}</Link>
						</p>
					</div>
				) : (
					''
				)}
				{match.params.category && subCategory ? (
					<div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
						<p>
							<Link to='/'>Home</Link>
						</p>&gt;{' '}
						<p>
							<Link to={`/filter/${match.params.category}`}>{match.params.category}</Link>
						</p>
						<p>&gt;{subCategory}</p>
					</div>
				) : (
					''
				)}
			</div>
			<Grid container style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
				{category?.map((product, i) => {
					return (
						<Grid item key={i} xs={12} sm={6} md={4} lg={3} style={{ padding: 20 }}>
							<Product product={product} />
						</Grid>
					);
				})}
			</Grid>
		</div>
	);
};

export default FilterScreen;
