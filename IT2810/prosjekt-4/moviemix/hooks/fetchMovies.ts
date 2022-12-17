import { useQuery, ApolloQueryResult, useReactiveVar } from "@apollo/client";
import { GET_MOVIES } from "../queries/movieQueries";
import { useEffect } from "react";
import { moviesVar } from "../state/moviesVariable";
import { limitVar } from "../state/limitVariable";
import { toggleTrendingVar } from "../state/toggleTrendingVariable";
import { toggleRatingVar } from "../state/toggleRatingVariable";
import { toggleVar } from "../state/toggleVariable";
import { genreVar } from "../state/genreVariable";

/**
 * Fetches movies from the Database
 * @returns loadMore: Function to load more movies
 */
export const fetchMovies = () => {
  const limit = useReactiveVar(limitVar);
  const trending = useReactiveVar(toggleTrendingVar);
  const rating = useReactiveVar(toggleRatingVar);
  const toggleSwitch = useReactiveVar(toggleVar);
  const genre = useReactiveVar(genreVar);

  const { refetch, fetchMore } = useQuery(GET_MOVIES, {
    variables:
      toggleSwitch === 1
        ? {
            genre: genre,
            offset: 0,
            limit: limit,
            sort: { popularity: trending },
          }
        : {
            genre: genre,
            offset: 0,
            limit: limit,
            sort: { averageRating: rating, popularity: "DESC" },
          },
  });

  useEffect(() => {
    refetch()
      .then((result: ApolloQueryResult<any>) => {
        moviesVar(result.data.movies);
      })
      .catch((error: string) => {
        console.log("Something went wrong while loading movies:", error);
      });
  }, [limitVar(), toggleTrendingVar(), toggleRatingVar(), genreVar()]);

  const loadMore = () => {
    const currentLength = moviesVar().length;
    fetchMore({
      variables: { offset: currentLength, limit: 6 },
    }).then((result) => {
      limitVar(currentLength + result.data.movies.length);
    });
  };

  return { loadMore };
};
