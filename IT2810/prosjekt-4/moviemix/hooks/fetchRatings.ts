import { useQuery, useReactiveVar } from "@apollo/client";
import { GET_RATINGS_BY_USER } from "../queries/userQueries";
import { ratingsVariable } from "../state/ratingsVariable";
import { userIdVar } from "../state/userVariable";

/**
 * Fetches ratings from the Database
 */
export const fetchRatings = () => {
  const { data } = useQuery(GET_RATINGS_BY_USER, {
    variables: {
      id: useReactiveVar(userIdVar),
    },
    fetchPolicy: "network-only",
  });
  ratingsVariable(data !== undefined ? data.users[0].ratings : []);
};
