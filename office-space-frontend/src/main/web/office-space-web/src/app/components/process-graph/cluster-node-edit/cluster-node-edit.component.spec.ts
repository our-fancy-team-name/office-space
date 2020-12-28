import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClusterNodeEditComponent } from './cluster-node-edit.component';

describe('ClusterNodeEditComponent', () => {
  let component: ClusterNodeEditComponent;
  let fixture: ComponentFixture<ClusterNodeEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClusterNodeEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClusterNodeEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
