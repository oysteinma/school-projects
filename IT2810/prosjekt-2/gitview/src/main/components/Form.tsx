import { useContext, useState } from "react";
import { AuthContext } from "../App";
import { useApi } from "../api/Api";
import { Alert, Snackbar } from "@mui/material";

/** The input-form page, with text, two inputfields, a button and a picture
 *
 * A function (store) stores the inputs in local/session-storage, and a snackbar
 * informs the user of their input's validity.
 */

export const Form = () => {
  const [id, setId] = useState("");
  const [token, setToken] = useState("");
  const { setAuthenticated } = useContext(AuthContext);
  const image = require("../assets/git-logo.png");
  const [openSnackSuccess, setOpenSnackSuccess] = useState(false);
  const [openSnackError, setOpenSnackError] = useState(false);
  const { getRepo } = useApi();

  function store() {
    if (!(id && token)) {
      alert("Please fill out both fields!");
    } else {
      getRepo(id, token)
        .then(() => {
          setAuthenticated(true);
          window.localStorage.setItem("id", id);
          window.sessionStorage.setItem("token", token);
          setId("");
          setToken("");
          setOpenSnackSuccess(true);
        })
        .catch(() => {
          setAuthenticated(false);
          window.localStorage.clear();
          window.sessionStorage.clear();
          setOpenSnackError(true);
        });
    }
  }

  const handleClose = (
    event?: React.SyntheticEvent | Event,
    reason?: string
  ) => {
    setOpenSnackSuccess(false);
    setOpenSnackError(false);
  };

  return (
    <div id="form">
      <div id="introform">
        <h1>Welcome To GitView!</h1>
        <p>
          This is a simple website which lets you view the users, commits, and
          issues of your own GitLab repository. The pages lets you interact in
          different ways with the data, such that you can view more specific
          details about your own repository!
        </p>
        <hr></hr>
        <p>
          Please enter your Project ID and Project Access Token down below to
          use the website.
        </p>
      </div>

      <div id="formdiv">
        <div id="iddiv">
          <input
            id="idinput"
            className="inputs"
            placeholder="Project ID"
            type="text"
            required
            value={id}
            onChange={(e) => setId(e.target.value)}
          />
        </div>

        <div id="tokendiv">
          <input
            id="tokeninput"
            className="inputs"
            placeholder="Project Token"
            type="text"
            required
            value={token}
            onChange={(e) => setToken(e.target.value)}
          />
        </div>

        <div id="buttondiv">
          <button
            id="buttoninput"
            type="button"
            role={"button"}
            onClick={store}
          >
            Connect!
          </button>
        </div>
        <div id="gitdiv">
          <img src={image} id="gitlogo" />
        </div>
      </div>
      <div>
        <Snackbar
          open={openSnackError}
          autoHideDuration={2000}
          onClose={handleClose}
        >
          <Alert severity="error">
            Could not load Project Information. Try again!
          </Alert>
        </Snackbar>
        <Snackbar
          open={openSnackSuccess}
          autoHideDuration={2000}
          onClose={handleClose}
        >
          <Alert severity="success">
            Project Information loaded successfully!
          </Alert>
        </Snackbar>
      </div>
    </div>
  );
};
