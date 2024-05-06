import { RouterModule } from "@angular/router"
import { AppComponent } from "../../src/app/app.component"
import { MatToolbarModule } from "@angular/material/toolbar"
import { MatButtonModule } from "@angular/material/button"

describe('AppComponent', ()=>{
    it('can mount', ()=>{
        cy.mount(AppComponent, {
            imports:[
                RouterModule,
                MatToolbarModule,
                MatButtonModule
              ],
              
        })
    })
})