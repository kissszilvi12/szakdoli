import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { PostsService, Post } from '../posts.service';
import { Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

@Component({
  selector: 'app-post-list-by-tag',
  templateUrl: './post-list-by-tag.component.html',
  styleUrls: ['./post-list-by-tag.component.css']
})
export class PostListByTagComponent implements OnInit {

  private posts: Observable<Post[]>;

  constructor(private activatedRoute: ActivatedRoute, private postsService: PostsService) { }

  ngOnInit() {
    this.posts = this.activatedRoute.paramMap
      .pipe(mergeMap(paramMap =>
        this.postsService.findAllByPredicate(function (post) {
          return post.tags.includes(paramMap.get('tag'));
        })));
  }
}
