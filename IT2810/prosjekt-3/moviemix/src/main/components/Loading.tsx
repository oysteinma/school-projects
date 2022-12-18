import Box from "@mui/material/Box";
import CircularProgress from "@mui/material/CircularProgress";

/**
 * @returns the Loading component, which displays a loading circle.
 */
export default function Loading() {
  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="80vh"
    >
      <CircularProgress size="5rem" />
    </Box>
  );
}
