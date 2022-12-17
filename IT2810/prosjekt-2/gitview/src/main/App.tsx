import { HashRouter, Routes, Route } from "react-router-dom";
import { createContext, useState } from "react";
import { Navbar } from "./components/Menu";
import { Form } from "./components/Form";
import { User } from "./components/User";
import { Commit } from "./components/Commit";
import { Issue } from "./components/Issue";
import { Repository } from "./components/Repository";
import Footer from "./components/Footer";

export const AuthContext = createContext({
  authenticated: false,
  setAuthenticated: (auth: boolean) => {
    console.log(auth);
  },
});
/**
 *
 * App Gives a general structure for the website. on top
 * you have the navbar, then the respective page, then
 * the navbar.
 */
const App = () => {
  const [authenticated, setAuthenticated] = useState(
    window.localStorage.getItem("id") && window.sessionStorage.getItem("token")
      ? true
      : false
  );
  return (
    <AuthContext.Provider value={{ authenticated, setAuthenticated }}>
      <HashRouter>
        <Navbar />
        <div className="content">
          {window.localStorage.getItem("id") &&
            window.sessionStorage.getItem("token") && <Repository />}
          <Routes>
            <Route path="/" element={<Form />} />
            <Route path="/users" element={<User />} />
            <Route path="/commits" element={<Commit />} />
            <Route path="/issues" element={<Issue />} />
          </Routes>
        </div>
        <div className="footer">
          <Footer />
        </div>
      </HashRouter>
    </AuthContext.Provider>
  );
};

export default App;
