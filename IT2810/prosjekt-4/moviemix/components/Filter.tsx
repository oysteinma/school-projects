import { View, StyleSheet, ScrollView } from "react-native";
import { Button, Menu, ToggleButton, Text } from "react-native-paper";
import { Feather } from "@expo/vector-icons";
import { toggleTrendingVar } from "../state/toggleTrendingVariable";
import { limitVar } from "../state/limitVariable";
import { toggleVar } from "../state/toggleVariable";
import { toggleRatingVar } from "../state/toggleRatingVariable";
import { useState } from "react";
import { genreVar } from "../state/genreVariable";

const Allgenres = [
  "All",
  "Action",
  "Adventure",
  "Science Fiction",
  "Thriller",
  "Fantasy",
  "Crime",
  "Western",
  "Drama",
  "Family",
  "Animation",
  "Comedy",
  "Mystery",
  "Romance",
  "War",
  "History",
  "Music",
  "Horror",
  "Documentary",
  "TV Movie",
];

/**
 * @returns the Filter component, which displays filter- and toggle buttons.
 */
export const Filter = () => {
  const [disableTrending, setDisableTrending] = useState<boolean>(false);
  const [disableRating, setDisableRating] = useState<boolean>(false);
  const [visible, setVisible] = useState(false);
  const openMenu = () => setVisible(true);
  const closeMenu = () => setVisible(false);
  const trending = toggleTrendingVar();
  const rating = toggleRatingVar();
  const genre = genreVar();

  function handleToggleChange(value: string) {
    if (value === "trending") {
      setDisableRating(true);
      setDisableTrending(false);
      toggleVar(1);
      trending === "ASC" ? toggleTrendingVar("DESC") : toggleTrendingVar("ASC");
    } else {
      setDisableTrending(true);
      setDisableRating(false);
      toggleVar(0);
      rating === "ASC" ? toggleRatingVar("DESC") : toggleRatingVar("ASC");
    }
    limitVar(6);
  }
  return (
    <View>
      <Text style={styles.text}>Trending{"      "}Rating</Text>
      <ToggleButton.Row
        onValueChange={(value) => handleToggleChange(value)}
        value=""
        style={styles.toggle}
      >
        <ToggleButton
          accessibilityLabel="toggle trending"
          style={{ borderColor: "transparent" }}
          icon={() =>
            disableTrending === true ? (
              <Feather name="x-circle" size={30} color="#82FFC3" />
            ) : trending === "DESC" ? (
              <Feather name="trending-up" size={30} color="#82FFC3" />
            ) : (
              <Feather name="trending-down" size={30} color="#82FFC3" />
            )
          }
          value="trending"
        />
        <View style={styles.space} />
        <ToggleButton
          accessibilityLabel="toggle rating"
          style={{ borderColor: "transparent" }}
          icon={() =>
            disableRating === true ? (
              <Feather name="x-circle" size={30} color="#82FFC3" />
            ) : rating === "DESC" ? (
              <Feather name="arrow-up" size={30} color="#82FFC3" />
            ) : (
              <Feather name="arrow-down" size={30} color="#82FFC3" />
            )
          }
          value="rating"
        />
      </ToggleButton.Row>
      <View style={styles.space} />
      <View style={styles.centerMenu}>
        <Menu
          style={{
            marginTop: 50,
            marginBottom: 20,
          }}
          visible={visible}
          onDismiss={closeMenu}
          anchor={
            <View
              style={{
                width: 170,
                alignItems: "center",
                alignSelf: "center",
              }}
            >
              <Button
                accessibilityLabel="select genre"
                style={{ height: 100, marginTop: 10 }}
                onPress={openMenu}
              >
                {genre === "" ? "Genres" : genre !== "All" ? genre : "All"}
              </Button>
            </View>
          }
        >
          <ScrollView style={{ height: 200 }}>
            {Allgenres.map((genre, key) => (
              <Menu.Item
                accessibilityLabel="select spesific genre"
                style={{ height: 50 }}
                key={key}
                onPress={() => {
                  if (genre === "All") {
                    genreVar("");
                  } else {
                    genreVar(genre);
                  }
                  closeMenu();
                }}
                title={genre}
              />
            ))}
          </ScrollView>
        </Menu>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  text: {
    color: "white",
    textAlign: "center",
    fontSize: 20,
  },
  genrebutton: {
    width: 20,
  },
  space: {
    width: 50,
  },
  centerMenu: {
    justifyContent: "center",
    flexDirection: "row",
  },
  toggle: {
    alignContent: "center",
    justifyContent: "center",
  },
});
