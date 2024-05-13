import { defineConfig } from "cypress";

const pgp = require('pg-promise');
const { db } = require('././support/db.ts');

export default defineConfig({
  component: {
    devServer: {
      framework: "angular",
      bundler: "webpack",
    },
    specPattern: "**/*.cy.ts",
  },

  e2e: {
    setupNodeEvents(on, config) {
      on('task', {

        clearDB(){
          const query = 'Delete from Post';
          db.none(query);

          return null;
        }
        
      })
    },
    baseUrl:'http://localhost:4200'
  },
});
