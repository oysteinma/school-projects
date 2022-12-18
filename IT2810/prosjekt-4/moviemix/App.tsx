import { ApolloProvider } from "@apollo/client";
import { Provider } from "react-native-paper";
import { SafeAreaProvider } from "react-native-safe-area-context";
import { client } from "./config/client";
import SetupUser from "./index";
import Navigation from "./navigation";

/**
 * Starting point of the application. Wraps the app in the necessary providers.
 * @returns the application
 */
export default function App() {
  return (
    <ApolloProvider client={client}>
      <SafeAreaProvider>
        <Provider>
          <Navigation />
          <SetupUser />
        </Provider>
      </SafeAreaProvider>
    </ApolloProvider>
  );
}
