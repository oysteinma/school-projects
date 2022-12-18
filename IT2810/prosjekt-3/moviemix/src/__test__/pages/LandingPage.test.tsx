import LandingPage from "../../main/pages/LandingPage";
import { render } from "@testing-library/react";
import { MockedProvider } from "@apollo/client/testing";
import { HashRouter as Router } from "react-router-dom";

/**
 * @tests The landing page component - namely that the component renders
 */

it("check if LandingPage renders succesfully", () => {
  const { container } = render(
    <MockedProvider>
      <Router>
        <LandingPage />
      </Router>
    </MockedProvider>
  );
  expect(container).toMatchSnapshot();
});
