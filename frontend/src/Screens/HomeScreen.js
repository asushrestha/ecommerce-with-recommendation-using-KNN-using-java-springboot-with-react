import { Grid } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import Slider from 'react-slick';
import { listProducts } from '../Actions/ProductActions';
import { TopFiveAction } from '../Actions/TopFiveAction';
import Loader from '../Components/Loader';
import Message from '../Components/Message';
import Paginations from '../Components/Pagination';
import Product from '../Components/Product';

const HomeScreen = ({history}) => {
	const [currentPage,setCurrentPage] = useState(1);
	const [productsPerPage,setProductsPerPage]=useState(8);

	const dispatch = useDispatch();

	const productList = useSelector((state) => state.productList);
	const { loading, products, error } = productList;

	const topFive = useSelector((state) => state.topFive);
	const { loading: fiveLoading, five, error: fiveError } = topFive;


	useEffect(
		() => {
			dispatch(listProducts());
			dispatch(TopFiveAction());
		},
		[ dispatch ]
	);

	const settings = {
		dots: true,
		className: 'center',
		infinite: true,
		centerPadding: '60px',
		slidesToShow: 1,
		swipeToSlide: true,
		afterChange: function(index) {
			console.log(`Slider Changed to: ${index + 1}, background: #222; color: #bada55`);
		},
	};

	const indexOfLastProduct = currentPage * productsPerPage;
	const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
	const currentProducts = products?.slice(indexOfFirstProduct,indexOfLastProduct);

	const paginate = (pageNumber) => {
		setCurrentPage(pageNumber);
	}
	return (
		<div style={{marginTop:100}}>
			<h1 style={{ textAlign: 'center' }}>Top products</h1>
			{fiveLoading && <Loader />}
			{fiveError && <Message severity="error">Something went wrong!</Message>}
			<div style={{border:'1px solid black'}}>
			<Slider {...settings}>
				{five?.map((each, index) => {  
					return (
						<div key={index}>
							<div style={{display:'flex', justifyContent:'center', alignItems:'center'}}>
								<Link to={`/product/${each.id}`}>
							<img
								src={`data:image/jpeg;base64,${each.img}`}
								alt={each.name}
								style={{ height: 300, width: 'auto' }}
							/></Link>
							
							</div>
							<Link to={`/product/${each.id}`} style={{textDecoration:'none'}}>
							<h2 style={{textAlign:'center', color:'tomato'}}>
								{each.name}
							</h2>
							</Link>
						</div>
					);
				})}
				<div />
			</Slider>
			</div>

			<h1 style={{ textAlign: 'left' }}>Latest Products</h1>
			{loading ? (
				<Loader />
			) : error ? (
				<Message severity='error'>{error}</Message>
			) : (
				<Grid container style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
					
					{currentProducts.map((product, i) => {
						return (
							<Grid item key={i} xs={12} sm={6} md={4} lg={3} style={{ padding: 20 }}>
								<Product product={product} />
								
							</Grid>
						);
					})}
					
					<Grid item md={12} style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
					<Paginations productsPerPage={productsPerPage} totalProducts={products.length} paginate={paginate}/>
					</Grid>
				</Grid>
			)}
		</div>
	);
};

export default HomeScreen;
