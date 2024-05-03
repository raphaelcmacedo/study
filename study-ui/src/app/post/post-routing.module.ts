import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PostListComponent } from './component/post-list/post-list.component';
import { PostDetailComponent } from './component/post-detail/post-detail.component';

const postRoutes: Routes = [
  {path: '', component: PostListComponent},
] 

@NgModule({
  imports: [
    RouterModule.forChild(postRoutes)
  ],
  exports:[
    RouterModule
  ]
})
export class PostRoutingModule { }
