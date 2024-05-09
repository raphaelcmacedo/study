export class DetailForm{
    elements = {
        subjectInput: () => cy.get('.mat-mdc-form-field-infix > [data-cy="subject"]'),
        textInput: () => cy.get('.mat-mdc-form-field-infix > [data-cy="text"]'),
        btnSubmit: () => cy.get('[data-cy="btn-submit"]'),
        btnCancel: () => cy.get('[data-cy="btn-cancel"]'),
        errSubject: () => cy.get('[data-cy="err-subject"]'),
        errText: () => cy.get('[data-cy="err-text"]'),
        errForm: () => cy.get('[data-cy="err-form"]'),
    }

    typeSubject(value: string){
        if(!value) return;
        this.elements.subjectInput().type(value);
    }

    touchSubject(){
        this.elements.subjectInput().click();
    }

    typeText(value: string){
        if(!value) return;
        this.elements.textInput().type(value);
    }

    touchText(){
        this.elements.textInput().click();
    }

    touchForm(){
        this.touchSubject();
        this.touchText();
        this.touchSubject();
    }

    clickSubmit(){
        this.elements.btnSubmit().click();
    }

    clickCancel(){
        this.elements.btnCancel().click();
    }

    submitIsDisabled(){
        this.elements.btnSubmit().should('be.disabled');
    }

    submitIsEnabled(){
        this.elements.btnSubmit().should('be.enabled');
    }

    containsSubjectError(){
        this.elements.errSubject().contains('Subject is required.').should('exist');
    }
    
    containsTextError(){
        this.elements.errText().contains('Text is required.').should('exist');
    }

    containsFormError(value: string){
        this.elements.errForm().contains(value).should('exist');
    }
}