import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClusterPackageListComponent } from './cluster-package-list.component';

describe('ClusterPackageListComponent', () => {
  let component: ClusterPackageListComponent;
  let fixture: ComponentFixture<ClusterPackageListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClusterPackageListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClusterPackageListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
