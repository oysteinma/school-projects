import { View } from "react-native";
import { Searchbar } from "react-native-paper";
import { GET_SEARCH_RESULT } from "../queries/movieQueries";
import { useQuery } from "@apollo/client";
import { searchResultVar } from "../state/searchResultVariable";
import { useState } from "react";
import { Filter } from "./Filter";
import { NavigationProp, ParamListBase } from "@react-navigation/native";

/**
 *
 * @param navigation
 * @returns the search bar
 */
export default function SearchField({
  navigation,
}: {
  navigation: NavigationProp<ParamListBase>;
}) {
  const [searchString, setSearchString] = useState<string>("");
  const [status, setStatusMessage] = useState<string>("Search");

  const { refetch } = useQuery(GET_SEARCH_RESULT);
  const handleSearch = () => {
    if (searchString === "") {
      setStatusMessage("Please enter a movie!");
    } else {
      refetch({
        searchString: searchString,
      }).then((result) => {
        searchResultVar(result.data.fuzzyMoviesSearch);
        navigation.navigate("SearchResultScreen");
      });
      setStatusMessage("Search");
      setSearchString("");
    }
  };
  return (
    <View>
      <Searchbar
        accessibilityLabel="search for a movie"
        style={{ margin: 40 }}
        value={searchString}
        placeholder={status}
        maxLength={35}
        onChangeText={(search) => setSearchString(search)}
        onSubmitEditing={() => handleSearch()}
      />
      <Filter />
    </View>
  );
}
