import { useQuery } from "@apollo/client";
import Loading from "../components/Loading";
import ErrorMessage from "../components/ErrorMessage";
import { GET_RATINGS_BY_USER } from "../queries/query";
import MovieMixHeader from "../components/MovieMixHeader";
import { Box } from "@mui/material";
import ListView from "../components/ListView";

/**
 *
 * @returns the YourRatings page, which displays the movies the user has rated
 * or none if the user has not rated any movies
 */
export default function YourRatings() {
  const { loading, error, data } = useQuery(GET_RATINGS_BY_USER, {
    variables: {
      id: window.localStorage.getItem("userId"),
    },
    fetchPolicy: "network-only",
  });

  if (loading) return <Loading />;
  if (error) return <ErrorMessage {...error} />;

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
      <MovieMixHeader title="Your Ratings" />
      <ListView {...data} />
    </Box>
  );
}
