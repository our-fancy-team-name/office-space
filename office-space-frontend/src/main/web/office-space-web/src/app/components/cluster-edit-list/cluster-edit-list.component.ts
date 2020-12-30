import { trigger, state, style, transition, animate } from '@angular/animations';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { merge, Observable } from 'rxjs';
import { debounceTime, startWith, switchMap, map, catchError } from 'rxjs/operators';
import { TableSearchRequest, TablePagingRequest, ColumnSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { ClusterService } from 'src/app/services/cluster.service';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-cluster-edit-list',
  templateUrl: './cluster-edit-list.component.html',
  styleUrls: ['./cluster-edit-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class ClusterEditListComponent implements OnInit, AfterViewInit {
  TIME_OUT = 800;
  codeCreCtr = new FormControl('', [
    this.validator.required(),
    this.validator.maxLength(5),
    this.validator.minLength(5)
  ]);
  codeCtr = new FormControl('', [
    this.validator.required(),
    this.validator.minLength(5),
    this.validator.maxLength(5)
  ]);
  descriptionCreCtr = new FormControl('');
  descriptionCtr = new FormControl('');

  nameCreCtr = new FormControl('');
  nameCtr = new FormControl('');

  agreement = false;
  dataSource = [];
  codeSearchCtr = new FormControl('');
  nameSearchCtr = new FormControl('');
  descriptionSearchCtr = new FormControl('');
  columnsToDisplay = ['code', 'name', 'description', 'action'];
  expandedElement = null;
  resultLength = 0;
  pageSize = 0;
  isAddingCls = false;
  isDeleteClusterMode = false;
  elementToDelete = null;
  agreementToDelete = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    public validator: ValidatorsService,
    private clusterService: ClusterService,
    private spinner: NgxSpinnerService,
    private snackBar: MatSnackBar
  ) { }

  ngAfterViewInit(): void {
    this.searchTableData();
  }

  ngOnInit(): void {
  }

  addCre() {
    this.isAddingCls = !this.isAddingCls;
    this.agreement = false;
  }

  submitCre() {
    this.spinner.show();
    const processGeneralDto = {
      code: this.codeCreCtr.value,
      name: this.nameCreCtr.value,
      description: this.descriptionCreCtr.value
    };
    this.clusterService.create(processGeneralDto).subscribe(res => {
      this.spinner.hide();
      this.isAddingCls = false;
      this.codeCreCtr.reset();
      this.nameCreCtr.reset();
      this.descriptionCreCtr.reset();
      this.paginator.page.emit();
    }, err => {
      this.spinner.hide();
      this.codeCreCtr.setErrors(this.validator.getErrorMessage(err.error.message));
    });
  }

  isSubmitCreDisable() {
    return !this.agreement || this.codeCreCtr.value === '' || this.validator.isValid(this.codeCreCtr);
  }

  closeCre() {
    this.isAddingCls = false;
  }

  expanDetails(element) {
    return element === this.expandedElement ? 'expanded' : 'collapsed';
  }

  searchTableData() {
    merge(
      this.codeSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.nameSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
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
              columnName: 'code',
              operation: DataBaseOperation.LIKE,
              term: this.codeSearchCtr.value || ''
            },
            {
              columnName: 'name',
              operation: DataBaseOperation.LIKE,
              term: this.nameSearchCtr.value || ''
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
          return this.clusterService.getListView(tableRequest);
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

  isHideEditBtn(element: any) {
    return this.isDeleteClusterMode && element === this.elementToDelete;
  }

  openElement(element: { code: any; name: any; description: any; }) {
    this.closeCre();
    this.expandedElement = this.expandedElement === element ? null : element;
    this.agreement = false;
    this.codeCtr.setValue(element.code);
    this.nameCtr.setValue(element.name);
    this.descriptionCtr.setValue(element.description);
  }

  enableDeleteCluster(element) {
    this.isDeleteClusterMode = true;
    this.elementToDelete = element;
  }

  deleteCluster(element: any) {

  }

  isConfirmDeleteDisable(element: any) {
    return !this.agreementToDelete;
  }

  cancelDeleteCluster(element) {
    this.agreementToDelete = false;
    this.elementToDelete = null;
  }

  submit(element) {
    this.spinner.show();
    const data = {
      id: element.id,
      code: this.codeCtr.value,
      name: this.nameCtr.value,
      description: this.descriptionCtr.value
    };
    this.clusterService.update(data).subscribe(res => {
      this.spinner.hide();
      this.paginator.page.emit();
      this.close();
    }, err => {
      this.codeCtr.setErrors(this.validator.getErrorMessage(err.error.message));
      this.spinner.hide();
    });
  }

  isSubmitDisable() {
    return !this.agreement || this.codeCtr.value === '' || this.validator.isValid(this.codeCtr);
  }

  close() {
    this.expandedElement = null;
  }
}
