import {
  Box,
  Typography,
  Grid,
  Card,
  Stack,
  Avatar,
  Divider,
} from "@mui/material";
import { IRating } from "../model/Interfaces";
import StarIcon from "@mui/icons-material/Star";

/**
 * @returns the ListView component, which displays data vertically.
 * @param data : movie-reviews connected to a user.
 * Is being used in YourRatings to display movie reviews.
 */
export default function ListView({ ...data }) {
  const imageTemplate = require("../graphics/template.jpg");
  return (
    <Box style={{ width: "40vw" }}>
      {data?.users[0]?.ratings.length === 0 ? (
        <Box color={"white"}>
          <Typography variant="h5" align="center">
            REVIEW A MOVIE TO SEE IT HERE!
          </Typography>
        </Box>
      ) : (
        <Grid container rowSpacing={2} spacing={2}>
          {data.users[0].ratings.map((rating: IRating, index: number) => (
            <Grid item xs={12} key={index}>
              <Card
                sx={{ p: 2 }}
                style={{
                  backgroundColor: "#0E0E1F",
                  color: "white",
                  borderRadius: "10px",
                }}
              >
                <Stack padding={2} spacing={1}>
                  <Typography sx={{ fontWeight: "bold" }}>
                    {rating.movie.title}
                  </Typography>
                  <Box>
                    <Avatar
                      variant="rounded"
                      src={
                        rating?.movie.poster_url !== null
                          ? rating?.movie.poster_url
                          : imageTemplate
                      }
                    />
                  </Box>
                  <Divider sx={{ my: 0.5, backgroundColor: "white" }} />
                  <Typography>
                    Your rating: <br /> {rating.rating}/5{" "}
                    <StarIcon
                      fontSize="small"
                      style={{ color: "yellow", position: "absolute" }}
                    />{" "}
                  </Typography>
                  <Divider sx={{ my: 0.5, backgroundColor: "white" }} />
                  <Typography paragraph sx={{ wordWrap: "break-word" }}>
                    Your review: <br />
                    {rating.description}
                  </Typography>
                </Stack>
              </Card>
            </Grid>
          ))}
        </Grid>
      )}
    </Box>
  );
}
