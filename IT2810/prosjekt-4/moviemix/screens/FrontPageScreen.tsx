import { NavigationProp, ParamListBase } from "@react-navigation/native";
import { View } from "react-native";
import FrontPageMovieCards from "../components/FrontPageMovieCards";

/**
 *
 * @param navigation
 * @returns the front page of the app
 */
export default function FrontPageScreen({
  navigation,
}: {
  navigation: NavigationProp<ParamListBase>;
}) {
  return (
    <View>
      <FrontPageMovieCards navigation={navigation} />
    </View>
  );
}
