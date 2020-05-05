import { Component, OnInit } from '@angular/core';
import { CampServiceService } from '../camp-service.service';
import { Camp } from '../camp';

@Component({
  selector: 'app-active-camps',
  templateUrl: './active-camps.component.html',
  styleUrls: ['./active-camps.component.css']
})
export class ActiveCampsComponent implements OnInit {
  camps: Camp[];

  constructor(private campService: CampServiceService) { }

  ngOnInit(): void {
    this.campService.findActive().subscribe(data => {
      this.camps = data;
    });
  }

}
