import { AppBar, Button, Toolbar, Box } from "@mui/material";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/index.css";

/**
 *
 * @returns the Navbar component. Includes the logo 
 * and a link to the front page, and links to all the 
 * other pages on the app.
 */
export const Navbar = () => {
  const navigate = useNavigate();
  const logo = require("../assets/logo.png");
  const [currentPath, setCurrentPath] = useState("/");
  const handleNavigate = (path: string) => {
    navigate(path);
    setCurrentPath(path);
  };
  return (
    <AppBar
      position="sticky"
      sx={{
        backgroundColor: "#071A2F",
        mb: 3,
        top: 0,
      }}
    >
      <Toolbar disableGutters>
        <Box
          component="img"
          src={logo}
          height="48px"
          width="48px"
          alt="logo"
          onClick={() => handleNavigate("/")}
          sx={{ "&:hover": { cursor: "pointer" } }}
        />
        <Button
          sx={{
            ml: 0.5,
            mr: 0.2,
            fontFamily: "Expletus Sans",
            color: "white",
            fontSize: 20,
            fontWeight: "bold",
            "&:hover": { backgroundColor: "#0059B2" },
          }}
          onClick={() => handleNavigate("/")}
        >
          Gitview
        </Button>
        <Box className="links" sx={{ display: "flex", gap: 0.2 }}>
          <Button
            sx={{
              "&:hover": { backgroundColor: "#0059B2" },
              color: "white",
              padding: "2px 6px",
              ...(currentPath === "/users" && {
                backgroundColor: "#0059B2",
              }),
            }}
            onClick={() => handleNavigate("/users")}
            className={currentPath === "/users" ? "selected" : ""}
          >
            Users
          </Button>
          <Button
            sx={{
              color: "white",
              padding: "2px 6px",
              "&:hover": { backgroundColor: "#0059B2" },
              ...(currentPath === "/commits" && {
                backgroundColor: "#0059B2",
              }),
            }}
            onClick={() => handleNavigate("/commits")}
          >
            Commits
          </Button>
          <Button
            sx={{
              color: "white",
              padding: "2px 6px",
              "&:hover": { backgroundColor: "#0059B2" },
              ...(currentPath === "/issues" && {
                backgroundColor: "#0059B2",
              }),
            }}
            onClick={() => handleNavigate("/issues")}
          >
            Issues
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
};
