import { Box, Divider } from "@mui/material";
import React from "react";

/**
 *  A simple footer, in the form of a React Class component
 */

class Footer extends React.Component {
  render() {
    return (
      <footer>
        <Divider style={{ backgroundColor: "white" }} />
        <Box color="white" textAlign="center" marginTop={1} marginBottom={1}>
          GitView &reg; {new Date().getFullYear()}
        </Box>
        <Divider style={{ backgroundColor: "white" }} />
      </footer>
    );
  }
}

export default Footer;
