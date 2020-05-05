import { Component, OnInit } from '@angular/core';
import { CampServiceService } from '../camp-service.service';
import { Camp } from '../camp';
import { Person } from '../person';
import { PersonService } from '../person.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Parent } from '../parent';
import { ParentService } from '../parent.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  camps: Camp[];
  person: Person;
  parent: Parent

  constructor(private route: ActivatedRoute, private router: Router,private personService: PersonService, private parentService: ParentService, private campService: CampServiceService) {
      this.person = new Person();
      this.parent = new Parent();
   }

  onSubmit() {
    this.parentService.save(this.parent).subscribe(result => this.gotoHome());
    this.personService.save(this.person).subscribe(result => this.gotoHome());
  }
 
  gotoHome() {
    this.router.navigate(['/']);
  }

  ngOnInit(): void {
    this.campService.findActive().subscribe(data => {
      this.camps = data;
    });
  }

}
