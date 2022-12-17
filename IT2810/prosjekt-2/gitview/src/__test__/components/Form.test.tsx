import { render, screen } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import { Form } from "../../main/components/Form";

it("trigger alert when missing input of project token", async () => {
  global.alert = jest.fn();
  const { getByPlaceholderText } = render(<Form />);

  const inputID = getByPlaceholderText("Project ID");
  userEvent.type(inputID, "1234");

  userEvent.click(screen.getByRole("button", { name: "Connect!" }));

  expect(global.alert).toHaveBeenCalledTimes(1);
});
