export {}
declare global {
    namespace Cypress {
        interface Chainable {
            cleanup(): Chainable<void>;
        }
    }
}