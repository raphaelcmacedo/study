import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Post } from '../model/Post';
import { Page } from '../model/Page';

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
  }