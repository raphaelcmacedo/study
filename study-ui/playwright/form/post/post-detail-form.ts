import { Page, expect } from '@playwright/test';

export class PostDetailForm{
    constructor(private page:Page){}

    elements = {
        subjectInput: () => this.page.getByLabel('Subject'),
        textInput: () => this.page.getByLabel('Text'),
        btnSubmit: () => this.page.getByRole('button', { name: 'Submit' }),
        btnCancel: () => this.page.getByRole('button', { name: 'Cancel' }),
        errSubject: () => this.page.locator('[data-cy="err-subject"]'),
        errText: () => this.page.locator('[data-cy="err-text"]'),
        errForm: () => this.page.locator('[data-cy="err-form"]'),
    }

    async typeSubject(value: string){
        if(!value) return;
        await this.elements.subjectInput().fill(value);
    }

    async touchSubject(){
        await this.elements.subjectInput().click();
    }

    async typeText(value: string){
        if(!value) return;
        await this.elements.textInput().fill(value);
    }

    async touchText(){
        await this.elements.textInput().click();
    }

    async touchForm(){
        await this.touchSubject();
        await this.touchText();
        await this.touchSubject();
    }

    async clickSubmit(){
        await this.elements.btnSubmit().click();
    }

    async clickCancel(){
        await this.elements.btnCancel().click();
    }

    async submitIsDisabled(){
        await expect(this.elements.btnSubmit().isDisabled).toBeTruthy();
    }

    async submitIsEnabled(){
        await expect(this.elements.btnSubmit().isEnabled).toBeTruthy();
    }

    async containsSubjectError(){
        await expect(this.elements.errSubject()).toContainText('Subject is required.');
    }
    
    async containsTextError(){
        await expect(this.elements.errText()).toContainText('Text is required.');
    }

    async containsFormError(value: string){
        await expect(this.elements.errForm()).toContainText(value);
    }


}