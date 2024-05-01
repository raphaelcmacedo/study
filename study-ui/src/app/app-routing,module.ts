import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';


const appRoutes: Routes = [
  
  {
    path: 'post',
    loadChildren: () => import('./post/post.module').then(m => m.PostModule),
    data: { preload: true }
  },
  //{ path: '',   redirectTo: '/superheroes', pathMatch: 'full' },
  //{ path: '**', component: PageNotFoundComponent } TODO: Add PageNotFound
];

@NgModule({
  imports: [
    RouterModule.forRoot(
      appRoutes,
      {
        enableTracing: false, // <-- debugging purposes only
        //preloadingStrategy: SelectivePreloadingStrategyService,
      }
    )
  ],
  exports: [
    RouterModule
  ]
})
export class AppRoutingModule { }
