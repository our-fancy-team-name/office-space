import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClusterEditListComponent } from './cluster-edit-list.component';

describe('ClusterEditListComponent', () => {
  let component: ClusterEditListComponent;
  let fixture: ComponentFixture<ClusterEditListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClusterEditListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClusterEditListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
