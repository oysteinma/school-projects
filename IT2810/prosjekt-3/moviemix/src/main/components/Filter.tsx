import {
  Button,
  FormControl,
  FormControlLabel,
  InputLabel,
  MenuItem,
  Radio,
  RadioGroup,
  Select,
  SelectChangeEvent,
  Stack,
} from "@mui/material";
import Box from "@mui/material/Box";
import TrendingUpIcon from "@mui/icons-material/TrendingUp";
import ArrowDownwardIcon from "@mui/icons-material/ArrowDownward";
import ArrowUpwardIcon from "@mui/icons-material/ArrowUpward";
import TrendingDownIcon from "@mui/icons-material/TrendingDown";
import { SyntheticEvent, useState } from "react";

/**
 * @returns the Filter component, which displays filter- and sorting buttons.
 * @param props : parameters to decide sorting direction and filter options.
 */
export const Filter = (props: {
  onGenreChange: (arg0: string) => void;
  onRatingChange: (arg0: string) => void;
  onPopularityChange: (arg0: string) => void;
  onFilteringChange: (arg0: string) => void;
  onLimitChange: (arg0: number) => void;
  sortRating: string;
  sortPopularity: string;
}) => {
  const genres = [
    "All",
    "Action",
    "Adventure",
    "Science Fiction",
    "Thriller",
    "Fantasy",
    "Crime",
    "Western",
    "Drama",
    "Family",
    "Animation",
    "Comedy",
    "Mystery",
    "Romance",
    "War",
    "History",
    "Music",
    "Horror",
    "Documentary",
    "TV Movie",
  ];

  const [selectedGenre, setSelectedGenre] = useState<string>("");
  const [popularityIsDiabled, setPopularityIsDiabled] =
    useState<boolean>(false);
  const [ratingIsDiabled, setRatingIsDiabled] = useState<boolean>(false);

  function changeFilter(event: SyntheticEvent<Element, Event>) {
    var toggleValue = (event.target as HTMLInputElement).value;

    if (toggleValue === "bothEnabled") {
      setPopularityIsDiabled(false);
      setRatingIsDiabled(false);
      props.onFilteringChange(toggleValue);
    } else if (toggleValue === "onlyRating") {
      setPopularityIsDiabled(true);
      setRatingIsDiabled(false);
      props.onFilteringChange(toggleValue);
    } else {
      setPopularityIsDiabled(false);
      setRatingIsDiabled(true);
      props.onFilteringChange(toggleValue);
    }
    props.onLimitChange(12);
  }

  return (
    <Box display={"flex"} flexDirection={"column"} gap={5}>
      <Stack direction={"row"} spacing={2}>
        <Button
          id="popularityFilter"
          aria-label="Sort by popularity"
          variant="contained"
          disabled={popularityIsDiabled}
          startIcon={
            props.sortPopularity === "ASC" ? (
              <TrendingDownIcon />
            ) : (
              <TrendingUpIcon />
            )
          }
          onClick={() => {
            props.onLimitChange(12);
            props.onPopularityChange(
              props.sortPopularity === "DESC" ? "ASC" : "DESC"
            );
          }}
          sx={{
            background: "#545189",
            justifyContent: "flex-start",
            fontWeight: "bold",
            height: "56px",
            width: "148px",
            "&:hover": { backgroundColor: "#423D89" },
            "&:disabled": {
              backgroundColor: "#100F21",
              color: "#545189",
            },
          }}
        >
          Trending
        </Button>
        <Button
          id="ratingFilter"
          aria-label="Sort by rating"
          variant="contained"
          disabled={ratingIsDiabled}
          startIcon={
            props.sortRating === "ASC" ? (
              <ArrowDownwardIcon />
            ) : (
              <ArrowUpwardIcon />
            )
          }
          onClick={() => {
            props.onLimitChange(12);
            props.onRatingChange(props.sortRating === "DESC" ? "ASC" : "DESC");
          }}
          sx={{
            background: "#545189",
            justifyContent: "flex-start",
            fontWeight: "bold",
            height: "56px",
            width: "148px",
            "&:hover": { backgroundColor: "#423D89" },
            "&:disabled": {
              backgroundColor: "#100F21",
              color: "#545189",
            },
          }}
        >
          Rating
        </Button>
        <Box>
          <FormControl
            id="filterGenre"
            aria-label="Filter by genre"
            sx={{
              width: 150,
            }}
          >
            <InputLabel
              sx={{
                color: "white",
                fontWeight: "bold",
                "&.Mui-focused": {
                  color: "white",
                },
              }}
            >
              Genres
            </InputLabel>
            <Select
              sx={{
                backgroundColor: "#545189",
                borderRadius: "5px",
                color: "white",
                fontWeight: "bold",
                "&:hover": { backgroundColor: "#423D89" },
              }}
              label={"Genre"}
              value={selectedGenre}
              onChange={(event: SelectChangeEvent<typeof selectedGenre>) => {
                props.onLimitChange(12);
                let genre = event.target.value;
                setSelectedGenre(genre);
                if (genre === "All") {
                  genre = "";
                }
                props.onGenreChange(genre);
              }}
              MenuProps={{
                PaperProps: {
                  style: {
                    maxHeight: 40 * 4.5,
                  },
                },
              }}
            >
              {genres.map((genre) => (
                <MenuItem key={genre} value={genre}>
                  {genre}
                </MenuItem>
              ))}
            </Select>
          </FormControl>
        </Box>
      </Stack>
      <FormControl>
        <RadioGroup
          row
          color="white"
          defaultValue={"bothEnabled"}
          onChange={(event) => changeFilter(event)}
          sx={{ justifyContent: "center", alignItems: "center" }}
        >
          <FormControlLabel
            id="filterBoth"
            aria-label="Filter by genre and rating"
            value="bothEnabled"
            control={<Radio />}
            label="Enable Both"
            sx={{
              color: "white",
              "& .MuiSvgIcon-root": {
                fontSize: 30,
                color: "#423D89",
              },
            }}
          />
          <FormControlLabel
            id="filterOnlyTrending"
            aria-label="Filter by trending only"
            value="onlyPopularity"
            control={<Radio />}
            label="Only Trending"
            sx={{
              color: "white",
              "& .MuiSvgIcon-root": {
                fontSize: 30,
                color: "#423D89",
              },
            }}
          />
          <FormControlLabel
            id="filterOnlyRating"
            aria-label="Filter by rating only"
            value="onlyRating"
            control={<Radio />}
            label="Only Rating"
            sx={{
              color: "white",
              "& .MuiSvgIcon-root": {
                fontSize: 30,
                color: "#423D89",
              },
            }}
          />
        </RadioGroup>
      </FormControl>
    </Box>
  );
};
