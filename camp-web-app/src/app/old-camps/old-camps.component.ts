import { Component, OnInit } from '@angular/core';
import { Camp } from '../model/camp/camp';
import { CampService } from '../model/camp/camp.service';

@Component({
  selector: 'app-old-camps',
  templateUrl: './old-camps.component.html',
  styleUrls: ['./old-camps.component.css']
})
export class OldCampsComponent implements OnInit {
  camps: Camp[];
  years: number[];

  constructor(private campService: CampService) { }

  ngOnInit(): void {
    this.campService.findYears().subscribe(data => {
      this.years = data;
    });

    this.campService.findInactive().subscribe(data => {
      this.camps = data;
    });
  }

}
