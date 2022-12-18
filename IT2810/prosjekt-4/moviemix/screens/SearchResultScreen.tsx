import { NavigationProp, ParamListBase } from "@react-navigation/native";
import { View } from "react-native";
import SearchPageCards from "../components/SearchPageCards";

/**
 *
 * @param navigation
 * @returns the search result screen of the app
 */
export default function SearchResultScreen({
  navigation,
}: {
  navigation: NavigationProp<ParamListBase>;
}) {
  return (
    <View>
      <SearchPageCards navigation={navigation} />
    </View>
  );
}
