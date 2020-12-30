import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductEditListComponent } from './product-edit-list.component';

describe('ProductEditListComponent', () => {
  let component: ProductEditListComponent;
  let fixture: ComponentFixture<ProductEditListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProductEditListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductEditListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
