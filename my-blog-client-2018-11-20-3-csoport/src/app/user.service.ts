import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

type User = {
  firstName: string
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private currentUser: BehaviorSubject<User>;

  constructor() {
    this.currentUser = new BehaviorSubject(null);
  }

  setCurrentUser(user: User) {
    this.currentUser.next(user);
  }

  signOut() {
    this.setCurrentUser(null);
  }

  getCurrentUser() {
    return this.currentUser.asObservable();
  }
}
