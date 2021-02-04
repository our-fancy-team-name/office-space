import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RdfEditListComponent } from './rdf-edit-list.component';

describe('RdfEditListComponent', () => {
  let component: RdfEditListComponent;
  let fixture: ComponentFixture<RdfEditListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RdfEditListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RdfEditListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
