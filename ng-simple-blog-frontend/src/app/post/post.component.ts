import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PostService } from '../post.service';
import { PostPayload } from '../add-post/post-payload';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {

  post!: PostPayload;
  permaLink!: Number;

  constructor(private router: ActivatedRoute, private postService: PostService) { }

  ngOnInit(): void {
    this.router.params.subscribe(params => {
      this.permaLink = params['id'];
    });

    this.postService.getPosts(this.permaLink).subscribe((data: PostPayload) => {
      this.post = data;
    }, (err: any) => {
      console.log('Failure Response');
    })
  }

}
