import { Component, OnInit, Inject, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PostService } from '../../service/PostService';
import { PostRequest } from '../../model/PostRequest';
import { Observable } from 'rxjs';
import { Post } from '../../model/Post';
import { MatButton } from '@angular/material/button';
import { NgIf } from '@angular/common';
import { MatInput } from '@angular/material/input';
import { MatFormField, MatLabel, MatError } from '@angular/material/form-field';

@Component({
    selector: 'app-post-detail',
    templateUrl: './post-detail.component.html',
    styleUrl: './post-detail.component.css',
    standalone: true,
    imports: [
        FormsModule,
        ReactiveFormsModule,
        MatFormField,
        MatLabel,
        MatInput,
        NgIf,
        MatError,
        MatButton,
    ],
})
export class PostDetailComponent implements OnInit {
  id: string | null = null;
  error: string | null = null;

  postService = inject(PostService);
  fb = inject(FormBuilder);
  dialogRef = inject(MatDialogRef<PostDetailComponent>);

  form = this.fb.nonNullable.group({
    subject: ['', Validators.required],
    text: ['', Validators.required],
  });

  constructor(@Inject(MAT_DIALOG_DATA) public data: { id: string | null }) {
    this.id = data.id;
  }

  ngOnInit(): void {
    this.error = null;
    this.loadPost();
  }

  private loadPost(){
    if(this.id){
      this.postService.getPost(this.id).subscribe({
        next:(response) =>{
          this.error = null;
          this.form.patchValue({
            subject: response.subject,
            text: response.text
          });  
        },
        error:(err) =>{
          this.error = `Error while loading the post. ${err.error}`;
        }
      });
    }
  }

  onSubmit() {
    const postRequest:PostRequest = this.form.getRawValue();
    let result:Observable<Post>;
    if(this.id){//Update
      result = this.postService.updatePost(postRequest, this.id);
    }else{//Create
      result = this.postService.addPost(postRequest);
    }

    result.subscribe({
      next:(response) =>{
        this.error = null;
        this.dialogRef.close(response);
      },
      error:(err)=> {
        this.error = `Error while saving the post. ${err.error}`;
      }
    });
  }

  onClose() {
    this.dialogRef.close();
  }
}
