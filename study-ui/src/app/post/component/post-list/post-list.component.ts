import { Component, OnInit, inject } from '@angular/core';
import { PostService } from '../../service/PostService';
import { Post } from '../../model/Post';
import { Page } from '../../model/Page';
import { MatTableDataSource, MatTable, MatColumnDef, MatHeaderCellDef, MatHeaderCell, MatCellDef, MatCell, MatHeaderRowDef, MatHeaderRow, MatRowDef, MatRow } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { PostDetailComponent } from '../post-detail/post-detail.component';
import { MatIcon } from '@angular/material/icon';
import { MatFabButton, MatIconButton } from '@angular/material/button';
import { MatProgressSpinner } from '@angular/material/progress-spinner';
import { NgIf } from '@angular/common';
import { MatCard, MatCardHeader, MatCardTitle } from '@angular/material/card';

@Component({
    selector: 'app-post-list',
    templateUrl: './post-list.component.html',
    styleUrl: './post-list.component.css',
    standalone: true,
    imports: [
        MatCard,
        MatCardHeader,
        MatCardTitle,
        NgIf,
        MatProgressSpinner,
        MatFabButton,
        MatIcon,
        MatTable,
        MatColumnDef,
        MatHeaderCellDef,
        MatHeaderCell,
        MatCellDef,
        MatCell,
        MatIconButton,
        MatHeaderRowDef,
        MatHeaderRow,
        MatRowDef,
        MatRow,
    ],
})
export class PostListComponent implements OnInit {
  posts: Page<Post> | null = null;
  dataSource = new MatTableDataSource<Post>();
  displayedColumns: string[] = ['id', 'subject', 'text', 'actions'];
  loading = false;
  error: string | null = null;
  currentPage = 0;
  pageSize = 1000;

  postService = inject(PostService);
  dialog = inject(MatDialog)

  ngOnInit(): void {
    this.getPosts();
  }

  private getPosts(): void {
    this.loading = true;
    this.error = null;

    this.postService.getPosts(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        this.loading = false;
        this.error = null;
        this.posts = response;
        this.dataSource = new MatTableDataSource<Post>(response.content);
        this.currentPage = response.pageable.pageNumber;
      },
      error: (err) => {
        this.loading = false;
        if(typeof err.error === 'string'){
          this.error = `Failed to load posts. ${err.error}`;
        }else{
          this.error = 'Failed to load posts. Try again later.';
        }
        
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
