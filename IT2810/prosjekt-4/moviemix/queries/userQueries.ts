import { gql } from "@apollo/client";

/**
 * Creating a user. The intention is to insert "[{}]" as input, since the only thing in user is id,
 * and it is auto-generated
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

/**
 * @param rating : star-rating value
 * @param description : string input from user
 * @param movieId : movie id
 * @param userId : string with auto-generated id
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
 * @param id: UserID, in asyncstorage
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
