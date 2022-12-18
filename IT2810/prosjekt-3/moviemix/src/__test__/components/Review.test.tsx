import { render, screen, cleanup, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom";
import { Review } from "../../main/components/Review";
import { MockedProvider } from "@apollo/client/testing";
import { CREATE_RATING } from "../../main/queries/query";
import { useMutation } from "@apollo/client";
import userEvent from "@testing-library/user-event";

/**
 * @tests The review component - namely that the component renders
 * and displays helper-text on empty input.
 */
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

const mocks = [
  {
    request: {
      query: CREATE_RATING,
      variables: {
        rating: 0.5,
        description: "",
        movieId: 367215,
        userId: "id",
      },
    },
    result: {
      data: {},
    },
  },
];

it("Renders Review without error", async () => {
  render(
    <MockedProvider mocks={mocks} addTypename={false}>
      <Review {...prop} />
    </MockedProvider>
  );

  expect(await screen.findByText("Review movie")).toBeInTheDocument();
});

it("Renders helpertext on empty input", async () => {
  render(
    <MockedProvider mocks={mocks} addTypename={false}>
      <Review {...prop} />
    </MockedProvider>
  );

  userEvent.click(await screen.findByText("Review movie"));
  userEvent.click(await screen.findByText("Save"));

  expect(
    await screen.findByText("Please write a review before submitting.")
  ).toBeInTheDocument();
});
