import { render, screen, cleanup, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import Movie from "../../main/components/Movie";
import { Typography } from "@mui/material";
import { MockedProvider } from "@apollo/client/testing";
import { GET_MOVIES } from "../../main/queries/query";
import userEvent from "@testing-library/user-event";

/**
 * @tests The movie component - namely that the component renders
 * and displays the title passed as prop.
 */
const mocks = [
  {
    request: {
      query: GET_MOVIES,
      variables: {
        id: 367215,
      },
    },
    result: {
      data: {
        title: "The Fear of 13",
        id: 367215,
        averageRating: 8.2,
        tagline: "We are the stories we tell.",
        released: "10/15/2015",
        poster_url:
          "https://image.tmdb.org/t/p/w185/n87Rk9b3DeOMvYAHZPGnsRZCK0R.jpg",
        actors: [
          {
            name: "Sammy Silverwatch",
          },
        ],
        director: [
          {
            name: "David Sington",
          },
        ],
      },
    },
  },
];

const prop = {
  title: "The Fear of 13",
  id: 367215,
  averageRating: 8.2,
  tagline: "We are the stories we tell.",
  released: "10/15/2015",
  poster_url: "https://image.tmdb.org/t/p/w185/n87Rk9b3DeOMvYAHZPGnsRZCK0R.jpg",
  actors: [
    {
      name: "Sammy Silverwatch",
    },
  ],
  director: [
    {
      name: "David Sington",
    },
  ],
};

it("renders without error", async () => {
  render(
    <MockedProvider mocks={mocks} addTypename={false}>
      <Movie {...prop} />
    </MockedProvider>
  );

  expect(await screen.findByText("Movie details")).toBeInTheDocument();
});

it("Renders movie title without error", async () => {
  render(
    <MockedProvider mocks={mocks} addTypename={false}>
      <Movie {...prop} />
    </MockedProvider>
  );

  userEvent.click(await screen.findByText("Movie details"));
  expect(await screen.findByText("The Fear of 13")).toBeInTheDocument();
});
