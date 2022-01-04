import { Card } from '@material-ui/core';
import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Rating from './Rating';

const ParagraphStyle = styled.p`font-size: 17px;`;

const StyledImage = styled.img`
	transition: all 0.5s ease-in-out;
	&:hover {
		transform: scale(1.1);
	}
`;
const Product = ({ product }) => {
	return (
		<div>
			<Card style={{ height: '61vh', padding: '10px' }}>
				<Link
					to={`/product/${product.id}`}
					style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}
				>
					<StyledImage
						src={`data:image/jpeg;base64,${product.img}`}
						title={product.name}
						style={{ height: '200px', width: 'auto' }}
					/>
				</Link>
				<div style={{ width: '100%', border: '1px solid black', marginTop: 20 }} />
				<Link to={`/product/${product.id}`} style={{ textDecoration: 'none', color: 'black' }}>
					<ParagraphStyle
						style={{
							width: '96% ',
							margin: 'auto ',
							fontSize: 20,
							marginTop: 20,
							fontWeight: 'bold',
						}}
					>
						{product.name}
					</ParagraphStyle>
					<ParagraphStyle
						style={{
							width: '96% ',
							margin: 'auto ',
						}}
					>
						Quantity: {product.quantity}
					</ParagraphStyle>
					<ParagraphStyle
						style={{
							width: '96% ',
							margin: 'auto ',
							color: 'red',
							fontWeight: 'bold',
						}}
					>
						Price: Rs.{product.price}
					</ParagraphStyle>
					<ParagraphStyle
						style={{
							width: '96% ',
							margin: 'auto ',
						}}
					>
						Category: {product.category}
					</ParagraphStyle>
					<div
						style={{
							width: '96% ',
							margin: 'auto ',
						}}
					>
						<Rating value={product?.rating?.rating} />
					</div>
				</Link>
			</Card>
		</div>
	);
};

export default Product;
