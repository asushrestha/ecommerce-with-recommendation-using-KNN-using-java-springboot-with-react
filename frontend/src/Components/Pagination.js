import React from 'react';
import { NavLink } from 'react-router-dom';
import styled from 'styled-components';

const Ul = styled.ul`
	list-style: none;
	display: flex;
	justify-content: center;
	align-items: center;
`;
const Li = styled.li`
	display: flex;
	justify-content: center;
	align-items: center;
	cursor: pointer;
	& > a {
		color: black;
		font-weight: bold;
		border: 1px solid black;
		padding: 10px 20px;
		margin-left: 10px;
		transition: all 0.5s ease-in-out;
		&:hover {
			background-color: tomato;
			color: white;
		}
	}
`;

const Paginations = ({ productsPerPage, totalProducts, paginate }) => {
	const pageNumbers = [];
	for (let i = 1; i <= Math.ceil(totalProducts / productsPerPage); i++) {
		pageNumbers.push(i);
	}

	return (
		<div>
			<Ul>
				{pageNumbers.map((number, i) => {
					return (
						<Li key={i} className='myDIV'>
							<a className='page' onClick={() => paginate(number)}>
								{number}
							</a>
						</Li>
					);
				})}
			</Ul>
		</div>
	);
};

export default Paginations;
