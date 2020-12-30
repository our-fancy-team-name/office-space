import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RoleEditListComponent } from './role-edit-list.component';

describe('RoleEditListComponent', () => {
  let component: RoleEditListComponent;
  let fixture: ComponentFixture<RoleEditListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RoleEditListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RoleEditListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
