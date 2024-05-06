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
})

