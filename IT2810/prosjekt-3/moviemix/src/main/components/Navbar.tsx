import { AppBar, Button, Toolbar, Box } from "@mui/material";
import { useNavigate } from "react-router-dom";

/**
 * @returns the Navbar component, which displays the navbar on each page.
 */
export default function Navbar() {
  const navigate = useNavigate();
  const logo = require("../graphics/logo.png");

  const handleNavigate = (path: string) => {
    navigate(path);
    window.scroll(0, 0);
  };
  return (
    <AppBar
      position="sticky"
      sx={{
        backgroundColor: "#1A1A27",
        width: "100%",
        margin: "0",
        top: 0,
      }}
    >
      <Toolbar>
        <Box
          component="img"
          src={logo}
          height="32px"
          width="36px"
          alt="logo"
          onClick={() => handleNavigate("/")}
          sx={{ "&:hover": { cursor: "pointer" }, marginLeft: "1rem" }}
        />
        <Button
          sx={{
            color: "white",
            mx: "1rem",
            padding: "2px 6px",
            "&:hover": { backgroundColor: "#002C58" },
            fontWeight: "bold",
          }}
          onClick={() => handleNavigate("/")}
        >
          MovieMix
        </Button>
        <Box className="links" sx={{ display: "flex", gap: 0.2 }}>
          <Button
            sx={{
              color: "white",
              padding: "2px 6px",
              "&:hover": { backgroundColor: "#002C58" },
              fontWeight: "bold",
            }}
            onClick={() => handleNavigate("/ratings")}
          >
            Your Ratings
          </Button>
        </Box>
      </Toolbar>
    </AppBar>
  );
}
