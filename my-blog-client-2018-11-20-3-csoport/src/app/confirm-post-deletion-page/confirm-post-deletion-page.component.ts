import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PostsService, Post } from '../posts.service';
import { Observable, Subscription } from 'rxjs';
import { mergeMap, map } from 'rxjs/operators';

@Component({
  selector: 'app-confirm-post-deletion-page',
  templateUrl: './confirm-post-deletion-page.component.html',
  styleUrls: ['./confirm-post-deletion-page.component.css']
})
export class ConfirmPostDeletionPageComponent implements OnInit, OnDestroy {

  private post: Observable<Post>;
  private postSubscription: Subscription;

  constructor(private activatedRoute: ActivatedRoute, private postsService: PostsService, private router: Router) { }

  ngOnInit() {
    this.post = this.activatedRoute.paramMap
      .pipe(mergeMap(paramMap =>
        this.postsService.findByPostNo(paramMap.get('postNo'))
          .pipe(map(posts =>
            posts.reduce((defaultPost, post) =>
              post || defaultPost, null)))));

    this.postSubscription = this.post.subscribe(post => {
      if (!post) {
        this.router.navigateByUrl('/error/404');
      }
    });
  }

  ngOnDestroy() {
    this.postSubscription.unsubscribe();
  }

  confirm(postNo) {
    this.postsService.deleteByPostNo(postNo);
    this.router.navigateByUrl('/');
  }
}
