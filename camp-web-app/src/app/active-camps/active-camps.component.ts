import { Component, OnInit } from '@angular/core';
import { CampService } from '../model/camp/camp.service';
import { Camp } from '../model/camp/camp';

@Component({
  selector: 'app-active-camps',
  templateUrl: './active-camps.component.html',
  styleUrls: ['./active-camps.component.css']
})
export class ActiveCampsComponent implements OnInit {
  camps: Camp[];

  constructor(private campService: CampService) { }

  ngOnInit(): void {
    this.campService.findActive().subscribe(data => {
      this.camps = data;
    });
  }

}
