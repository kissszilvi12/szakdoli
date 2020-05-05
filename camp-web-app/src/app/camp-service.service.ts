import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Camp } from 'src/app/camp';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CampServiceService {
  private campUrl: string;

  constructor(private http: HttpClient) { }

  public findActive(): Observable<Camp[]> {    
    this.campUrl = 'http://localhost:8080/activeCamps';
    return this.http.get<Camp[]>(this.campUrl);
  }

  public findInactive(): Observable<Camp[]> {    
    this.campUrl = 'http://localhost:8080/inactiveCamps';
    return this.http.get<Camp[]>(this.campUrl);
  }

  public findAll(): Observable<Camp[]> {
    this.campUrl='http://localhost:8080/camps/';
    return this.http.get<Camp[]>(this.campUrl);
  }

  public findByYear(year:number): Observable<Camp[]> {
    this.campUrl='http://localhost:8080/campByYear/'+year;
    return this.http.get<Camp[]>(this.campUrl);
  }

  public findYears(): Observable<number[]> {
    this.campUrl='http://localhost:8080/campYears';
    return this.http.get<number[]>(this.campUrl);
  }
 
  //public save(camp: Camp) {
  //  return this.http.post<Camp>(this.campssUrl, camp);
  //}
}