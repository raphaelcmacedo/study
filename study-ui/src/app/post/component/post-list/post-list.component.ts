import { Component, OnInit } from '@angular/core';
import { PostService } from '../../service/PostService';
import { Post } from '../../model/Post';
import { Page } from '../../model/Page';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent implements OnInit {
  
  posts: Page<Post> | null = null;
  loading = false;
  error: string | null = null;
  currentPage = 0;
  pageSize = 10;

  constructor(private postService:PostService){

  }

  ngOnInit(): void {
    this.getPosts(this.currentPage);
  }

  private getPosts(page:number):void{
    this.loading = true;
    this.error = null;

    this.postService.getPosts(page, this.pageSize).subscribe({
      next: (response) => {
        this.loading = false;
        this.posts = response;
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Failed to load posts. Please try again later.';
      }
    });
  }

  loadPreviousPage(): void {
    this.currentPage--;
    this.getPosts(this.currentPage);
  }

  loadNextPage(): void {
    this.currentPage++;
    this.getPosts(this.currentPage);
  }

  

}
