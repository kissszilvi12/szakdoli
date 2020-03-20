import { Injectable } from '@angular/core';
import { of, Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

export type Post = {
  postNo: number,
  title: string,
  text: string,
  tags: string[]
};

@Injectable({
  providedIn: 'root'
})
export class PostsService {

  private fakePosts: Post[];
  private posts: BehaviorSubject<Post[]>;

  constructor() {
    this.fakePosts = [
      {
        postNo: 1,
        title: 'Test title',
        text: 'Test text.',
        tags: ['elte', 'asd']
      },
      {
        postNo: 2,
        title: 'Test title #2',
        text: 'Test text.',
        tags: ['hz']
      }
    ];
    this.posts = new BehaviorSubject(this.fakePosts);
  }

  findAll(): Observable<Post[]> {
    return this.posts.asObservable();
  }

  findAllByPredicate(predicate): Observable<Post[]> {
    return this.findAll().pipe(map(x => x.filter(predicate)));
  }

  findByPostNo(postNo) {
    return this.findAllByPredicate(post => post.postNo === +postNo);
  }

  deleteByPostNo(postNo) {
    this.posts.next(this.fakePosts.filter(post => post.postNo !== postNo));
  }
}
