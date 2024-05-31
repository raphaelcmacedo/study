import { Component, OnInit, inject, signal } from '@angular/core';
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
import { single } from 'rxjs';

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
  //Signals
  posts = signal<Page<Post> | null>(null);
  dataSource = signal(new MatTableDataSource<Post>());
  loading = signal(false);
  error = signal<string | null>(null);
  
  //Material datagrid
  displayedColumns: string[] = ['id', 'subject', 'text', 'actions'];
  currentPage = signal(0);
  pageSize = signal(1000);

  //Injected
  postService = inject(PostService);
  dialog = inject(MatDialog)

  ngOnInit(): void {
    this.getPosts();
  }

  private getPosts(): void {
    this.loading.set(true);
    this.error.set(null);

    this.postService.getPosts(this.currentPage(), this.pageSize()).subscribe({
      next: (response) => {
        this.loading.set(false);
        this.error.set(null);
        this.posts.set(response);
        this.dataSource.set(new MatTableDataSource<Post>(response.content));
        this.currentPage.set(response.pageable.pageNumber);
      },
      error: (err) => {
        this.loading.set(false);
        if(typeof err.error === 'string'){
          this.error.set(`Failed to load posts. ${err.error}`);
        }else{
          this.error.set('Failed to load posts. Try again later.');
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
        this.error.set('Failed to delete post');
      }
    });
  }
}
