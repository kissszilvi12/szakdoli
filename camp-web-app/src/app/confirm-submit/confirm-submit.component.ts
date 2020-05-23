import { Component, OnInit } from '@angular/core';
import {  Router, ActivatedRoute } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { Person } from '../model/person/person';

@Component({
  selector: 'app-confirm-submit',
  templateUrl: './confirm-submit.component.html',
  styleUrls: ['./confirm-submit.component.css']
})
export class ConfirmSubmitComponent implements OnInit {
  private post: Observable<Person>;
  private postSubscription: Subscription;

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  confirm() {
    this.router.navigateByUrl('/');
  }
}
