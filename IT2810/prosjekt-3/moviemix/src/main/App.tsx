import { HashRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import LandingPage from "./pages/LandingPage";
import YourRatingsPage from "./pages/YourRatings";
import SearchResults from "./pages/SearchResults";
import { makeVar, useMutation } from "@apollo/client";
import { CREATE_USER } from "./queries/query";

/**
 * Sets up ReactiveVar and creates a user if one does not exist
 * @returns HashRouter - the router for the application
 */

export const moviesVar = makeVar([]);
export default function App() {
  const [createUsers] = useMutation(CREATE_USER);

  if (window.localStorage.getItem("userId") === null) {
    createUsers({ variables: { input: [{}] } }).then((result) => {
      window.localStorage.setItem(
        "userId",
        result.data.createUsers.users[0].id
      );
    });
  }

  return (
    <HashRouter>
      <Navbar />
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/ratings" element={<YourRatingsPage />} />
        <Route path="/results" element={<SearchResults />} />
      </Routes>
    </HashRouter>
  );
}
