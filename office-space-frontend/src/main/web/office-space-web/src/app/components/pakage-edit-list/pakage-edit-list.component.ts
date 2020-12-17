import { trigger, state, style, transition, animate } from '@angular/animations';
import { AfterViewInit, Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { NgxSpinnerService } from 'ngx-spinner';
import { merge, Observable } from 'rxjs';
import { debounceTime, startWith, switchMap, map, catchError } from 'rxjs/operators';
import { ColumnSearchRequest, TablePagingRequest, TableSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { StorageService } from 'src/app/services/auth/storage.service';
import { PackageService } from 'src/app/services/package.service';
import { ProductService } from 'src/app/services/product.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { SelectSearchComponent } from '../select-search/select-search.component';

@Component({
  selector: 'app-pakage-edit-list',
  templateUrl: './pakage-edit-list.component.html',
  styleUrls: ['./pakage-edit-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class PakageEditListComponent implements OnInit, AfterViewInit {
  isAddingPkg = false;
  serialNumberCreCtr = new FormControl('', [
    this.validator.required()
  ]);
  serialNumberCtr = new FormControl('', [
    this.validator.required()
  ]);
  url = `${this.storage.get(StorageService.API)}product/list-name`;
  tableSearchObject: TableSearchRequest = {
    columnSearchRequests: [
      {
        columnName: 'name',
        operation: DataBaseOperation.LIKE,
        term: '',
        isOrTerm: true
      },
      {
        columnName: 'partNumber',
        operation: DataBaseOperation.LIKE,
        term: '',
        isOrTerm: true
      }
    ],
    pagingRequest: {
      page: 0,
      pageSize: 6
    },
    sortingRequest: null
  };
  descriptionCreCtr = new FormControl();
  descriptionCtr = new FormControl();
  agreement = false;
  dataSource = [];
  columnsToDisplay = ['serialNumber', 'product', 'description', 'action'];
  expandedElement = null;
  serialNumberSearchCtr = new FormControl();
  productSearchCtr = new FormControl();
  descriptionSearchCtr = new FormControl();
  resultLength = 0;
  pageSize = 0;
  agreementToDelete = false;
  isDeletePackageMode = false;
  elementToDelete = null;

  readonly TIME_OUT = 800;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('prdCre') productCre: SelectSearchComponent;
  @ViewChildren('prdEdit') productEdits: QueryList<SelectSearchComponent>;

  constructor(
    public validator: ValidatorsService,
    private storage: StorageService,
    private packageService: PackageService,
    private spinner: NgxSpinnerService,
    private ProductService: ProductService
  ) { }

  ngAfterViewInit(): void {
    this.searchTableData();
  }

  ngOnInit(): void {
  }

  addCre() {
    this.isAddingPkg = !this.isAddingPkg;
    this.agreement = false;
    this.expandedElement = null;
  }

  submitCre() {
    this.spinner.show();
    const packageDto = {
      productId: this.productCre.getValue().id,
      serialNumber: this.serialNumberCreCtr.value,
      description: this.descriptionCreCtr.value
    };

    this.packageService.create(packageDto).subscribe(res => {
      this.serialNumberCreCtr.reset();
      this.descriptionCreCtr.reset('');
      this.productCre.setValue(null);
      this.spinner.hide();
      this.paginator.page.emit();
      this.closeCre();
    }, err => {
      this.serialNumberCreCtr.setErrors(this.validator.getErrorMessage(err.error.message));
      this.spinner.hide();
    });

  }

  isSubmitCreDisable() {
    return (this.productCre?.getValue() || null) === null || !this.agreement || this.serialNumberCreCtr.value === '';
  }

  closeCre() {
    this.isAddingPkg = false;
  }

  submit(element) {
    this.spinner.show();
    const packageDto = {
      id: element.id,
      productId: this.productEdits.filter(i => i.identifier === element.id)[0].getValue().id,
      serialNumber: this.serialNumberCtr.value,
      description: this.descriptionCtr.value || ''
    }
    this.packageService.update(packageDto).subscribe(res => {
      this.spinner.hide();
      this.expandedElement = null;
      this.paginator.page.emit();
    }, err => {
      this.serialNumberCtr.setErrors(this.validator.getErrorMessage(err.error.message));
      this.spinner.hide();
    })

  }

  isSubmitDisable(element) {
    return !this.agreement || this.serialNumberCtr.value === '';
  }

  closeElement() {
    this.expandedElement = null;
  }

  expanDetails(element) {
    return element === this.expandedElement ? 'expanded' : 'collapsed';
  }

  searchTableData() {
    merge(
      this.serialNumberSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.productSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.descriptionSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.paginator.page
    ).pipe(
        startWith({}),
        switchMap(() => {
          this.spinner.show();
          const tableRequest = new TableSearchRequest();
          const pageRequest = new TablePagingRequest();
          const colRequest: ColumnSearchRequest[] = [
            {
              columnName: 'serialNumber',
              operation: DataBaseOperation.LIKE,
              term: this.serialNumberSearchCtr.value || ''
            },
            {
              columnName: 'product',
              operation: DataBaseOperation.LIKE,
              term: this.productSearchCtr.value || ''
            },
            {
              columnName: 'description',
              operation: DataBaseOperation.LIKE,
              term: this.descriptionSearchCtr.value || ''
            }
          ];
          pageRequest.page = this.paginator.pageIndex;
          pageRequest.pageSize = this.paginator.pageSize;
          tableRequest.pagingRequest = pageRequest;
          tableRequest.columnSearchRequests = colRequest;
          return this.packageService.getListView(tableRequest);
        }),
        map(data => {
          this.resultLength = data.totalElements;
          this.pageSize = data.pageSize;
          return data.content;
        }),
        catchError(() => {
          return new Observable();
        })
      ).subscribe(data => {
        this.spinner.hide();
        this.dataSource = data;
      });
  }

  isHideEditBtn(element) {
    return this.isDeletePackageMode && element === this.elementToDelete;
  }

  openElement(element) {
    this.closeCre();
    this.expandedElement = this.expandedElement === element ? null : element;
    this.agreement = false;
    if (this.expandedElement !== null) {
      this.serialNumberCtr.setValue(element.serialNumber);
      this.descriptionCtr.setValue(element.description);
      const data = JSON.parse(JSON.stringify(this.tableSearchObject));
      data.columnSearchRequests.forEach(i=> i.operation = DataBaseOperation.EQUAL);
      data.columnSearchRequests[0].term = element.product?.split(' - ')[0];
      data.columnSearchRequests[1].term = element.product?.split(' - ')[1];
      this.ProductService.getListProductDisplayName(data).subscribe(res => {
        this.productEdits.filter(i => i.identifier === element.id)[0].setValue(res.content[0]);
      })
    }
  }

  enableDeletePackage(element) {
    this.isDeletePackageMode = true;
    this.agreementToDelete = false;
    this.elementToDelete = element;
  }

  deletePackage(element) {
    this.spinner.show();
    this.packageService.delete(element.id).subscribe(res => {
      this.closeElement();
      this.spinner.hide();
      this.paginator.page.emit();
    })
  }

  isConfirmDeleteDisable(element) {
    return !this.agreementToDelete;
  }

  cancelDeletePackage(element) {
    this.elementToDelete = null;
    this.isDeletePackageMode = false;
  }
}
