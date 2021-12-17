import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { PostPayload } from './add-post/post-payload';

@Injectable({
  providedIn: 'root'
})
export class PostService {
  private url = 'http://localhost:8080/api/posts/';

  constructor(private httpClient: HttpClient) { }

  addPost(postPayload: PostPayload) {
    return this.httpClient.post(this.url, postPayload);
  }

  getAllPosts(): Observable<Array<PostPayload>> {
    return this.httpClient.get<Array<PostPayload>>(this.url + 'all');
  }

  getPosts(permaLink: Number): Observable<PostPayload> {
    return this.httpClient.get<PostPayload>(this.url + 'get/' + permaLink);
  }
}
