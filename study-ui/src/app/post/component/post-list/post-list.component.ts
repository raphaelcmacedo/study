import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { PostService } from '../../service/PostService';
import { Post } from '../../model/Post';
import { Page } from '../../model/Page';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatDialog } from '@angular/material/dialog';
import { PostDetailComponent } from '../post-detail/post-detail.component';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css',
})
export class PostListComponent implements OnInit {
  posts: Page<Post> | null = null;
  dataSource = new MatTableDataSource<Post>();
  displayedColumns: string[] = ['id', 'subject', 'text', 'actions'];
  loading = false;
  error: string | null = null;
  currentPage = 0;
  pageSize = 1000;


  constructor(private postService: PostService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.getPosts();
  }

  private getPosts(): void {
    this.loading = true;
    this.error = null;

    this.postService.getPosts(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.loading = false;
        this.posts = response;
        this.dataSource = new MatTableDataSource<Post>(response.content);
        this.currentPage = response.pageable.pageNumber;
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Failed to load posts. Please try again later.';
      },
    });
  }

  openDetailDialog(id: string | number | null): void {
    const dialogData = {
      data: { id: id },
      minWidth: '500px',
      minHeight: '400px',
    };

    const dialogRef = this.dialog.open(PostDetailComponent, dialogData);

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.getPosts();
      }
    });
  }

  editPost(post: Post) {
    this.openDetailDialog(post.id);
  }

  deletePost(post: Post) {
    this.postService.deletePost(post.id).subscribe({
      next:() => {
        this.getPosts();
      },
      error:(err) =>{
        this.error = "Failed to delete post";
      }
    });
  }
}
