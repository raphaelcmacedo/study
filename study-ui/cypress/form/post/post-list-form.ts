export class PostListForm{
    elements = {
        btnNew: () => cy.get('[data-cy="btn-new"]'),
        subjectRow: ()=> cy.get('[data-cy="subject"]'),
        textRow: ()=> cy.get('[data-cy="text"]'),
    }

    clickNew(){
        this.elements.btnNew().click();
    }

    containsRowWithSubject(value: string){
        this.elements.subjectRow().contains(value).should('exist');
    }

    containsRowWithText(value: string){
        this.elements.textRow().contains(value).should('exist');
    }
}