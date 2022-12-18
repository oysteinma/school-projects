import { gql } from "@apollo/client";

/**
 * Returns list of movies based on filters, sorting, limit and offset.
 */
export const GET_MOVIES = gql`
  query getMovies(
    $genre: String
    $sort: [MovieSort!]
    $limit: Int
    $offset: Int
  ) {
    movies(
      where: { genres_SOME: { name_CONTAINS: $genre } }
      options: { sort: $sort, limit: $limit, offset: $offset }
    ) {
      title
      id
      averageRating
      tagline
      released
      poster_url
      actors {
        name
      }
      director {
        name
      }
    }
  }
`;

/**
 * Returns a list of movies, based on search string.
 */
export const GET_SEARCH_RESULT = gql`
  query getSearchResult($searchString: String) {
    fuzzyMoviesSearch(searchString: $searchString) {
      id
      title
      averageRating
      tagline
      released
      poster_url
      actors {
        name
      }
      director {
        name
      }
    }
  }
`;
