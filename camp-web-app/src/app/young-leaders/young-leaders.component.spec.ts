import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { YoungLeadersComponent } from './young-leaders.component';

describe('YoungLeadersComponent', () => {
  let component: YoungLeadersComponent;
  let fixture: ComponentFixture<YoungLeadersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ YoungLeadersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(YoungLeadersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
