import { useApi } from "../../main/api/Api";

/**
 * Tests for the API-request
 */

const data = useApi();
var tempProjectID: string = "4211";

it("Test Issue Call", async () => {
  const user = await data.getIssues(tempProjectID, "");
  expect(user.data[0].web_url.slice(0, 31)).toBe(
    "https://gitlab.stud.idi.ntnu.no"
  );
});

it("Test Repo Call", async () => {
  const repo = await data.getRepo(tempProjectID, "");
  expect(repo.data.web_url.slice(0, 31)).toBe(
    "https://gitlab.stud.idi.ntnu.no"
  );
});
