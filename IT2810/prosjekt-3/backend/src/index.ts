import { ApolloServer } from "@apollo/server";
import { startStandaloneServer } from "@apollo/server/standalone";
import { Neo4jGraphQL } from "@neo4j/graphql";
import neo4j from "neo4j-driver";
import { RESTDataSource } from "@apollo/datasource-rest";

// noinspection GraphQLMissingType
const typeDefs = `#graphql

type Movie {
    id: Int @unique
    title: String
    averageRating: Float
    overview: String
    popularity: Float
    released: String
    tagline: String
    director: [Director!]! @relationship(type: "HAS_DIRECTOR", direction: OUT)
    genres: [Genre!]! @relationship(type: "HAS_GENRES", direction: OUT)
    actors: [Actor!]! @relationship(type: "HAS_ACTOR", direction: OUT)
    poster_url: String
}

type Actor {
    name: String @unique
}

type Genre {
    name: String @unique
}

type Director {
    name: String @unique
}

type Rating {
    id: ID! @id(autogenerate: true)
    rating: Float
    description: String
    movie: Movie! @relationship(type: "HAS_RATED", direction: IN)
    user: User! @relationship(type: "HAS_RATING", direction : IN)

}
type User {
    id: ID! @id(autogenerate: true)
    ratings: [Rating!]! @relationship(type: "HAS_RATING", direction: OUT)
}
enum SortDirection {
    ASC
    DESC
}

input MovieSort {
    popularity: SortDirection
    averageRating: SortDirection
}

input MovieOptions {
    sort: [MovieSort]!
    limit: Int
    offset: Int
}

type Query {
    moviePoster(id: String): String
    movies(where: MovieWhere, options: MovieOptions): [Movie!]!
    movie(id: Int): Movie
    
    fuzzyMoviesSearch(searchString: String): [Movie!]! @cypher(
        statement: """
            CALL db.index.fulltext.queryNodes(
            'movieSearchIndex', $searchString+'~')
            YIELD node
            RETURN distinct node
            LIMIT 16
        """
    )
} 
`;

// Dette er slik vi kunne ønsket å implementere FuzzySearch
// Hvis vi hadde hatt mer tid

// fuzzyMoviesSearch(searchString: String, genre: String, sort: String, direction: String): [Movie!]! @cypher(
//         statement: """
//             CALL db.index.fulltext.queryNodes(
//             'movieSearchIndex', $searchString+'~')
//             YIELD node
//             MATCH (node)-[r:HAS_GENRES]->(g:Genre)
//             WHERE (g.name=$genre or $genre is NULL)
//             RETURN distinct node
//             ORDER BY
//             CASE WHEN $direction = \\"ASC\\" THEN node[$sort] ELSE null END ASC,
//             CASE WHEN $direction = \\"ASC\\" THEN null ELSE node[$sort] END DESC
//             LIMIT 16
//         """

/**
 * Endte opp med å være ubrukt, egentlig designet for å hente bilder.
 * Endte opp med å laste opp alle bildene til database med en gang
 */
class TmdbApi extends RESTDataSource {
  override baseURL = "https://api.themoviedb.org/3/";
  // Just to hide the API key
  apiKey = "<API_KEY>";

  async moviePoster(id): Promise<String> {
    const data = await this.get(
      `movie/${encodeURIComponent(id)}?api_key=${encodeURIComponent(
        this.apiKey
      )}`
    );
    return "https://image.tmdb.org/t/p/w185" + data.poster_path;
  }
}

interface ContextValue {
  dataSources: {
    tmdbApi: TmdbApi;
  };
}

const resolvers = {
  Query: {
    moviePoster: async (_, { id }, { dataSources }) => {
      return dataSources.tmdbApi.moviePoster(id);
    },
    movie(source) {
      return `${source.id()}`;
    },
  },
};

const driver = neo4j.driver(
  "bolt://localhost:7687",
  // Should be changed to use environment variables,
  // but were not able because of school constraints.
  neo4j.auth.basic("<USERNAME>", "<PASSWORD>")
);

const neoSchema = new Neo4jGraphQL({ typeDefs, driver, resolvers });

neoSchema.getSchema().then(async (schema) => {
  const server = new ApolloServer({
    schema,
  });
  const { url } = await startStandaloneServer(server, {
    context: async () => {
      const { cache } = server;
      return {
        dataSources: {
          tmdbApi: new TmdbApi({ cache }),
        },
      };
    },
    listen: { port: 4000 },
  });
  console.log(`🚀  Server ready at: ${url}`);
});
