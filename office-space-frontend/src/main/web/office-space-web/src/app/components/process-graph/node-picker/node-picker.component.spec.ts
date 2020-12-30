import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NodePickerComponent } from './node-picker.component';

describe('NodePickerComponent', () => {
  let component: NodePickerComponent;
  let fixture: ComponentFixture<NodePickerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NodePickerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NodePickerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
