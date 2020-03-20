import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmPostDeletionPageComponent } from './confirm-post-deletion-page.component';

describe('ConfirmPostDeletionPageComponent', () => {
  let component: ConfirmPostDeletionPageComponent;
  let fixture: ComponentFixture<ConfirmPostDeletionPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfirmPostDeletionPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfirmPostDeletionPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
