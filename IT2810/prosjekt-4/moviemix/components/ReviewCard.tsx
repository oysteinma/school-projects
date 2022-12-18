import { useMutation, useReactiveVar } from "@apollo/client";
import { StyleSheet } from "react-native";
import { View } from "react-native";
import { CREATE_RATING, GET_RATINGS_BY_USER } from "../queries/userQueries";
import { NavigationProp, ParamListBase } from "@react-navigation/native";
import {
  Button,
  Card,
  TextInput,
  Paragraph,
  ToggleButton,
} from "react-native-paper";
import { userIdVar } from "../state/userVariable";
import { IMovie } from "../model/Interfaces";
import { Ionicons } from "@expo/vector-icons";
import { MaterialCommunityIcons } from "@expo/vector-icons";
import { KeyboardAwareScrollView } from "react-native-keyboard-aware-scroll-view";
import { useState } from "react";

/**
 *
 * @param props navigation and movie
 * @returns the review card for a single movie
 */
export default function ReviewCard(props: {
  movie: IMovie;
  navigation: NavigationProp<ParamListBase>;
}) {
  const [addReview] = useMutation(CREATE_RATING);
  const [review, setReview] = useState<string>("");
  const [rating, setRating] = useState<string>("1");
  const [error, setError] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string>("");
  const IDUser = useReactiveVar(userIdVar);

  let actorList = "";
  try {
    actorList = Array.prototype.map
      .call(props.movie.actors, function (item) {
        return item.name;
      })
      .join(", ");
  } catch (error) {
    actorList = "No registered actors";
  }

  let director = "";
  if (props.movie.director !== undefined) {
    director = props.movie.director[0].name;
  } else {
    director = "No registered director";
  }

  const handleExit = () => {
    props.navigation.goBack();
  };

  const handleSave = () => {
    if (review.length <= 0) {
      setError(true);
      setErrorMessage("Please enter a review");
    } else {
      if (IDUser) {
        addReview({
          variables: {
            rating: parseInt(rating),
            description: review,
            movieId: props.movie.id,
            userId: IDUser,
          },
        });
      }
      handleExit();
    }
  };
  return (
    <KeyboardAwareScrollView
      contentContainerStyle={{
        marginTop: 20,
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <View>
        <Card style={styles.card}>
          <Paragraph style={styles.title}>{props.movie.title}</Paragraph>
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
            <Paragraph style={styles.text}>
              {props.movie.tagline === ""
                ? "'No tagline'"
                : "'" + props.movie.tagline + "'"}
            </Paragraph>
            <Paragraph style={styles.people}>
              <MaterialCommunityIcons
                name="movie-open"
                size={18}
                color="white"
              />{" "}
              {director}
            </Paragraph>
            <Paragraph style={styles.people}>
              <Ionicons name="ios-people" size={18} color="white" /> {actorList}
            </Paragraph>
            <TextInput
              style={{ margin: 10 }}
              label="Your review"
              accessibilityLabel="write review"
              placeholder={errorMessage}
              multiline={true}
              maxLength={100}
              error={error}
              value={review}
              autoCorrect={false}
              blurOnSubmit={true}
              onChangeText={(review) => setReview(review)}
            />

            <ToggleButton.Row
              onValueChange={(value) =>
                value !== null ? setRating(value) : null
              }
              value={rating}
              style={{
                alignContent: "center",
                justifyContent: "center",
              }}
            >
              <ToggleButton
                icon="star"
                color={"yellow"}
                style={styles.star}
                accessibilityLabel="select one stars"
                value="1"
              />
              <ToggleButton
                icon="star"
                accessibilityLabel="select two stars"
                color={parseInt(rating) > 1 ? "yellow" : "black"}
                style={styles.star}
                value="2"
              />
              <ToggleButton
                icon="star"
                accessibilityLabel="select three stars"
                color={parseInt(rating) > 2 ? "yellow" : "black"}
                style={styles.star}
                value="3"
              />
              <ToggleButton
                icon="star"
                accessibilityLabel="select four stars"
                color={parseInt(rating) > 3 ? "yellow" : "black"}
                style={styles.star}
                value="4"
              />
              <ToggleButton
                icon="star"
                accessibilityLabel="select five stars"
                color={parseInt(rating) > 4 ? "yellow" : "black"}
                style={styles.star}
                value="5"
              />
            </ToggleButton.Row>
          </Card.Content>
          <Card.Actions style={styles.actions}>
            <Button
              style={styles.button}
              color={"black"}
              onPress={() => handleExit()}
            >
              Cancel
            </Button>
            <Button
              style={styles.button}
              color={"black"}
              onPress={() => handleSave()}
            >
              Save
            </Button>
          </Card.Actions>
        </Card>
      </View>
    </KeyboardAwareScrollView>
  );
}

const styles = StyleSheet.create({
  card: {
    width: 300,
    backgroundColor: "#252E40",
    elevation: 10,
  },
  title: {
    color: "white",
    textAlign: "center",
    fontSize: 20,
    marginTop: 10,
  },
  poster: {
    width: 60,
    height: 110,
    alignSelf: "center",
  },
  text: {
    color: "white",
    marginTop: 10,
    textAlign: "center",
  },
  people: {
    color: "white",
    marginTop: 10,
    textAlign: "left",
  },
  actions: {
    display: "flex",
    flexDirection: "row",
    spaceBetween: "space-between",
    gap: 10,
    alignSelf: "center",
  },
  button: {
    backgroundColor: "#82FFC3",
    width: 80,
    margin: 10,
  },
  toggle: {
    display: "flex",
    flexDirection: "row",
    justifyContent: "center",
  },
  star: {
    borderColor: "transparent",
    backgroundColor: "transparent",
  },
});
