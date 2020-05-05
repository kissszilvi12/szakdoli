import { Component, OnInit } from '@angular/core';
import { PersonService } from '../person.service';
import { Person } from '../person';

@Component({
  selector: 'app-leaders',
  templateUrl: './leaders.component.html',
  styleUrls: ['./leaders.component.css']
})
export class LeadersComponent implements OnInit {
  leaders: Person[];

  constructor(private personService: PersonService) { }

  ngOnInit(): void {
    this.personService.findLeaders().subscribe(data => {
      this.leaders = data;
    });
  }
}