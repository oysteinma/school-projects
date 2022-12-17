import * as React from "react";
import Box from "@mui/material/Box";
import Modal from "@mui/material/Modal";
import Button from "@mui/material/Button";
import {
  Alert,
  Divider,
  IconButton,
  Rating,
  Snackbar,
  Stack,
  Typography,
} from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import { TextField } from "@mui/material";
import { useMutation } from "@apollo/client";
import { CREATE_RATING } from "../queries/query";

/**
 * @returns The review component - which lets the user review a movie.
 * Registering a review requires a review-text, and the review is linked
 * to the user-id stored in localstorage such that all reviews can be shown
 * in Your Ratings. It displays helpertext or a success-snackbar
 * depending on the validity of the input.
 */
export function Review({ ...movie }) {
  const [addReview] = useMutation(CREATE_RATING);
  const [rating, setRating] = React.useState<number | null>(0.5);
  const [description, setDescription] = React.useState<String>("");
  const [hover, setHover] = React.useState(-1);
  const [open, setOpen] = React.useState(false);
  const [validation, setValidation] = React.useState<boolean>(false);
  const [openSnackSuccess, setOpenSnackSuccess] = React.useState(false);

  const id = window.localStorage.getItem("userId");

  const style = {
    position: "absolute" as "absolute",
    display: "flex",
    flexDirection: "column",
    justifyContent: "space-around",
    top: "50%",
    left: "50%",
    transform: "translate(-50%, -50%)",
    bgcolor: "#1A1A27",
    border: "4px solid white",
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

  const labels: { [index: string]: string } = {
    0.5: "Terrible",
    1: "Bad",
    1.5: "Poor",
    2: "Poor+",
    2.5: "Ok",
    3: "Ok+",
    3.5: "Good",
    4: "Good+",
    4.5: "Excellent",
    5: "Outstanding",
  };

  function getLabelText(rating: number) {
    return `${rating} Star${rating !== 1 ? "s" : ""}, ${labels[rating]}`;
  }

  const handleSave = () => {
    if (description.length > 0) {
      addReview({
        variables: {
          rating: rating,
          description: description,
          movieId: movie.id,
          userId: id,
        },
      });
      setDescription("");
      setOpenSnackSuccess(true);
      setValidation(false);
      handleClose();
    } else {
      setValidation(true);
    }
  };

  const handleCloseSnack = () => {
    setOpenSnackSuccess(false);
  };

  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setValidation(false);
    setOpen(false);
  };

  return (
    <React.Fragment>
      <Button
        onClick={handleOpen}
        aria-label="Review movie"
        style={{ width: "35%", alignSelf: "center", fontWeight: "bold" }}
      >
        Review movie
      </Button>
      <Modal
        hideBackdrop
        open={open}
        onClose={handleClose}
        aria-labelledby={"Review" + movie.title}
      >
        <Box sx={{ ...style, width: 450 }}>
          <IconButton
            sx={{ top: 0, right: 0, position: "fixed", color: "white" }}
            aria-label="Close window"
            onClick={handleClose}
          >
            <CloseIcon />
          </IconButton>
          <Typography id="child-modal-title" variant="h4" align="center">
            Review
          </Typography>
          <Divider sx={{ my: 0.5, backgroundColor: "white" }} />
          <Box>
            <Stack spacing={2} padding={2} alignItems={"center"}>
              <Rating
                name="rate-movie"
                sx={customStars}
                value={rating}
                precision={0.5}
                getLabelText={getLabelText}
                onChange={(_event, newRating) => {
                  setRating(newRating);
                }}
                onChangeActive={(event, newHover) => {
                  setHover(newHover);
                }}
                defaultValue={0.5}
                size={"large"}
                aria-label="Star rating"
                id="star-rating-input"
                readOnly={false}
              />
              {rating !== null && (
                <Typography>
                  Your rating: {labels[hover !== -1 ? hover : rating]}
                </Typography>
              )}
            </Stack>
          </Box>
          <TextField
            id="text-input"
            label="Write your review here"
            placeholder="What did you think of the movie?"
            multiline
            fullWidth
            minRows={4}
            maxRows={4}
            variant="outlined"
            color="secondary"
            error={validation}
            data-testid="text"
            helperText={
              validation ? "Please write a review before submitting." : ""
            }
            aria-label="Write review here"
            onChange={(newdesc) => setDescription(newdesc.target.value)}
            InputProps={{ inputProps: { style: { color: "#ffff" } } }}
            InputLabelProps={{
              style: { color: "#fff" },
            }}
            sx={{
              color: "white",
              fontSize: 70,
              "& .MuiOutlinedInput-root": {
                "& fieldset": {
                  border: "2px solid purple",
                },
                "&:hover fieldset": {
                  border: "2px solid purple",
                },
              },
            }}
          />
          <Stack padding={2} alignItems={"center"}>
            <Stack direction={"row"} spacing={10}>
              <Button
                aria-label="Abort review"
                variant="contained"
                color="error"
                onClick={handleClose}
                style={{ height: 60, width: 120, fontWeight: "bold" }}
              >
                Abort
              </Button>
              <Button
                aria-label="Save review"
                variant="contained"
                color="success"
                onClick={handleSave}
                style={{ height: 60, width: 120, fontWeight: "bold" }}
              >
                Save
              </Button>
            </Stack>
          </Stack>
        </Box>
      </Modal>
      <Snackbar
        open={openSnackSuccess}
        autoHideDuration={1500}
        onClose={handleCloseSnack}
      >
        <Alert severity="success">Your review was saved!</Alert>
      </Snackbar>
    </React.Fragment>
  );
}
