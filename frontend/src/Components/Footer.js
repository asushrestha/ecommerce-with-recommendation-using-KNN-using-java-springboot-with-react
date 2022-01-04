import React from 'react';
import Container from '@material-ui/core/Container';
import { Grid } from '@material-ui/core';
import styled from 'styled-components';

const FooterGrid = styled(Grid)`
text-align:center;

`;
const Footer = () => {
	return (
		<footer>
			<Container maxWidth='xl' style={{ marginTop: 40 }}>
				<Grid item xs={12} sm={12} md={12} lg={12}>
					<FooterGrid item xs={12} sm={12} md={12} lg={12}>
						<p>Copyright &copy; Wardrobe</p>
					</FooterGrid>
				</Grid>
			</Container>
		</footer>
	);
};

export default Footer;
