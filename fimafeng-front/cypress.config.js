const { defineConfig } = require("cypress");

module.exports = defineConfig({
  env: {
    baseUrl: process.env.CYPRESS_BASE_URL || "http://localhost:3000",
  },

  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
