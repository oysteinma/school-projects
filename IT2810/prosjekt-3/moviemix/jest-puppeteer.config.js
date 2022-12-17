module.exports = {
  launch: {
    dumpio: true,
    headless: true,
    product: "chrome",
  },
  server: {
    command: "npm start",
    port: 3000,
    launchTimeout: 15000,
  },
  browserContext: "default",
};
