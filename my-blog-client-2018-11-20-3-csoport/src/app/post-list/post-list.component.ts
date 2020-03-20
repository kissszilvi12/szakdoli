import { Component, Input } from '@angular/core';
import { Observable } from 'rxjs';

import { Post } from '../posts.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.css']
})
export class PostListComponent {

  @Input() private posts: Observable<Post[]>;
}
