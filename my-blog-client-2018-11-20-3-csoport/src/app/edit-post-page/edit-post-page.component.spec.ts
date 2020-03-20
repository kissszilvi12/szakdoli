import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPostPageComponent } from './edit-post-page.component';

describe('EditPostPageComponent', () => {
  let component: EditPostPageComponent;
  let fixture: ComponentFixture<EditPostPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditPostPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditPostPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
