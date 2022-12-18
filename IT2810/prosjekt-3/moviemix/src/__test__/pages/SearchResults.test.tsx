import SearchResults from "../../main/pages/SearchResults";
import { render } from "@testing-library/react";
import { MockedProvider } from "@apollo/client/testing";
import { HashRouter as Router } from "react-router-dom";

/**
 * @tests The search results page component - namely that the component renders
 */
it("check if SearchResults renders succesfully", () => {
  const { container } = render(
    <MockedProvider>
      <Router>
        <SearchResults />
      </Router>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
