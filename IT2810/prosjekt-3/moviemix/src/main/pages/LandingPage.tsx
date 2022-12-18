import Box from "@mui/material/Box";
import { useQuery } from "@apollo/client";
import { GET_MOVIES } from "../queries/query";
import Search from "../components/Search";
import MovieMixHeader from "../components/MovieMixHeader";
import ErrorMessage from "../components/ErrorMessage";
import CardView from "../components/CardView";
import { Filter } from "../components/Filter";
import { useState } from "react";
import { moviesVar } from "../App";

/**
 * Gets the movies from the database and fetches more when scrolling to the bottom
 * @returns the LandingPage page, which displays the main page of the app
 */

export default function LandingPage() {
  const [limit, setLimit] = useState<number>(12);
  const [genre, setGenre] = useState<string>("");
  const [sortRating, setSortRating] = useState<string>("DESC");
  const [sortPopularity, setSortPopularity] = useState<string>("DESC");
  const [filter, setFiltering] = useState<string>("bothEnabled");

  const { error, refetch, fetchMore } = useQuery(GET_MOVIES, {
    variables:
      filter === "bothEnabled"
        ? {
            genre: genre,
            offset: 0,
            limit: limit,
            sort: { popularity: sortPopularity, averageRating: sortRating },
          }
        : filter === "onlyRating"
        ? // Needs to have standard value for popularity since several movies have the same rating
          {
            genre: genre,
            offset: 0,
            limit: limit,
            sort: { popularity: "DESC", averageRating: sortRating },
          }
        : {
            // ONLY POPULARITY
            genre: genre,
            offset: 0,
            limit: limit,
            sort: { popularity: sortPopularity },
          },
  });
  refetch().then((r) => {
    moviesVar(r.data.movies);
  });

  if (error) return <ErrorMessage {...error} />;

  window.onscroll = function LoadMoreContent() {
    if (window.scrollY + window.innerHeight >= document.body.scrollHeight) {
      const currentLength = moviesVar().length;
      fetchMore({
        variables: { offset: currentLength, limit: 12 },
      }).then((r) => {
        setLimit(currentLength + r.data.movies.length);
      });
    }
  };

  return (
    <Box
      display={"flex"}
      id="container"
      flexDirection={"column"}
      alignItems={"center"}
      alignContent={"center"}
      width={"50vh"}
      mx={"auto"}
      marginTop={"5vh"}
      marginBottom={"5vh"}
      gap={"5vh"}
    >
      <MovieMixHeader title="MovieMix" />
      <Search />
      <Filter
        onGenreChange={setGenre}
        onRatingChange={setSortRating}
        onPopularityChange={setSortPopularity}
        onFilteringChange={setFiltering}
        onLimitChange={setLimit}
        sortRating={sortRating}
        sortPopularity={sortPopularity}
      />
      <CardView />
    </Box>
  );
}
