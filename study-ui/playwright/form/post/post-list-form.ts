import { Page, expect } from '@playwright/test';

export class PostListForm{
    constructor(private page:Page){}

    elements = {
        btnNew: () => this.page.getByLabel('Add new post'),
        row: (value: string)=> this.page.getByRole('cell', {name: value}).first(),
    }

    async clickNew(){
        await this.elements.btnNew().click();
    }

    async containsRow(value: string){
        await expect(this.elements.row(value)).toBeVisible();
    }

    async notContainsRow(value: string){
        await expect(this.elements.row(value)).not.toBeVisible();
    }

}