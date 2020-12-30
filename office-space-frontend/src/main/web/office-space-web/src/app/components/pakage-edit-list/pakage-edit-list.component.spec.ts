import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PakageEditListComponent } from './pakage-edit-list.component';

describe('PakageEditListComponent', () => {
  let component: PakageEditListComponent;
  let fixture: ComponentFixture<PakageEditListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PakageEditListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PakageEditListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
