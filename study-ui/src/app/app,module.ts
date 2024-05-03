import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { Router } from '@angular/router';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing,module';
import { HttpClientModule } from '@angular/common/http';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MaterialModule } from './material.module';


@NgModule({
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,  
    AppRoutingModule,
    HttpClientModule,
    MaterialModule,
  ],
  declarations: [
    AppComponent,
  ],
  bootstrap: [ AppComponent ],
  providers: [
    provideAnimationsAsync()
  ]
})
export class AppModule {
  
  constructor(router: Router) {
    
  }
}