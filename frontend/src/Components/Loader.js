import { CircularProgress } from '@material-ui/core';
import React from 'react';

const Loader = () => {
	return (
		<div>
			<CircularProgress size={40} color='primary' style={{ display: 'block', margin: 'auto' }} />
		</div>
	);
};

export default Loader;
