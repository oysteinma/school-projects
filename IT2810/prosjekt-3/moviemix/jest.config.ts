export default {
  testTimeout: 30000,
  testMatch: ["**/?(*.)+(spec|test).(ts|tsx)"],
  preset: "jest-puppeteer",
  transform: {
    "^.+\\.ts$": "ts-jest",
  },
  testPathIgnorePatterns: ["/node_modules/", "dist"],
  moduleFileExtensions: ["ts", "tsx", "js", "jsx", "json", "node"],
};
