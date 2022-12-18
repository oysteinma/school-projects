import { gql } from "@apollo/client";

/**
 * @param rating : stjerne-rating verdi
 * @param description : string input fra brukeren
 * @param movieId : filmens id
 * @param userId : string med autogenerert brukerid
 */
export const CREATE_RATING = gql`
  mutation ($rating: Float, $description: String, $movieId: Int, $userId: ID!) {
    createRatings(
      input: {
        rating: $rating
        description: $description
        movie: { connect: { where: { node: { id: $movieId } } } }
        user: { connect: { where: { node: { id: $userId } } } }
      }
    ) {
      info {
        nodesCreated
      }
    }
  }
`;
/**
 * @param id: userId, lagret i localStorage
 */
export const GET_RATINGS_BY_USER = gql`
  query getUserRatings($id: ID) {
    users(where: { id: $id }) {
      ratings {
        description
        rating
        movie {
          title
          poster_url
        }
      }
    }
  }
`;
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
      title
      averageRating
      tagline
      released
      poster_url
      director {
        name
      }
    }
  }
`;

/**
 * This would have been the optimal solution
 * @param direction : string ASC eller DESC
 * @param searchString : string input fra brukeren, det som søkes etter
 * @param genre : string Én genre med stor forbokstav
 * @param sort : string Hva du vil sortere på.
 *  Basert på navnene på feltene i databasen, f.eks. "popularity" eller "averageRating"
 */
// export const GET_SEARCH_RESULT = gql`
//   query getSearchResult(
//     $direction: String
//     $searchString: String
//     $genre: String
//     $sort: String
//   ) {
//     fuzzyMoviesSearch(
//       direction: $direction
//       searchString: $searchString
//       genre: $genre
//       sort: $sort
//     ) {
//       title
//       averageRating
//       tagline
//       released
//       poster_url
//       director {
//         name
//       }
//     }
//   }
// `;

/**
 * Oppretter en bruker. Meningen er å sette inn "[{}]" som input, siden det eneste i bruker er id,
 * og det er autogenerert
 */
export const CREATE_USER = gql`
  mutation Mutation($input: [UserCreateInput!]!) {
    createUsers(input: $input) {
      users {
        id
      }
    }
  }
`;
