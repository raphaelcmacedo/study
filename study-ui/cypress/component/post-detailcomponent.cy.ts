import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog"
import { PostDetailComponent } from "../../src/app/post/component/post-detail/post-detail.component"
import { provideHttpClient } from "@angular/common/http"
import { provideAnimations } from "@angular/platform-browser/animations"
import { DetailForm } from "../form/post/post-detail-form";

const detailForm = new DetailForm();    

function mount():void{
    cy.mount(PostDetailComponent, {
        providers:[
            { provide: MatDialogRef, useValue: {} },
            { provide: MAT_DIALOG_DATA, useValue: {} },
            provideHttpClient(),
            provideAnimations()
        ]    
    })
}

describe('PostDetailComponent', ()=>{
    beforeEach(()=>{
        mount();
    })

    it('can mount', ()=>{
        
    })

    it('contains all elements', ()=>{
        detailForm.elements.subjectInput().should('exist');
        detailForm.elements.textInput().should('exist');
        detailForm.elements.btnSubmit().should('exist');
        detailForm.elements.btnCancel().should('exist');
    })

    it('enables submit when form is filled', ()=>{
        const post = {
            subject: 'Subject',
            text: 'Created from cypress',
        }

        detailForm.submitIsDisabled();
        detailForm.typeSubject(post.subject);
        detailForm.typeText(post.text);
        detailForm.submitIsEnabled();
    })
})


