import { Button, Container, Grid, makeStyles, TextField } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { Login } from '../Actions/UserActions';
import Loader from '../Components/Loader';
import Message from '../Components/Message';
import {Test} from '../Actions/Test'

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

const LoginScreen = ({ history, location }) => {
	const [ userName, setUserName ] = useState('');
	const [ password, setPassword ] = useState('');
	const classes = useStyles();
	const test = useStyles();
	const redirect = location.search ? location.search.split('=')[1] : '/';

	const dispatch = useDispatch();

	const userLogin = useSelector((state) => state.userLogin);
	const { loading, error, userInfo } = userLogin;

	useEffect(
		() => {
			if (userInfo) {
				history.push(redirect);
			}
		},
		[ history, userInfo, redirect ]
	);

	const submitHandler = (e) => {
		e.preventDefault();
		dispatch(Login(userName, password));
	};
	// const testSubmitHandler = (e) =>{
	// 	e.preventDefault();
	// 	dispatch(Test());
	// }
	return (
		<div style={{ marginTop: 100 }}>
			<h1 style={{ textAlign: 'center' }}>Sign In</h1>

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
						
						<Grid container>
							<Grid item>
								<Link
									to={redirect ? `/register?redirect=${redirect}` : '/register'}
									variant='body2'
									style={{ textDecoration: 'none', color: 'black' }}
								>
									{"Don't have an account? Sign Up"}
								</Link>
							</Grid>
						</Grid>
					</form>
					{/* <form className={classes.form} onSubmit={testSubmitHandler}>
					<Button type='submit' className={test.submit}>API Test</Button>
					</form> */}
				</div>
			</Container>
		</div>
	);
};

export default LoginScreen;
