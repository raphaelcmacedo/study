import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PostListComponent } from './component/post-list/post-list.component';
import { PostDetailComponent } from './component/post-detail/post-detail.component';
import { PostRoutingModule } from './post-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MaterialModule } from '../material.module';



@NgModule({
  declarations: [
    PostListComponent,
    PostDetailComponent
  ],
  imports: [
    HttpClientModule,
    CommonModule,
    PostRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MaterialModule
  ],
  providers: [ HttpClientModule]
})
export class PostModule { }
