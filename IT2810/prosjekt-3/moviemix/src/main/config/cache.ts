import { InMemoryCache } from "@apollo/client";

/**
 * Standard setup for merging old query results with new query results,
 * to support endless scrolling
 */
export const cache: InMemoryCache = new InMemoryCache({
  typePolicies: {
    Query: {
      fields: {
        movies: {
          keyArgs: false,
          merge(existing = [], incoming) {
            return [...existing, ...incoming];
          },
        },
      },
    },
  },
});
