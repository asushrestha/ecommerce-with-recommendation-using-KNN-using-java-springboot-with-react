import { Menu, MenuItem } from '@material-ui/core';
import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link, Route } from 'react-router-dom';
import styled from 'styled-components';
import { adminLogout } from '../Actions/AdminActions';
import { logout } from '../Actions/UserActions';
import NavItems from '../Files/NavItems';
import './Header.scss';
import { withRouter } from 'react-router';
import img1 from './logo.png';

const StyledHeader = styled.header`
	display: flex;
	position: fixed;
	z-index: 99;
	top: 0;
	width: 100%;
	align-items: center;
	justify-content: center;
	background-color: #212121;
	height: 80px;
	font-family: 'Poppins';
`;
const StyledOuterDiv = styled.div`
	width: 80%;
	margin: auto;
	display: flex;
	justify-content: space-between;
	align-items: center;
`;

const Ul = styled.ul`
	color: white;
	cursor: pointer;
	display: grid;
	grid-template-columns: repeat(5, auto);
	grid-gap: 0;
	list-style: none;
`;

const Li = styled.li`
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 15px 10px;
	font-size: 15px;
	margin-top: -3vh;
	&:hover {
		background-color: #ffb300;
		color: black;
		transition: all 1s ease;
	}
`;

const Header = ({ history }) => {
	const [ kids, setKids ] = useState('kids');
	const [ men, setMen ] = useState('men');
	const [ search, setSearch ] = useState('');
	const [ women, setWomen ] = useState('women');
	const [ dress, setDress ] = useState('dress');
	const [ traditional, setTraditional ] = useState('traditional');
	const [ upper, setUpper ] = useState('upper');

	const [ anchorEl, setAnchorEl ] = useState(null);

	const handleClick = (event) => {
		setAnchorEl(event.currentTarget);
	};

	const handleClose = () => {
		setAnchorEl(null);
	};

	const dispatch = useDispatch();
	const userLogin = useSelector((state) => state.userLogin);
	const { userInfo } = userLogin;

	const adminLogin = useSelector((state) => state.adminLogin);
	const { adminInfo } = adminLogin;

	const logoutHandler = () => {
		dispatch(logout());
	};
	const adminLogoutHandler = () => {
		dispatch(adminLogout());
	};

	const changeHandler = (e) => {
		history.push(`/search/${e.target.value}?keyword=${e.target.value}`);
		setSearch(e.target.value);
	};
	console.log(search);
	return (
		<StyledHeader>
			<StyledOuterDiv>
				<div style={{ color: 'white', cursor: 'pointer', marginTop: '-3vh' }}>
					<Link
						to='/'
						style={{
							textDecoration: 'none',
							color: 'white',
							display: 'flex',
							justifyContent: 'center',
							alignItems: 'center',
						}}
					>
						<img src={img1} style={{ height: '30px', width: 'auto' }} />
					</Link>
				</div>
				<div>
					<Ul>
						{NavItems.map((NavItem, index) => {
							return (
								<div key={index}>
									<Link
										key={index}
										to={NavItem.link}
										style={{ textDecoration: 'none', color: 'white' }}
									>
										<Li key={index}>
											<i className={NavItem.icon} />
											<p style={{ paddingLeft: 5 }}>{NavItem.title}</p>
										</Li>
									</Link>
								</div>
							);
						})}
						{/*<Li className='menu'>
							<i className='fas fa-bars' />
							<p style={{ paddingLeft: 5 }}>Category</p>
							<ul>
								<li class='link'>
									<a href=''>Im a link</a>
								</li>
								<li class='link'>
									<a href=''>Im a link</a>
								</li>
								<li>
									Nested dropdown
									<ul>
										<li class='link'>
											<a href=''>Im a link</a>
										</li>
										<li class='link'>
											<a href=''>Im a link</a>
										</li>
									</ul>
								</li>
							</ul>
						</Li>*/}
						<div className='menu'>
							<ul>
								<li>
									<i className='fas fa-bars' style={{ paddingRight: 5 }} />
									Categories
									<ul>
										<li>
											<Link to={`/filter/${kids}`}>Kids</Link>

											<i className='fas fa-angle-right' style={{ paddingLeft: 50 }} />
											<ul>
												<li className='link'>
													<Link to={`/filter/${kids}?subCategory=${dress}`}>Dress</Link>
												</li>
												<li className='link'>
													<Link to={`/filter/${kids}?subCategory=${traditional}`}>
														Traditional
													</Link>
												</li>
												<li className='link'>
													<Link to={`/filter/${kids}?subCategory=${upper}`}>Upper</Link>
												</li>
											</ul>
										</li>
										<li>
											<Link to={`/filter/${men}`}>Men</Link>
											<i className='fas fa-angle-right' style={{ paddingLeft: 50 }} />
											<ul>
												<li className='link'>
													<Link to={`/filter/${men}?subCategory=${dress}`}> Dress</Link>
												</li>
												<li className='link'>
													<Link to={`/filter/${men}?subCategory=${traditional}`}>
														Traditional
													</Link>
												</li>
												<li className='link'>
													<Link to={`/filter/${men}?subCategory=${upper}`}> Upper</Link>
												</li>
											</ul>
										</li>
										<li>
											<Link to={`/filter/${women}`}>Women</Link>
											<i className='fas fa-angle-right' style={{ paddingLeft: 20 }} />
											<ul>
												<li className='link'>
													<Link to={`/filter/${women}?subCategory=${dress}`}> Dress</Link>
												</li>
												<li className='link'>
													<Link to={`/filter/${women}?subCategory=${traditional}`}>
														Traditional
													</Link>
												</li>
												<li className='link'>
													<Link to={`/filter/${women}?subCategory=${upper}`}> Upper</Link>
												</li>
											</ul>
										</li>
									</ul>
								</li>
							</ul>
						</div>
					</Ul>
				</div>
				<div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: -15 }}>
					<input
						type='text'
						id='search'
						name='search'
						label='Search'
						value={search}
						onChange={changeHandler}
						style={{ height: 30, width: 220 }}
					/>
				</div>
				<div>
					<Ul>
						{adminInfo ? null : (
							<Link to='/cart' style={{ textDecoration: 'none', color: 'white' }}>
								<Li>
									<i className='fas fa-shopping-cart' /> <p style={{ paddingLeft: 5 }}>Cart</p>
								</Li>
							</Link>
						)}

						{userInfo ? (
							<div>
								<Li onClick={handleClick}>
									<i className='fas fa-user' />
									<p style={{ paddingLeft: 5 }}>{userInfo.userName}</p>
								</Li>
								<Menu
									id='simple-menu'
									anchorEl={anchorEl}
									keepMounted
									open={Boolean(anchorEl)}
									onClose={handleClose}
									style={{ display: 'block' }}
								>
									<MenuItem>
										<Link
											to='/profile'
											style={{ textDecoration: 'none', color: 'black', display: 'block' }}
										>
											Profile
										</Link>
									</MenuItem>

									<MenuItem onClick={logoutHandler}>Logout</MenuItem>
								</Menu>
							</div>
						) : adminInfo ? (
							<div>
								<Li onClick={handleClick}>
									<i className='fas fa-user' />
									<p style={{ paddingLeft: 5 }}>{adminInfo.userName}</p>
								</Li>
								<Menu
									id='simple-menu'
									anchorEl={anchorEl}
									keepMounted
									open={Boolean(anchorEl)}
									onClose={handleClose}
									style={{ display: 'block' }}
								>
									<MenuItem>
										<Link
											to='/admindashboard'
											style={{ textDecoration: 'none', color: 'black', display: 'block' }}
										>
											Dashboard
										</Link>
									</MenuItem>

									<MenuItem onClick={adminLogoutHandler}>Logout</MenuItem>
								</Menu>
							</div>
						) : (
							<Link to='/login' style={{ textDecoration: 'none', color: 'white' }}>
								<Li>
									<i className='fas fa-sign-in-alt' /> <p style={{ paddingLeft: 5 }}>Login</p>
								</Li>
							</Link>
						)}

						{userInfo ? null : adminInfo ? null : (
							<Link to='/register' style={{ textDecoration: 'none', color: 'white' }}>
								<Li>
									<i className='fas fa-user-plus' /> <p style={{ paddingLeft: 5 }}>Register</p>
								</Li>
							</Link>
						)}
					</Ul>
				</div>
			</StyledOuterDiv>
		</StyledHeader>
	);
};

export default withRouter(Header);
