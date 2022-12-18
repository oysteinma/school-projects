import "@testing-library/jest-dom";
import { act, render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { MockedProvider } from "@apollo/client/testing";
import { HashRouter as Router } from "react-router-dom";
import { GET_SEARCH_RESULT } from "../../main/queries/query";
import Search from "../../main/components/Search";
import CardView from "../../main/components/CardView";

/**
 * @tests Test the search component, mocks the query and results
 * to see if the correct data is displayed.
 *
 */

const pause = async (ms: number) => {
  await new Promise((res) => setTimeout(res, ms));
};
const mocks = [
  {
    request: {
      query: GET_SEARCH_RESULT,
      variables: {
        searchString: "Lord of the Rings",
      },
    },
    result: {
      data: {
        fuzzyMoviesSearch: [
          {
            id: 120,
            title: "The Lord of the Rings: The Fellowship of the Ring",
            averageRating: 9.5,
            tagline:
              "Young hobbit Frodo Baggins, after inheriting a mysterious ring from his uncle Bilbo...",
            released: "12/19/2001",
            poster_url: "",
            director: [{ name: "Peter Jackson" }],
          },
          {
            id: 121,
            title: "The Lord of the Rings: The Two Towers",
            averageRating: 9.3,
            tagline:
              "Frodo and Sam are trekking to Mordor to destroy the One Ring o...",
            released: "12/18/2002",
            poster_url: "",
            director: [{ name: "Peter Jackson" }],
          },
          {
            id: 122,
            title: "The Lord of the Rings: The Return of the King",
            averageRating: 9.3,
            tagline:
              "Aragorn is revealed as the heir to the ancient kings as he, Gandalf and...",
            released: "12/17/2003",
            poster_url: "",
            director: [{ name: "Peter Jackson" }],
          },
        ],
      },
    },
  },
];

describe("Search test", () => {
  it("search bar loads", async () => {
    act(() => {
      render(
        <MockedProvider mocks={mocks} addTypename={false}>
          <Router>
            <Search />
          </Router>
        </MockedProvider>
      );
    });
    const searchBar = (await document.getElementById(
      "searchbar"
    )) as HTMLInputElement;
    await act(async () => {
      await userEvent.type(searchBar, "Test Value");
    });
    expect(searchBar.value).toBe("Test Value");
    expect(searchBar).toBeTruthy();
  });
  it("search returns correct result", async () => {
    act(() => {
      render(
        <MockedProvider mocks={mocks} addTypename={false}>
          <Router>
            <Search />
            <CardView />
          </Router>
        </MockedProvider>
      );
    });
    const searchBar = (await document.getElementById(
      "searchbar"
    )) as HTMLInputElement;
    userEvent.type(searchBar, "Lord of the Rings");
    const searchButton = screen.getByText("Search", {
      selector: "button",
    });
    userEvent.click(searchButton);
    await act(async () => {
      await pause(500);
    });
    expect(
      await screen.getByText("The Lord of the Rings: The Return of the King", {
        exact: false,
      })
    ).toBeInTheDocument();
  });
});
