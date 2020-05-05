import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveCampsComponent } from './active-camps.component';

describe('ActiveCampsComponent', () => {
  let component: ActiveCampsComponent;
  let fixture: ComponentFixture<ActiveCampsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActiveCampsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiveCampsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
