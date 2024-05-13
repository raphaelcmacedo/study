import {test as base} from "@playwright/test";
import { PostListForm } from "../../form/post/post-list-form";
import { PostDetailForm } from "../../form/post/post-detail-form";

type Fixtures = {
    postListForm:PostListForm;
    postDetailForm:PostDetailForm;
  };

export const test = base.extend<Fixtures>({
    postListForm: async ({page}, use) =>{
        const postListForm: PostListForm = new PostListForm(page);
        await use(postListForm);
    },
    postDetailForm: async ({page}, use) =>{
        const postDetailForm: PostDetailForm = new PostDetailForm(page);
        await use(postDetailForm);
    },
    
})