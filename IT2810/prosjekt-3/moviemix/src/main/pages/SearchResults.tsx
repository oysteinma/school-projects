import { Box } from "@mui/material";
import CardView from "../components/CardView";
import MovieMixHeader from "../components/MovieMixHeader";
import Search from "../components/Search";

/**
 *
 * @returns the SearchResults page, which displays the search results
 */
export default function SearchResults() {
  return (
    <Box
      display={"flex"}
      flexDirection={"column"}
      alignItems={"center"}
      alignContent={"center"}
      width={"50vw"}
      mx={"auto"}
      marginTop={"5vh"}
      marginBottom={"5vh"}
      gap={"5vh"}
    >
      <MovieMixHeader title="Search Results" />
      <Search />
      <CardView />
    </Box>
  );
}
