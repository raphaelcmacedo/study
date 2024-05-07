Cypress.Commands.add('cleanup', () =>{
    cy.task('clearDB');
});