import "@testing-library/jest-dom";
import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { Filter } from "../../main/components/Filter";

/**
 * @tests Test the different buttons inside the filter component
 * and see if they call functions with the correct parameters.
 */

const setGenre = jest.fn();
const setSortRating = jest.fn();
const setSortPopularity = jest.fn();
const setFiltering = jest.fn();
const setLimit = jest.fn();
const sortRating = "ASC";
const sortPopularity = "DESC";

function setup() {
  const component = render(
    <Filter
      onGenreChange={setGenre}
      onRatingChange={setSortRating}
      onPopularityChange={setSortPopularity}
      onFilteringChange={setFiltering}
      onLimitChange={setLimit}
      sortRating={sortRating}
      sortPopularity={sortPopularity}
    />
  );

  return {
    ...component,
  };
}

describe("Filter Test", () => {
  it("Only Trending radio button to call with correct value, limit set and correct button is disabled", async () => {
    const { getByLabelText, getByText } = setup();
    userEvent.click(getByLabelText(/Only Trending/i));
    expect(setFiltering).toHaveBeenCalledWith("onlyPopularity");
    expect(setFiltering).toHaveBeenCalledTimes(1);
    expect(setLimit).toHaveBeenCalledWith(12);
    expect(setLimit).toHaveBeenCalledTimes(1);

    expect(
      getByText(/Rating/i, {
        selector: "button",
      })
    ).toBeDisabled();

    expect(
      getByText(/Trending/i, {
        selector: "button",
      })
    ).not.toBeDisabled();
  });

  it("Only Trending radio button to call with correct value, limit set and correct button is disabled", async () => {
    const { getByLabelText, getByText } = setup();
    userEvent.click(getByLabelText(/Only Rating/i));
    expect(setFiltering).toHaveBeenCalledWith("onlyRating");
    expect(setFiltering).toHaveBeenCalledTimes(1);
    expect(setLimit).toHaveBeenCalledWith(12);
    expect(setLimit).toHaveBeenCalledTimes(1);

    expect(
      getByText(/Trending/i, {
        selector: "button",
      })
    ).toBeDisabled();

    expect(
      getByText(/Rating/i, {
        selector: "button",
      })
    ).not.toBeDisabled();
  });

  it("Both Enable radio button to call with correct value, limit set and none of buttons is disabled", async () => {
    const { getByLabelText, getByText } = setup();
    userEvent.click(getByLabelText(/Only Rating/i));
    userEvent.click(getByLabelText(/Enable Both/i));
    expect(setFiltering).toHaveBeenCalledWith("bothEnabled");
    expect(setFiltering).toHaveBeenCalledTimes(2);
    expect(setLimit).toHaveBeenCalledWith(12);
    expect(setLimit).toHaveBeenCalledTimes(2);

    expect(
      getByText(/Trending/i, {
        selector: "button",
      })
    ).not.toBeDisabled();

    expect(
      getByText(/Rating/i, {
        selector: "button",
      })
    ).not.toBeDisabled();
  });

  it("When clicking trending button change sorting direction and limit", async () => {
    const { getByText } = setup();

    userEvent.click(
      getByText(/Trending/i, {
        selector: "button",
      })
    );

    expect(setSortPopularity).toHaveBeenCalledTimes(1);
    expect(setSortPopularity).toHaveBeenCalledWith("ASC");
    expect(setLimit).toHaveBeenCalledWith(12);
    expect(setLimit).toHaveBeenCalledTimes(1);
  });

  it("When clicking rating button change sorting direction and limit", async () => {
    const { getByText } = setup();

    userEvent.click(
      getByText(/Rating/i, {
        selector: "button",
      })
    );

    expect(setSortRating).toHaveBeenCalledTimes(1);
    expect(setSortRating).toHaveBeenCalledWith("DESC");
    expect(setLimit).toHaveBeenCalledWith(12);
    expect(setLimit).toHaveBeenCalledTimes(1);
  });
});
