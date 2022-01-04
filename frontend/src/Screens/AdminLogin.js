import { Button, Container, makeStyles, TextField } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Admin } from '../Actions/AdminActions';
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

const AdminScreen = ({ history, location }) => {
	const [ userName, setUserName ] = useState('');
	const [ password, setPassword ] = useState('');
	const classes = useStyles();

	const redirect = location.search ? location.search.split('=')[1] : '/';

	const dispatch = useDispatch();

	const adminLogin = useSelector((state) => state.adminLogin);
	const { loading, error, adminInfo } = adminLogin;

	useEffect(
		() => {
			if (adminInfo) {
				history.push(redirect);
			}
		},
		[ history, adminInfo, redirect ]
	);

	const submitHandler = (e) => {
		e.preventDefault();
		dispatch(Admin(userName, password));
	};
	return (
		<div>
			<h1 style={{ textAlign: 'center', marginTop: 100 }}>Sign In as Admin</h1>

			{loading && <Loader />}
			<Container component='main' maxWidth='xs' style={{ marginTop: -70 }}>
				<div className={classes.paper}>
					<form className={classes.form} onSubmit={submitHandler} noValidate>
						{error && <Message severity='error'>{error}</Message>}
						<TextField
							variant='outlined'
							margin='normal'
							required
							fullWidth
							id='username'
							label='Username'
							name='username'
							autoComplete='username'
							value={userName}
							onChange={(e) => setUserName(e.target.value)}
							autoFocus
						/>
						<TextField
							variant='outlined'
							margin='normal'
							required
							fullWidth
							name='password'
							label='Password'
							type='password'
							id='password'
							value={password}
							onChange={(e) => setPassword(e.target.value)}
						/>

						<Button type='submit' fullWidth variant='contained' color='primary' className={classes.submit}>
							Sign In
						</Button>
					</form>
				</div>
			</Container>
		</div>
	);
};

export default AdminScreen;
