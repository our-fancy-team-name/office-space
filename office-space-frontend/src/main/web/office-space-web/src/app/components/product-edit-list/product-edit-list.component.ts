import { trigger, state, style, transition, animate } from '@angular/animations';
import { AfterViewInit, Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { merge, Observable } from 'rxjs';
import { debounceTime, startWith, switchMap, map, catchError } from 'rxjs/operators';
import { TableSearchRequest, TablePagingRequest, ColumnSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { StorageService } from 'src/app/services/auth/storage.service';
import { ClusterService } from 'src/app/services/cluster.service';
import { ProductService } from 'src/app/services/product.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { SelectSearchComponent } from '../select-search/select-search.component';

@Component({
  selector: 'app-product-edit-list',
  templateUrl: './product-edit-list.component.html',
  styleUrls: ['./product-edit-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ProductEditListComponent implements OnInit, AfterViewInit {

  readonly TIME_OUT = 800;

  isAddingPrd = false;
  nameCreCtr = new FormControl('', [
    this.validator.required(),
  ]);
  nameCtr = new FormControl('', [
    this.validator.required(),
  ]);
  partNumberCreCtr = new FormControl('', [
    this.validator.required()
  ]);
  partNumberCtr = new FormControl('', [
    this.validator.required()
  ]);
  descriptionCreCtr = new FormControl('');
  familyCreCtr = new FormControl('');
  descriptionCtr = new FormControl('');
  familyCtr = new FormControl('');
  agreement = false;
  nameSearchCtr = new FormControl('');
  partNumberSearchCtr = new FormControl('');
  descriptionSearchCtr = new FormControl('');
  familySearchCtr = new FormControl('');
  expandedElement = null;
  resultLength = 0;
  pageSize = 0;
  url = `${this.storage.get(StorageService.API)}cluster/list-view`;
  tableSearchObject: TableSearchRequest = {
    columnSearchRequests: [
      {
        columnName: 'code',
        operation: DataBaseOperation.LIKE,
        term: '',
        isOrTerm: true
      },
      {
        columnName: 'name',
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
  tableSearchObjectId: TableSearchRequest = {
    columnSearchRequests: [
      {
        columnName: 'id',
        operation: DataBaseOperation.EQUAL,
        term: '',
        isOrTerm: false
      }
    ],
    pagingRequest: {
      page: 0,
      pageSize: 1
    },
    sortingRequest: null
  };

  columnsToDisplay = ['name', 'partNumber', 'description', 'family', 'action'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('clusterSelect') clusterSelect: SelectSearchComponent;
  @ViewChildren('clusterEdit') clusterEdits: QueryList<SelectSearchComponent>;

  dataSource = [];
  agreementToDelete = false;
  isDeleteMode = false;
  elementToDelete = null;

  constructor(
    private storage: StorageService,
    public validator: ValidatorsService,
    private spinner: NgxSpinnerService,
    private productService: ProductService,
    private snackBar: MatSnackBar,
    private translate: TranslateService,
    private clusterService: ClusterService
  ) { }

  ngAfterViewInit(): void {
    this.searchTableData();
  }

  searchTableData() {
    merge(
      this.nameSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.partNumberSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.descriptionSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.familySearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.paginator.page
    ).pipe(
        startWith({}),
        switchMap(() => {
          this.spinner.show();
          const tableRequest = new TableSearchRequest();
          const pageRequest = new TablePagingRequest();
          const colRequest: ColumnSearchRequest[] = [
            {
              columnName: 'name',
              operation: DataBaseOperation.LIKE,
              term: this.nameSearchCtr.value || ''
            },
            {
              columnName: 'partNumber',
              operation: DataBaseOperation.LIKE,
              term: this.partNumberSearchCtr.value || ''
            },
            {
              columnName: 'description',
              operation: DataBaseOperation.LIKE,
              term: this.descriptionSearchCtr.value || ''
            },
            {
              columnName: 'family',
              operation: DataBaseOperation.LIKE,
              term: this.familySearchCtr.value || ''
            }
          ];
          pageRequest.page = this.paginator.pageIndex;
          pageRequest.pageSize = this.paginator.pageSize;
          tableRequest.pagingRequest = pageRequest;
          tableRequest.columnSearchRequests = colRequest;
          return this.productService.findAll(tableRequest);
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
        this.dataSource = data;
        this.spinner.hide();
      });
  }

  ngOnInit(): void {
  }

  addCre() {
    this.isAddingPrd = !this.isAddingPrd;
    this.agreement = false;
    this.expandedElement = null;
  }

  submitCre() {
    const productDto = {
      partNumber: this.partNumberCreCtr.value,
      name: this.nameCreCtr.value,
      description: this.descriptionCreCtr.value,
      family: this.familyCreCtr.value,
      clusterId: this.clusterSelect.getValue()?.id
    };
    this.spinner.show();
    this.productService.create(productDto).subscribe(res => {
      this.partNumberCreCtr.reset();
      this.nameCreCtr.reset();
      this.descriptionCreCtr.reset();
      this.familyCreCtr.reset();
      this.paginator.page.emit();
      this.closeCre();
      this.spinner.hide();
    }, err => {
      const objectError = err.error.message.split(':')[0];
      const message = this.validator.getErrorMessage(err.error.message.split(':')[1]);
      if (objectError.includes('NAME')) {
        this.nameCreCtr.setErrors(message);
      } else {
        this.partNumberCreCtr.setErrors(message);
      }
      this.spinner.hide();
    });
  }

  isSubmitCreDisable() {
    return this.agreement === false || this.nameCreCtr.value === '' || this.partNumberCreCtr.value === '';
  }

  closeCre() {
    this.isAddingPrd = false;
    this.expandedElement = null;
  }

  expanDetails(element) {
    return element === this.expandedElement ? 'expanded' : 'collapsed';
  }

  isHideEditBtn(element) {
    return this.isDeleteMode && element === this.elementToDelete;
  }

  openElement(element) {
    this.agreement = false;
    this.isAddingPrd = false;
    this.expandedElement = this.expandedElement === element ? null : element;
    this.nameCtr.setValue(element.name);
    this.partNumberCtr.setValue(element.partNumber);
    this.descriptionCtr.setValue(element.description);
    this.familyCtr.setValue(element.family);
    if (element.clusterId == null) {
      return;
    }
    const data = JSON.parse(JSON.stringify(this.tableSearchObjectId));
    data.columnSearchRequests[0].term = element.clusterId;
    this.clusterService.getListView(data).subscribe(res => {
      this.clusterEdits.filter(i => i.identifier === element.id)[0].setValue(res.content[0]);
    });
  }

  enableDeletePrd(element) {
    this.elementToDelete = element;
    this.isDeleteMode = true;
    this.agreementToDelete = false;
  }

  deletePrd(element) {
    this.spinner.show();
    this.productService.delete(element.id).subscribe(res => {
      this.paginator.page.emit();
    }, err => {
      this.spinner.hide();
      this.agreementToDelete = false;
      this.translate.get(this.validator.getErrorMessage(err.error.message).message).subscribe(mes => {
        this.snackBar.open(mes, '', {
          duration: 5000
        });
      });
    });
  }

  isConfirmDeleteDisable(element) {
    return this.agreementToDelete === false;
  }

  cancelDeletePrd(element) {
    this.elementToDelete = null;
    this.isDeleteMode = false;
  }

  submit(element) {
    const productDto = {
      id: element.id,
      partNumber: this.partNumberCtr.value,
      name: this.nameCtr.value,
      description: this.descriptionCtr.value,
      family: this.familyCtr.value,
      clusterId: this.clusterEdits.filter(i => i.identifier === element.id)[0].getValue().id
    };
    this.spinner.show();
    this.productService.update(productDto).subscribe(res => {
      this.close();
      this.spinner.hide();
      this.paginator.page.emit();
    }, err => {
      const objectError = err.error.message.split(':')[0];
      const message = this.validator.getErrorMessage(err.error.message.split(':')[1]);
      if (objectError.includes('NAME')) {
        this.nameCtr.setErrors(message);
      } else {
        this.partNumberCtr.setErrors(message);
      }
      this.spinner.hide();
    });
  }

  isSubmitDisable() {
    return this.agreement === false || this.nameCtr.value === '' || this.partNumberCtr.value === '';
  }

  close() {
    this.expandedElement = null;
  }
}
