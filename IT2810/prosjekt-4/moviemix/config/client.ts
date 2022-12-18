import { ApolloClient, NormalizedCacheObject } from "@apollo/client";

import { cache } from "./cache";

/**
 * Setup Apollo Client
 */
export const client: ApolloClient<NormalizedCacheObject> = new ApolloClient({
  cache: cache,
  uri: "https://it2810-59s.idi.ntnu.no/graphql",
});
