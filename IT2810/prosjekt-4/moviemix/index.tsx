import { StatusBar } from "expo-status-bar";
import { fetchUserInfo } from "./hooks/fetchUserInfo";

/**
 * A work around for the fact that the ApolloProvider is not available
 * at the start of the app. Probably a better way to do this is to use cached
 * resource or something like that (splashscreen).
 */
export default function SetupUser() {
  fetchUserInfo();

  return <StatusBar style="auto" />;
}
