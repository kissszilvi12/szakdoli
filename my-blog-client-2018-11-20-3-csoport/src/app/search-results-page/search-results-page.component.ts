import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Post, PostsService } from '../posts.service';
import { mergeMap } from 'rxjs/operators';

@Component({
  selector: 'app-search-results-page',
  templateUrl: './search-results-page.component.html',
  styleUrls: ['./search-results-page.component.css']
})
export class SearchResultsPageComponent implements OnInit {

  private posts: Observable<Post[]>;

  constructor(private activatedRoute: ActivatedRoute, private postsService: PostsService) { }

  ngOnInit() {
    this.posts = this.activatedRoute.paramMap
      .pipe(mergeMap(paramMap =>
        this.postsService.findAllByPredicate(function (post) {
          const keywords = paramMap.get('keywords').split(/\s/g);
          
          return keywords.some(keyword => post.title.includes(keyword) || post.text.includes(keyword));
        })));
  }

}
