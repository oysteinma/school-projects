import App from "../../main/App";
import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";

it("check if App renders succesfully", () => {
  const { container } = render(<App />);
  expect(container).toMatchSnapshot();
});
