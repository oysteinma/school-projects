import { LinkingOptions } from "@react-navigation/native";
import * as Linking from "expo-linking";
import { RootStackParamList } from "../types";

/**
 * Configuration for deep linking.
 * Came from the expo template.
 * Heavily inspired by the template, but with
 * several modifications to fit my app.
 */
const linking: LinkingOptions<RootStackParamList> = {
  prefixes: [Linking.createURL("/")],
  config: {
    screens: {
      Root: {
        screens: {
          FrontPageScreen: {
            screens: {
              FrontPageScreen: "one",
            },
          },
          YourRatingsScreen: {
            screens: {
              YourRatingsScreen: "two",
            },
          },
        },
      },
      Review: "review",
      SearchResultScreen: "searchResult",
      ErrorScreen: "*",
    },
  },
};

export default linking;
