import { DetailForm } from "../form/post/post-detail-form";
import { PostListForm } from "../form/post/post-list-form";

const listForm = new PostListForm();
const detailForm = new DetailForm();    

describe('Post crud operations', ()=>{
    beforeEach(()=>{
        cy.visit('/');
        cy.get('[data-cy="btn-post"] > .mdc-button__label').click();
    })
    
    it('save new post', ()=>{
        const post = {
            subject: new Date().valueOf().toString(),
            text: 'Created from cypress',
        }
    
        listForm.clickNew();
    
        detailForm.typeSubject(post.subject);
        detailForm.typeText(post.text);
        detailForm.clickSubmit();
    
        listForm.containsRowWithSubject(post.subject);
        listForm.containsRowWithText(post.text);
        
    })

    it('validate required fields', ()=>{
        const post = {
            subject: '',
            text: '',
        }
    
        listForm.clickNew();
    
        detailForm.touchForm();

        detailForm.typeSubject(post.subject);
        detailForm.typeText(post.text);

        detailForm.containsSubjectError();
        detailForm.containsTextError();
        detailForm.submitIsDisabled();
        
    })

    it('submit is disabled when a new form is opened', ()=>{
        const post = {
            subject: '',
            text: '',
        }
    
        listForm.clickNew();
        detailForm.submitIsDisabled();
        
    })

    it('cancel just close the window without saving it', ()=>{
        const post = {
            subject: 'Should not save',
            text: 'Created from cypress',
        }
    
        listForm.clickNew();
    
        detailForm.typeSubject(post.subject);
        detailForm.typeText(post.text);
        detailForm.clickCancel();
    
        listForm.notContainsRowWithSubject(post.subject);
        
        
    })
    
})

