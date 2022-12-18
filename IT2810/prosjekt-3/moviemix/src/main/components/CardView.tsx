import { IMovie } from "../model/Interfaces";
import { useReactiveVar } from "@apollo/client";
import { moviesVar } from "../App";
import {
  Box,
  Card,
  CardActions,
  CardContent,
  CardMedia,
  Divider,
  Grid,
  Typography,
} from "@mui/material";
import Movie from "./Movie";
import StarIcon from "@mui/icons-material/Star";

/**
 * @returns the CardView component, which displays movies as Cards in a Grid element.
 * Is being used on the LandingPage and in SearchResults.
 */
export default function CardView() {
  const movies = useReactiveVar(moviesVar);
  const imageTemplate = require("../graphics/template.jpg");

  return (
    <Box style={{ width: "80vw" }}>
      {movies.length === 0 && window.location.href.slice(-1) === "s" ? (
        <Box textAlign="center" color={"white"}>
          <Typography variant="h5">NO RESULTS FROM SEARCH!</Typography>
        </Box>
      ) : (
        <Grid
          container
          alignItems="center"
          justifyContent="center"
          rowSpacing={10}
          spacing={10}
        >
          {movies.map((movie: IMovie, index) => (
            <Grid item xs={8} md={4} lg={3} key={index}>
              <Card
                style={{
                  backgroundColor: "#171721",
                  boxShadow: "5px 10px ",
                  borderRadius: "10px",
                  borderColor: "white",
                }}
              >
                <CardMedia
                  component="img"
                  height="300"
                  image={
                    movie.poster_url !== null ? movie.poster_url : imageTemplate
                  }
                  alt="Movie poster"
                  sx={{
                    objectFit: "contain",
                    padding: "2em",
                    color: "white",
                  }}
                />
                <Divider sx={{ my: 0.5, backgroundColor: "white" }} />
                <CardContent>
                  <Typography
                    gutterBottom
                    variant="h6"
                    fontWeight={800}
                    component="div"
                    aria-label="Title of movie"
                    color={"white"}
                    noWrap={true}
                  >
                    {movie.title}
                    <br />
                    <StarIcon fontSize="small" style={{ color: "yellow" }} />
                    {movie.averageRating}
                    <br />
                    {movie.released}
                  </Typography>

                  <CardActions style={{ justifyContent: "center" }}>
                    <Movie {...movie} />
                  </CardActions>
                </CardContent>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  );
}
