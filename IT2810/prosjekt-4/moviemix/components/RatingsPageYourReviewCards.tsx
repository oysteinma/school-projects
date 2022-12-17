import { useReactiveVar } from "@apollo/client";
import { useScrollToTop } from "@react-navigation/native";
import { useRef } from "react";
import { FlatList, View, StyleSheet } from "react-native";
import { Card } from "react-native-paper";
import { fetchRatings } from "../hooks/fetchRatings";
import { ratingsVariable } from "../state/ratingsVariable";
import { YourReviewCard } from "./YourReviewCard";

/**
 *
 * @returns the list of your reviews
 */
export default function RatingsPageYourReviewCards() {
  fetchRatings();
  const allRatings = useReactiveVar(ratingsVariable);
  const ref = useRef(null);
  useScrollToTop(ref);

  return (
    <View>
      {allRatings === undefined || allRatings.length === 0 ? (
        <View style={styles.container}>
          <Card style={styles.card}>
            <Card.Title titleStyle={styles.text} title="No reviews 😢" />
          </Card>
        </View>
      ) : (
        <FlatList
          data={allRatings}
          ref={ref}
          renderItem={({ item }) => <YourReviewCard rating={item} />}
          removeClippedSubviews={true}
          showsVerticalScrollIndicator={false}
        ></FlatList>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    margin: 15,
  },
  card: {
    width: 300,
    backgroundColor: "#252E40",
    elevation: 10,
  },
  text: {
    color: "white",
    fontSize: 20,
    textAlign: "center",
  },
});
