import { test } from './fixture/post/post-fixture';

test.describe('Post crud operations', () => {
  test.beforeEach(async ({ page }) => {
    await page.goto('');
    await page.getByRole('link', { name: 'Post' }).click();
  });

  test('save new post', async ({ page, postListForm, postDetailForm }) => {
    const post = {
      subject: new Date().valueOf().toString(),
      text: 'Created from cypress',
    };
    await postListForm.clickNew();
    
    await postDetailForm.typeSubject(post.subject);
    await postDetailForm.typeText(post.text);
    await postDetailForm.clickSubmit();

    await postListForm.containsRow(post.subject);
    await postListForm.containsRow(post.text);
  });

  test('validate required fields', async ({ page, postListForm, postDetailForm }) => {
    const post = {
      subject: '',
      text: '',
    };
    await postListForm.clickNew();
    
    await postDetailForm.touchForm();
    await postDetailForm.typeSubject(post.subject);
    await postDetailForm.typeText(post.text);

    await postDetailForm.containsSubjectError();
    await postDetailForm.containsTextError();
    await postDetailForm.submitIsDisabled();
  });

  test('submit is disabled when a new form is opened', async ({ page, postListForm, postDetailForm }) => {
    const post = {
      subject: '',
      text: '',
    };
    await postListForm.clickNew();
    await postDetailForm.submitIsDisabled();
  });

  test('cancel just close the window without saving it', async ({ page, postListForm, postDetailForm }) => {
    const post = {
      subject: 'Should not save',
      text: 'Created from cypress',
    };
    await postListForm.clickNew();

    await postDetailForm.typeSubject(post.subject);
    await postDetailForm.typeText(post.text);
    await postDetailForm.clickCancel();

    await postListForm.notContainsRow(post.subject);
  });

  test('cannot save posts with duplicated subject', async ({ page, postListForm, postDetailForm }) => {
    const post = {
      subject: 'Duplicated',
      text: 'Created from cypress',
    };

    //Save first attempt
    await postListForm.clickNew();
    await postDetailForm.typeSubject(post.subject);
    await postDetailForm.typeText(post.text);
    await postDetailForm.clickSubmit();
    
    //Save second attempt
    await postListForm.clickNew();
    await postDetailForm.typeSubject(post.subject);
    await postDetailForm.typeText(post.text);
    await postDetailForm.clickSubmit();

    await postDetailForm.containsFormError(`Error while saving the post. Subject ${post.subject} already exists`);
  });


});
