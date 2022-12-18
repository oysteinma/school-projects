import { useReactiveVar } from "@apollo/client";
import { FlatList, View } from "react-native";
import { moviesVar } from "../state/moviesVariable";
import { fetchMovies } from "../hooks/fetchMovies";
import { MovieCard } from "../components/MovieCard";
import {
  NavigationProp,
  ParamListBase,
  useScrollToTop,
} from "@react-navigation/native";
import { useRef } from "react";
import SearchField from "./SearchField";

/**
 *
 * @param navigation
 * @returns the front page movie cards
 */
export default function FrontPageMovieCards({
  navigation,
}: {
  navigation: NavigationProp<ParamListBase>;
}) {
  const movies = useReactiveVar(moviesVar);
  const { loadMore } = fetchMovies();
  const ref = useRef(null);
  useScrollToTop(ref);

  return (
    <View>
      <FlatList
        removeClippedSubviews={true}
        ListHeaderComponent={<SearchField navigation={navigation} />}
        data={movies}
        ref={ref}
        renderItem={({ item }) => (
          <MovieCard movie={item} navigation={navigation} />
        )}
        showsVerticalScrollIndicator={false}
        onEndReachedThreshold={0.5}
        onEndReached={loadMore}
      ></FlatList>
    </View>
  );
}
