import { Button, Container, Grid, List, ListItem, makeStyles, TextField } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { getUserDetails } from '../Actions/UserActions';
import Loader from '../Components/Loader';
import Message from '../Components/Message';

const useStyles = makeStyles((theme) => ({
	paper: {
		marginTop: theme.spacing(8),
		display: 'flex',
		flexDirection: 'column',
		alignItems: 'center',
	},

	form: {
		width: '100%',
		marginTop: theme.spacing(1),
	},
	submit: {
		margin: theme.spacing(3, 0, 2),
	},
}));

const ProfileScreen = ({ history, location }) => {
	const classes = useStyles();

	const dispatch = useDispatch();

	const userLogin = useSelector((state) => state.userLogin);
	const { userInfo } = userLogin;

	const userDetails = useSelector((state) => state.userDetails);
	const { user, loading, error } = userDetails;

	useEffect(
		() => {
			if (!userInfo) {
				history.push('/login');
			}
			else {
				if (!user.userName) {
					dispatch(getUserDetails('profile'));
				}
			}
		},
		[ history, userInfo, dispatch ]
	);
	console.log(user.userName);
	return (
		<div style={{ marginTop: 100 }}>
			{loading && <Loader />}
			{error && <Message severity='error'>{error}</Message>}
			<Grid container style={{ marginTop: -70 }}>
				<Grid item xs={12} md={4}>
					<div className={classes.paper}>
						{loading && <Loader />}
						<h1>Your Profile</h1>
						<List>
							<ListItem>Full Name: {user.fullName}</ListItem>
							<ListItem>Username: {user.userName}</ListItem>
							<ListItem>Gender: {user.gender}</ListItem>
							<ListItem>Address: {user.address}</ListItem>
							<ListItem>Date of Birth: {user.dob}</ListItem>
							<ListItem>Phone No.: {user.phone}</ListItem>
						</List>
					</div>
				</Grid>
			</Grid>
		</div>
	);
};

export default ProfileScreen;
