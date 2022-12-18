import * as React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import Rating from "@mui/material/Rating";
import PersonIcon from "@mui/icons-material/Person";
import MovieIcon from "@mui/icons-material/Movie";
import PeopleIcon from "@mui/icons-material/People";
import {
  IconButton,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Stack,
} from "@mui/material";
import { Review } from "./Review";
import CloseIcon from "@mui/icons-material/Close";

/**
 * @returns The movie component - which lets the user see details about a movie.
 * Props are passed from the parent CardView.
 * In addition to movie details, the user may access the review component through a modal.
 */
export default function Movie({ ...movie }) {
  const [open, setOpen] = React.useState<boolean>(false);
  const imageTemplate = require("../graphics/template.jpg");
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  const style = {
    position: "absolute" as "absolute",
    display: "flex",
    flexDirection: "column",
    justifyContent: "space-around",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    width: 480,
    height: 600,
    bgcolor: "#1A1A27",
    border: "2px solid white",
    boxShadow: 24,
    p: 2,
    color: "white",
  };

  const customStars = {
    "& .MuiRating-iconFilled": {
      color: "yellow",
    },
    "& .MuiRating-iconEmpty": {
      color: "grey",
    },
  };

  let actorList = "";
  try {
    actorList = Array.prototype.map
      .call(movie.actors, function (item) {
        return item.name;
      })
      .join(", ");
  } catch (error) {
    actorList = "No registered actors";
  }

  let director = "";
  try {
    director = movie.director[0].name;
  } catch (error) {
    director = "No registered director";
  }

  return (
    <Box>
      <Button
        onClick={handleOpen}
        sx={{ fontWeight: "bold" }}
        aria-label="See movie details"
      >
        Movie details
      </Button>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby={"Details" + movie.title}
        id="film"
      >
        <Box sx={style}>
          <IconButton
            sx={{ top: 0, right: 0, position: "fixed", color: "white" }}
            aria-label="Close window"
            onClick={handleClose}
          >
            <CloseIcon />
          </IconButton>
          <Typography
            id="film-details-title"
            fontStyle="bold"
            fontSize={26}
            align="center"
            text-align={"center"}
            className="title"
          >
            {movie.title}
          </Typography>

          <Box
            component="img"
            sx={{
              alignSelf: "center",
              width: "20%",
            }}
            alt="The Movie Poster"
            src={movie.poster_url !== null ? movie.poster_url : imageTemplate}
          />

          <Typography id="film-details-rating" alignSelf={"center"}>
            <Rating
              sx={customStars}
              name="rate-movie"
              defaultValue={movie.averageRating / 2}
              precision={0.5}
              size={"large"}
              readOnly={true}
            />
          </Typography>

          <Typography
            id="film-details-tagline"
            fontStyle={"italic"}
            fontSize={22}
            align="center"
          >
            "{movie.tagline !== "" ? movie.tagline : "No tagline"}"
          </Typography>

          <List>
            <Stack direction={"row"}>
              <ListItem>
                <ListItemIcon>
                  <PersonIcon style={{ color: "white" }} />
                </ListItemIcon>
                <ListItemText
                  secondaryTypographyProps={{
                    color: "white",
                    fontWeight: "bold",
                  }}
                  primary={director}
                  secondary={"Director"}
                />
              </ListItem>
              <ListItem>
                <ListItemIcon>
                  <MovieIcon style={{ color: "white" }} />
                </ListItemIcon>
                <ListItemText
                  secondaryTypographyProps={{
                    color: "white",
                    fontWeight: "bold",
                  }}
                  primary={movie.released}
                  secondary={"Released"}
                />
              </ListItem>
            </Stack>
            <ListItem>
              <ListItemIcon>
                <PeopleIcon style={{ color: "white" }} />
              </ListItemIcon>
              <ListItemText
                secondaryTypographyProps={{
                  color: "white",
                  fontWeight: "bold",
                }}
                primary={actorList}
                secondary={"Actors"}
              />
            </ListItem>
          </List>
          <Review {...movie} />
        </Box>
      </Modal>
    </Box>
  );
}
