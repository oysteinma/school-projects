import { View, StyleSheet } from "react-native";
import { Button, Card, Title, Paragraph } from "react-native-paper";
import { IMovie } from "../model/Interfaces";
import { AntDesign } from "@expo/vector-icons";
import { NavigationProp, ParamListBase } from "@react-navigation/native";

/**
 *
 * @param props navigation and movie
 * @returns the movie card for a single movie
 */
export const MovieCard = (props: {
  movie: IMovie;
  navigation: NavigationProp<ParamListBase>;
}) => {
  function handlePress() {
    props.navigation.navigate("Review", { movie: props.movie });
  }

  return (
    <View style={styles.container}>
      <Card style={styles.card}>
        <Card.Title titleStyle={styles.text} title={props.movie.title} />
        <Card.Cover
          style={styles.poster}
          source={{
            uri:
              props.movie.poster_url !== null
                ? props.movie.poster_url
                : "https://s.studiobinder.com/wp-content/uploads/2017/12/Movie-Poster-Template-Dark-with-Image.jpg?x81279",
          }}
        ></Card.Cover>
        <Card.Content>
          <Title style={styles.text}>
            <AntDesign name="star" size={24} color="yellow" />{" "}
            {props.movie.averageRating}
          </Title>
          <Paragraph style={styles.text}>{props.movie.released}</Paragraph>
        </Card.Content>
        <Card.Actions style={styles.actions}>
          <Button
            accessibilityLabel="view more information about the movie"
            color={"black"}
            style={styles.button}
            onPress={() => handlePress()}
          >
            View More Info
          </Button>
        </Card.Actions>
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
  },
  actions: {
    alignSelf: "center",
  },
  button: {
    backgroundColor: "#82FFC3",
    marginBottom: 10,
  },
});
