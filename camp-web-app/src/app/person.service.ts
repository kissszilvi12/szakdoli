import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Person } from './person';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private personUrl: string;

  constructor(private http: HttpClient) { }

  public findLeaders(): Observable<Person[]> {    
    this.personUrl = 'http://localhost:8080/camper/byPos/leader';
    return this.http.get<Person[]>(this.personUrl);
  }

  public findYoungLeaders(): Observable<Person[]> {    
    this.personUrl = 'http://localhost:8080/camper/byPos/young-leader';
    return this.http.get<Person[]>(this.personUrl);
  }

  public save(person: Person) {
    this.personUrl = 'http://localhost:8080/addcamper';
    return this.http.post<Person>(this.personUrl, person);
  }
}
