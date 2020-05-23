import { Injectable, SystemJsNgModuleLoader } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Person } from './person';
import { Parent } from '../parent/parent';
import { Camp } from '../camp/camp';

@Injectable({
  providedIn: 'root'
})
export class PersonService {
  private personUrl: string;

  constructor(private http: HttpClient) { }

  public findLeaders(): Observable<Person[]> {    
    this.personUrl = 'http://localhost:8080/camper/byPos/VEZETŐ';
    return this.http.get<Person[]>(this.personUrl);
  }

  public findYoungLeaders(): Observable<Person[]> {    
    this.personUrl = 'http://localhost:8080/camper/byPos/IFI-VEZETŐ';
    return this.http.get<Person[]>(this.personUrl);
  }

  public save(person: Person, parent: Parent) {
    this.personUrl = 'http://localhost:8080/addcreatedcamper';
    var body: any = {parent, person} //, camps}
    console.log(body)
    return this.http.post<any>(this.personUrl, body);
  }
}
//queryParams: { person: 'person', parent: 'parent', camp: 'camps'}
