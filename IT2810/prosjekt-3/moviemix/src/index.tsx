import ReactDOM from "react-dom/client";
import App from "./main/App";
import {
  ApolloClient,
  ApolloProvider,
  NormalizedCacheObject,
} from "@apollo/client";
import { cache } from "./main/config/cache";
import { ThemeProvider, createTheme } from "@mui/material/styles";
import { CssBaseline } from "@mui/material/";

/**
 * The starting point of the application. Wraps the Apollo client around
 * the application and sets the theme.
 */

const themeDark = createTheme({
  palette: {
    background: {
      default: "#1d1d27",
    },
  },
});

const client: ApolloClient<NormalizedCacheObject> = new ApolloClient({
  cache: cache,
  uri: "http://it2810-59.idi.ntnu.no:4000/apolloServer",
});

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

root.render(
  <ApolloProvider client={client}>
    <ThemeProvider theme={themeDark}>
      <CssBaseline />
      <App />
    </ThemeProvider>
  </ApolloProvider>
);
