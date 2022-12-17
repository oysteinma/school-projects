import { ApolloError } from "@apollo/client";
import Alert from "@mui/material/Alert";
import AlertTitle from "@mui/material/AlertTitle";
import Box from "@mui/material/Box";

/**
 * @param messsage : the error-message to display on LandingPage and YourRatings
 * @returns the ErrorMessage component, which warns of page errors
 */
export default function ErrorMessage({ message }: ApolloError) {
  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="80vh"
    >
      <Alert severity="error" variant="outlined">
        <AlertTitle color="white">Error</AlertTitle>
        <Box color={"white"}>
          {message} --- <strong>try to fix it!</strong>
        </Box>
      </Alert>
    </Box>
  );
}
