import SearchIcon from "@mui/icons-material/Search";
import InputAdornment from "@mui/material/InputAdornment";
import FormControl from "@mui/material/FormControl";
import TextField from "@mui/material/TextField";
import Box from "@mui/material/Box";
import { Button } from "@mui/material";
import { useQuery } from "@apollo/client";
import { GET_SEARCH_RESULT } from "../queries/query";
import { useState } from "react";
import { moviesVar } from "../App";
import { useNavigate } from "react-router-dom";
import { KeyboardEvent } from "react";

/**
 * @returns the Search component, which displays search functionality
 * on LandingPage and SearchResults.
 */
export default function Search() {
  const { refetch } = useQuery(GET_SEARCH_RESULT);
  const [searchBar, handleSearchBar] = useState<string>("");
  const [validation, setValidation] = useState<boolean>(false);
  const navigate = useNavigate();
  const handleSubmit = () => {
    if (searchBar === "") {
      setValidation(true);
    } else {
      refetch({
        searchString: searchBar,
      }).then((r) => {
        moviesVar(r.data.fuzzyMoviesSearch);
        navigate("/results");
        setValidation(false);
      });
      handleSearchBar("");
    }
  };

  return (
    <Box display={"flex"} flexDirection={"row"} alignItems={"top"}>
      <Box>
        <FormControl>
          <TextField
            placeholder="Search"
            id="searchbar"
            value={searchBar}
            error={validation}
            helperText={validation ? "Enter a Search Term!" : ""}
            onChange={(e) => handleSearchBar(e.target.value)}
            onKeyPress={(e: KeyboardEvent<HTMLImageElement>) => {
              if (e.key === "Enter") {
                handleSubmit();
              }
            }}
            InputProps={{
              style: {
                width: "40vw",
                height: "50px",
                background: "white",
                borderRadius: "0",
              },
              startAdornment: (
                <InputAdornment position="start">
                  <SearchIcon />
                </InputAdornment>
              ),
            }}
          />
        </FormControl>
      </Box>
      <Box>
        <Button
          onClick={handleSubmit}
          sx={{
            height: "50px",
            background: "#F9F7F5",
            color: "black",
            border: "1px lightgray solid",
            borderRadius: "0",
            "&:hover": { cursor: "pointer", background: "lightgrey" },
          }}
        >
          Search
        </Button>
      </Box>
    </Box>
  );
}
