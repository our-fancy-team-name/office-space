import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RdfPageComponent } from './rdf-page.component';

describe('RdfPageComponent', () => {
  let component: RdfPageComponent;
  let fixture: ComponentFixture<RdfPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RdfPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RdfPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
