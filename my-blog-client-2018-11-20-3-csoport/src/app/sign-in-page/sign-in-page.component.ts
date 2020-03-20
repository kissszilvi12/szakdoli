import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

type UserCredentials = {
  username: string,
  password: string
};

@Component({
  selector: 'app-sign-in-page',
  templateUrl: './sign-in-page.component.html',
  styleUrls: ['./sign-in-page.component.css']
})
export class SignInPageComponent implements OnInit {

  private userCredentials: UserCredentials;

  constructor(private userService: UserService) {
    this.userCredentials = { username: '', password: '' };
  }

  ngOnInit() {
  }

  handleSubmit(form) {
    if (form.valid) {
      this.userService.setCurrentUser({ firstName: 'Sandor' });
    }
  }
}
