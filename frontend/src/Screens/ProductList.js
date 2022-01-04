import { Button, Grid } from '@material-ui/core';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { listProducts } from '../Actions/ProductActions';
import Loader from '../Components/Loader';
import Message from '../Components/Message';
import Rating from '../Components/Rating';

const ProductList = () => {
    const dispatch = useDispatch();
    
    const adminLogin = useSelector((state) => state.adminLogin);
	const { adminInfo } = adminLogin;

	const productList = useSelector((state) => state.productList);
    const { loading, products, error } = productList;
    
	useEffect(
		() => {
			dispatch(listProducts());
		},
		[ dispatch ]
    );
    
    const StyledButton = styled.button`
    border:none; 
    padding:20px; 
    background-color:tomato; 
    color:white;
    outline:none;
    cursor:pointer;
    transition: all 0.5s ease-in-out;
    text-transform:uppercase;
    &:hover{
        background-color:red;
    }
    `

	return (
        <div style={{ marginTop: 100 }}>
        <Link to='/admindashboard' style={{ textDecoration: 'none' }}>
            <Button variant='outlined' color='primary'>
                Back
            </Button>
        </Link>
        {adminInfo ? (
            <div>
                {loading? <Loader /> : error? <Message severity="error">{error}</Message>:<div>
                <Grid container>
                    <Grid item md={6}><h1>Product List</h1></Grid>
                    <Grid item md={6} style={{display:'flex', justifyContent:'flex-end',alignItems:'center' }}>
                        <Link to="/addproduct"><StyledButton >
                        <i className="fas fa-plus" style={{paddingRight:5}} />
                            Add Product</StyledButton>
                        </Link>
                        
                    </Grid>
                </Grid>             
                
                <Grid container>
                    <Grid item md={12}>
                        <Grid container>
                            <Grid item md={1} style={{ textAlign: 'left' }}>
                                <h3>Id</h3>
                            </Grid>
                            <Grid item md={3} style={{ textAlign: 'left' }}>
                                <h3>Name</h3>
                            </Grid>
                            <Grid item md={2} style={{ textAlign: 'left' }}>
                                <h3>Price</h3>
                            </Grid>
                            <Grid item md={1} style={{ textAlign: 'left' }}>
                                <h3>Quantity</h3>
                            </Grid>
                            <Grid item md={2} style={{ textAlign: 'left' }}>
                                <h3>Category</h3>
                            </Grid>
                            <Grid item md={2} style={{ textAlign: 'left' }}>
                                <h3>Rating</h3>
                            </Grid>
                            <Grid item md={1} style={{ textAlign: 'left' }}>
                                <h3>Image</h3>
                            </Grid>
                        </Grid>

                        {products.map((product,i) => (
                            <Grid container key={i}>
                                <Grid item md={1} style={{ textAlign: 'left' }}>
                                    {product.id}
                                </Grid>
                                <Grid item md={3} style={{ textAlign: 'left' }}>
                                    {product.name}
                                </Grid>
                                <Grid item md={2} style={{ textAlign: 'left' }}>
                                    {product.price}
                                </Grid>
                                <Grid item md={1} style={{ textAlign: 'left' }}>
                                    {product.quantity}
                                </Grid>
                                <Grid item md={2} style={{ textAlign: 'left' }}>
                                    {product.category}
                                </Grid>
                                <Grid item md={2} style={{ textAlign: 'left' }}>
                                    
                                    <Rating value={product?.rating?.rating} />
                                </Grid>
                                <Grid item md={1} style={{ textAlign: 'left' }}>
                                <img
											src={`data:image/jpeg;base64,${product.img}`}
											alt={product.name}
											style={{ height: 50, width: 'auto' }}
										/>
                                </Grid>
                            </Grid>
                        ))}
                    </Grid>
                </Grid> </div> }
                
            </div>
        ) : (
            <Message severity='error'>Not Authorized</Message>
        )}
    </div>);
};

export default ProductList;
