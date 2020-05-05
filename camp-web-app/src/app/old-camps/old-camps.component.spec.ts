import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OldCampsComponent } from './old-camps.component';

describe('OldCampsComponent', () => {
  let component: OldCampsComponent;
  let fixture: ComponentFixture<OldCampsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OldCampsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OldCampsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
