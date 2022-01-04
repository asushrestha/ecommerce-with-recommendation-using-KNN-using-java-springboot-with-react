import { Grid, Typography } from '@material-ui/core';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import Message from '../Components/Message';
import {getAdminStat }from "../Actions/AdminActions";

const StyledButton = styled.button`
	background: #f44336;
	cursor: pointer;
	color: white;
	border: none;
	width: 100%;
	height: 20vh;
	font-size: 20px;
	transition: all 0.5s ease-in-out;
	text-transform: uppercase;
	&:hover {
		background: #b71c1c;
	}
`;

const SquareCard = ({adminSt,children,currency,time}) => {
	console.log(adminSt)
	return(
		<div style={{display:"flex",flexDirection:'column',justifyContent:"center	",alignItems:"center", width:"150px",height:"150px",backgroundColor:'tomato',marginTop:"10%",color:'#ffffff'}}>
			<p 	>{children}</p>
			<h2 style={{marginTop:time?"-20px":""}}>{currency}{adminSt}</h2>
			<p style={{marginTop:'-20px'}}>{time}</p>
		</div>
	)
}

const AdminDashboard = () => {
		const adminLogin = useSelector((state) => state.adminLogin);
		const { adminInfo } = adminLogin;

		const dispatch = useDispatch();
		const adminStat = useSelector((state )=> state.adminStat);
		const {adminStats} = adminStat;

		console.log(adminStats)

		useEffect(()=>{
			dispatch(getAdminStat())
		},[])


	return (		
		<>
		<div style={{display:'flex', justifyContent:'space-around',alignItems:'center'}}>
			<SquareCard	 adminSt={adminStats?.maleUserCount}>Male Users</SquareCard>

			<SquareCard	 adminSt={adminStats?.femaleUserCount}>Female Users</SquareCard>

			<SquareCard	 adminSt={adminStats?.totalUsers}>Total Users</SquareCard>

			<SquareCard	 adminSt={adminStats?.checkedOutCount} time="Today">Transaction Count</SquareCard>

			<SquareCard  currency="Rs."	 adminSt={adminStats?.checkOutTotalAmount} time="Today">Transactions</SquareCard>

		</div>

		<div style={{ marginTop: 100 }}>
			{adminInfo ? (
				<Grid container>
					<Grid item md={4} style={{ padding: 20 }}>
						<Link to='/userlist'>
							<StyledButton>User List</StyledButton>
						</Link>
					</Grid>
					<Grid item md={4} style={{ padding: 20 }}>
						<Link to='/productlist'>
							<StyledButton>Product List</StyledButton>
						</Link>
					</Grid>
					<Grid item md={4} style={{ padding: 20 }}>
						<Link to='/addproduct'>
							<StyledButton>Add Product</StyledButton>
						</Link>
					</Grid>
				</Grid>
			) : (
				<Message severity='error'>Not Authorized!</Message>
			)}
		</div>
		</>
	);
};

export default AdminDashboard;
