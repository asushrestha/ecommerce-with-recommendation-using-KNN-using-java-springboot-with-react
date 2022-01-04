import React, { useState } from 'react';

const SearchBox = ({ history }) => {
	const [ keyword, setKeyword ] = useState('');

	const submitHandler = (e) => {
		e.preventDefault();
		if (keyword.trim()) {
			history.push(`/search/${keyword}`);
		}
		else {
			history.push('/');
		}
	};
	return (
		<div>
			<form onSubmit={submitHandler}>
				<input
					type='text'
					name='q'
					onChange={(e) => setKeyword(e.target.value)}
					placeholder='search product...'
				/>
				<button type='submit'>Search</button>
			</form>
		</div>
	);
};

export default SearchBox;
