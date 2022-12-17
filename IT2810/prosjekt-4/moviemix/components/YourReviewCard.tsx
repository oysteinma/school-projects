import { View, StyleSheet } from "react-native";
import { Card, Title, Paragraph } from "react-native-paper";
import { AntDesign } from "@expo/vector-icons";
import { IRating } from "../model/Interfaces";

/**
 *
 * @param rating
 * @returns the your review card for a single movie
 */
export const YourReviewCard = ({ rating }: { rating: IRating }) => {
  return (
    <View style={styles.container}>
      <Card style={styles.card}>
        <Card.Title titleStyle={styles.text} title="Your Rating" />
        <Card.Cover
          style={styles.poster}
          source={{
            uri:
              rating.movie.poster_url !== null
                ? rating.movie.poster_url
                : "https://s.studiobinder.com/wp-content/uploads/2017/12/Movie-Poster-Template-Dark-with-Image.jpg?x81279",
          }}
        ></Card.Cover>
        <Card.Content>
          <Paragraph style={styles.text}>{rating.movie.title}</Paragraph>
          <Title style={styles.text}>
            <AntDesign name="star" size={24} color="yellow" /> {rating.rating}
          </Title>
          <Paragraph style={styles.text}>{rating.description}</Paragraph>
        </Card.Content>
      </Card>
    </View>
  );
};

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
  poster: {
    width: 140,
    height: 210,
    alignSelf: "center",
  },
  text: {
    color: "white",
    textAlign: "center",
  },
  actions: {
    alignSelf: "center",
  },
  button: {
    backgroundColor: "#82FFC3",
    marginBottom: 10,
  },
});
