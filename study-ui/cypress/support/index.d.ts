// describe custom Cypress commands in this file

// load the global Cypress types
/// <reference types="cypress" />

export {}
declare namespace Cypress {
  interface Chainable {
    /**
     * Custom command to cleanup the environment before tests
     * @example cy.dataCy('greeting')
     */
    cleanup(): Chainable<any>
  }
}
