import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NodeEditListComponent } from './node-edit-list.component';

describe('NodeEditListComponent', () => {
  let component: NodeEditListComponent;
  let fixture: ComponentFixture<NodeEditListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NodeEditListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NodeEditListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
