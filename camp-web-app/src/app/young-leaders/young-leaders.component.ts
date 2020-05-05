import { Component, OnInit } from '@angular/core';
import { PersonService } from '../person.service';
import { Person } from '../person';

@Component({
  selector: 'app-young-leaders',
  templateUrl: './young-leaders.component.html',
  styleUrls: ['./young-leaders.component.css']
})
export class YoungLeadersComponent implements OnInit {
  youngLeaders: Person[];

  constructor(private personService: PersonService) { }

  ngOnInit(): void {
    this.personService.findYoungLeaders().subscribe(data => {
      this.youngLeaders = data;
    });
  }

}
