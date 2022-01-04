import { Button, Grid, List } from '@material-ui/core';
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { getUserList } from '../Actions/AdminActions';
import Loader from '../Components/Loader';
import Message from '../Components/Message';

const UserList = () => {
	const adminLogin = useSelector((state) => state.adminLogin);
	const { adminInfo } = adminLogin;

	const dispatch = useDispatch();

	const userList = useSelector((state) => state.userList);
	const { loading, error, users } = userList;


	useEffect(
		() => {
			dispatch(getUserList());
		},
		[ dispatch ]
	);

	return (
		<div style={{ marginTop: 20 }}>
			<Link to='/admindashboard' style={{ textDecoration: 'none' }}>
				<Button variant='outlined' color='primary'>
					Back
				</Button>
			</Link>
			{adminInfo ? (
				<div>
                    {loading? <Loader /> : error? <Message severity="error">{error}</Message>:<><h1>User Lists</h1>
					<Grid container>
						<Grid item md={12}>
							<Grid container>
								<Grid item md={1} style={{ textAlign: 'left' }}>
									<h3>ID</h3>
								</Grid>
								<Grid item md={2} style={{ textAlign: 'left' }}>
									<h3>Full Name</h3>
								</Grid>
								<Grid item md={3} style={{ textAlign: 'left' }}>
									<h3>Username</h3>
								</Grid>
								<Grid item md={2} style={{ textAlign: 'left' }}>
									<h3>Phone</h3>
								</Grid>
								<Grid item md={3} style={{ textAlign: 'left' }}>
									<h3>Added Date</h3>
								</Grid>
								<Grid item md={1} style={{ textAlign: 'left' }}>
									<h3>Gender</h3>
								</Grid>
							</Grid>

							{users.map((user) => (
								<Grid container>
									<Grid item md={1} style={{ textAlign: 'left' }}>
										{user.id}
									</Grid>
									<Grid item md={2} style={{ textAlign: 'left' }}>
										{user.fullName}
									</Grid>
									<Grid item md={3} style={{ textAlign: 'left' }}>
										{user.userName}
									</Grid>
									<Grid item md={2} style={{ textAlign: 'left' }}>
										{user.phone}
									</Grid>
									<Grid item md={3} style={{ textAlign: 'left' }}>
										{user.addedDate}
									</Grid>
									<Grid item md={1} style={{ textAlign: 'left' }}>
										{user.gender}
									</Grid>
								</Grid>
							))}
						</Grid>
					</Grid> </> }
					
				</div>
			) : (
				<Message severity='error'>Not Authorized</Message>
			)}
		</div>
	);
};

export default UserList;
