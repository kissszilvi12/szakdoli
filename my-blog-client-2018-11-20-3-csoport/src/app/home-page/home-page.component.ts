import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { PostsService } from '../posts.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  private posts: Observable<any>;

  constructor(private postsService: PostsService) { }

  ngOnInit() {
    this.posts = this.postsService.findAll();
  }

}
