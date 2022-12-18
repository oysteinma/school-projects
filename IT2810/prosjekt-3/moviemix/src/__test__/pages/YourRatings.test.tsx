import YourRatings from "../../main/pages/YourRatings";
import { render } from "@testing-library/react";
import { MockedProvider } from "@apollo/client/testing";
import { HashRouter as Router } from "react-router-dom";

/**
 * @tests The your ratings page component - namely that the component renders
 */

it("check if YourRatings renders succesfully", () => {
  const { container } = render(
    <MockedProvider>
      <Router>
        <YourRatings />
      </Router>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
