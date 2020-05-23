import { Component, OnInit } from '@angular/core';
import { CampService } from '../model/camp/camp.service';
import { Camp } from '../model/camp/camp';
import { Person } from '../model/person/person';
import { PersonService } from '../model/person/person.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Parent } from '../model/parent/parent';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  camps: Camp[];
  parent: Parent;
  person: Person;

  constructor(private route: ActivatedRoute, private router: Router, private campService: CampService, private personService: PersonService) {
      this.camps=[];
      this.parent = new Parent();
      this.person = new Person();
      this.person.camps =[];
   }

  onSubmit() {
    this.personService.save(this.person, this.parent).subscribe(result => this.gotoConfirm());
  }
 
  gotoConfirm() {
    this.router.navigate(['/elkuldve']);
  }

  ngOnInit(): void {
    this.campService.findActive().subscribe(data => {
      this.camps = data;
    });
  }

}
