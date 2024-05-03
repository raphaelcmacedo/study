import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Post } from '../model/Post';
import { Page } from '../model/Page';
import { PostRequest } from '../model/PostRequest';

@Injectable({
    providedIn: 'root'
  })

  export class PostService{
    private apiUrl = `${environment.apiUrl}/post`;

    constructor(private http: HttpClient) {}

    getPosts(page: number = 0, size: number = 10): Observable<Page<Post>> {
      const url = `${this.apiUrl}?page=${page}&size=${size}`;
      return this.http.get<Page<Post>>(url);
    }

    getPost(id: number | string):Observable<Post>{
      const url = `${this.apiUrl}/${id}`;
      return this.http.get<Post>(url);
    }

    addPost(postRequest: PostRequest):Observable<Post>{
      const url = `${this.apiUrl}`;
      return this.http.post<Post>(url, postRequest);
    }

    updatePost(postRequest: PostRequest, id: number | string):Observable<Post>{
      const url = `${this.apiUrl}/${id}`;
      return this.http.put<Post>(url, postRequest);
    }

    deletePost(id: number):Observable<void>{
      const url = `${this.apiUrl}/${id}`;
      return this.http.delete<void>(url);
    }
  }