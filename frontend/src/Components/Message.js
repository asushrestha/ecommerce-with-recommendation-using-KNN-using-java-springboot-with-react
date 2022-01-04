import React from 'react';
import Alert from '@material-ui/lab/Alert';

const Message = ({ severity, children }) => {
	return (
		<div>
			<Alert severity={severity}>{children}</Alert>
		</div>
	);
};

export default Message;
