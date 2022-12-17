import "expect-puppeteer";
import expectPuppeteer from "expect-puppeteer";

const pause = async (ms: number) => {
  await new Promise((res) => setTimeout(res, ms));
};

describe("E2E test", () => {
  beforeAll(async () => {
    await page.goto("http://localhost:3000");
    await pause(3000);
  });

  it("Should load page elements", async () => {
    await expectPuppeteer(page).toMatch("MovieMix");
    await expectPuppeteer(page).toMatch("Trending");
    await expectPuppeteer(page).toMatch("Rating");
    await expectPuppeteer(page).toMatch("Your Ratings");
  });

  /**
   * Filmer i databasen er absolutte fra vårt perspektiv. I et større perspektiv er testen kanskje litt manglende
   */
  it("Should filter", async () => {
    await expectPuppeteer(page).toMatch("Whiplash");
    await expectPuppeteer(page).toClick("#ratingFilter");
    await expectPuppeteer(page).toMatch("Robert the Doll");
    await expectPuppeteer(page).toClick("label", { text: "Genres" });
    await pause(500);
    await expectPuppeteer(page).toClick("li", { text: "Science Fiction" });
    await expectPuppeteer(page).toMatch("Zodiac");
    await pause(500);
    await expectPuppeteer(page).toClick("#ratingFilter");
    await expectPuppeteer(page).toMatch("Edge of Tomorrow");
    await expectPuppeteer(page).toClick("span", { text: "Only Trending" });
    await pause(500);
    await expectPuppeteer(page).toMatch("Jurassic World");
  });

  it("Should add rating", async () => {
    await expectPuppeteer(page).toClick("button", { text: "Movie details" });
    await expectPuppeteer(page).toClick("button", { text: "Review movie" });
    await expectPuppeteer(page).toFill("#text-input", "Test review");
    await expectPuppeteer(page).toClick("button", { text: "Save" });
    await expectPuppeteer(page).toClick("button[aria-label='Close window']");

    await expectPuppeteer(page).toClick("button", { text: "Your Ratings" });
    await expectPuppeteer(page).toMatch("Test review");
  });

  it("Should search", async () => {
    await page.goto("http://localhost:3000", { waitUntil: "load" });
    await expectPuppeteer(page).toFill("#searchbar", "Guardians");
    const navigation = page.waitForNavigation();
    await expectPuppeteer(page).toClick("button", { text: "Search" });
    await navigation;
    await expectPuppeteer(page).toMatch("Guardians of the Galaxy");
  });
});
