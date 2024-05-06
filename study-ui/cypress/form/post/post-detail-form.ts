export class DetailForm{
    elements = {
        subjectInput: () => cy.get('.mat-mdc-form-field-infix > [data-cy="subject"]'),
        textInput: () => cy.get('.mat-mdc-form-field-infix > [data-cy="text"]'),
        btnSubmit: () => cy.get('[data-cy="btn-submit"]'),
        btnCancel: () => cy.get('[data-cy="btn-cancel"]'),
    }

    typeSubject(value: string){
        if(!value) return;
        this.elements.subjectInput().type(value);
    }

    typeText(value: string){
        if(!value) return;
        this.elements.textInput().type(value);
    }

    clickSubmit(){
        this.elements.btnSubmit().click();
    }

    clickCancel(){
        this.elements.btnCancel().click();
    }
}