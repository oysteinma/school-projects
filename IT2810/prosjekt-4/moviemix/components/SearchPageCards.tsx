import { useReactiveVar } from "@apollo/client";
import { NavigationProp, ParamListBase } from "@react-navigation/native";
import { FlatList, View, StyleSheet } from "react-native";
import { Card } from "react-native-paper";
import { MovieCard } from "../components/MovieCard";
import { searchResultVar } from "../state/searchResultVariable";

/**
 *
 * @param navigation
 * @returns the list of search results
 */
export default function SearchPageCards({
  navigation,
}: {
  navigation: NavigationProp<ParamListBase>;
}) {
  const movies = useReactiveVar(searchResultVar);
  return (
    <View>
      {movies.length > 0 ? (
        <FlatList
          removeClippedSubviews={true}
          data={movies}
          renderItem={({ item }) => (
            <MovieCard movie={item} navigation={navigation} />
          )}
          showsVerticalScrollIndicator={false}
        ></FlatList>
      ) : (
        <View style={styles.container}>
          <Card style={styles.card}>
            <Card.Title titleStyle={styles.text} title="No Results 😢" />
          </Card>
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },
  text: {
    color: "white",
    textAlign: "center",
  },
  card: {
    width: 300,
    backgroundColor: "#252E40",
    elevation: 10,
  },
});
