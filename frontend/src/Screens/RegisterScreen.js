import { Button, Container, Grid, makeStyles, TextField } from '@material-ui/core';
import Alert from '@material-ui/lab/Alert';
import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import { Register } from '../Actions/UserActions';
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

const RegisterScreen = ({ history, location }) => {
	const [ userName, setUserName ] = useState('');
	const [ fullName, setFullName ] = useState('');
	const [ address, setAddress ] = useState('');
	const [ dob, setDob ] = useState('');
	const [ gender, setGender ] = useState('');
	const [ phone, setPhone ] = useState('');
	const [ password, setPassword ] = useState('');
	const classes = useStyles();

	const redirect = location.search ? location.search.split('=')[1] : '/';

	const dispatch = useDispatch();

	const userRegister = useSelector((state) => state.userRegister);
	const { loading, error, userInfo } = userRegister;

	//useEffect(
	//	() => {
	//		if (userInfo) {
	//			history.push(redirect);
	//		}
	//	},
	//	[ history, userInfo, redirect ]
	//);

	const submitHandler = async(e) => {
		e.preventDefault();
try{
	await dispatch(Register(fullName, address, dob, phone, gender, userName, password));
}
catch(err)
{
	
}
		
		
	};
	return (
		<div>
			<h1 style={{ textAlign: 'center', marginTop: 100 }}>Sign Up</h1>

			<Container component='main' maxWidth='xs' style={{ marginTop: -70 }}>
				<div className={classes.paper}>
					<form className={classes.form} onSubmit={submitHandler} noValidate>
						{error && <Message severity='error'>{error}</Message>}
						{loading && <Loader />}
						{userInfo ? <Message severity='success'>{userInfo.status}</Message> : null}
						<TextField
							variant='outlined'
							margin='normal'
							required
							fullWidth
							id='fullname'
							label='Full Name'
							name='fullname'
							autoComplete='fullname'
							value={fullName}
							onChange={(e) => setFullName(e.target.value)}
							autoFocus
						/>
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
							id='address'
							label='Address'
							name='address'
							autoComplete='address'
							value={address}
							onChange={(e) => setAddress(e.target.value)}
							autoFocus
						/>
						<TextField
							variant='outlined'
							margin='normal'
							required
							fullWidth
							id='dob'
							label='Date of Birth'
							name='dob'
							autoComplete='dob'
							value={dob}
							onChange={(e) => setDob(e.target.value)}
							autoFocus
						/>
						<TextField
							variant='outlined'
							margin='normal'
							type='Number'
							required
							fullWidth
							id='phone'
							label='phone'
							name='phone'
							autoComplete='phone'
							value={phone}
							onChange={(e) => setPhone(e.target.value)}
							autoFocus
						/>
						<TextField
							variant='outlined'
							margin='normal'
							required
							fullWidth
							id='gender'
							label='gender'
							name='gender'
							autoComplete='gender'
							value={gender}
							onChange={(e) => setGender(e.target.value)}
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
						Sign Up
						</Button>

						<Grid container>
							<Grid item>
								<Link
									to={redirect ? `/login?redirect=${redirect}` : '/login'}
									variant='body2'
									style={{ textDecoration: 'none', color: 'black' }}
								>
									{'Already have account? Sign In'}
								</Link>
							</Grid>
						</Grid>
					</form>
				</div>
			</Container>
		</div>
	);
};

export default RegisterScreen;
