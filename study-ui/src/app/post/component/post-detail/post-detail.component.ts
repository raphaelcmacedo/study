import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { PostService } from '../../service/PostService';
import { PostRequest } from '../../model/PostRequest';
import { Observable } from 'rxjs';
import { Post } from '../../model/Post';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.css',
})
export class PostDetailComponent implements OnInit {
  form: FormGroup;
  id: string | null = null;
  error: string | null = null;

  constructor(
    private postService: PostService,
    private fb: FormBuilder,
    private dialogRef: MatDialogRef<PostDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string | null }
  ) {
    this.id = data.id;
    this.error = null;

    this.form = this.fb.group({
      subject: ['', Validators.required],
      text: ['', Validators.required],
    });

    this.loadPost();
  }

  ngOnInit(): void {
    
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
    const postRequest:PostRequest = this.form.value;
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
