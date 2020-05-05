import { Injectable } from '@angular/core';
import { Parent } from './parent';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ParentService {
  private parentUrl: string;

  constructor(private http: HttpClient) { }

  public save(parent: Parent) {
    this.parentUrl = 'http://localhost:8080/addParent';
    return this.http.post<Parent>(this.parentUrl, parent);
  }
}
