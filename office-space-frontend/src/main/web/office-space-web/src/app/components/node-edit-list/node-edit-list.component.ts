import { animate, state, style, transition, trigger } from '@angular/animations';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { merge, Observable } from 'rxjs';
import { catchError, debounceTime, map, startWith, switchMap } from 'rxjs/operators';
import { ColumnSearchRequest, TablePagingRequest, TableSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { NodeService } from 'src/app/services/node.service';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-node-edit-list',
  templateUrl: './node-edit-list.component.html',
  styleUrls: ['./node-edit-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class NodeEditListComponent implements OnInit, AfterViewInit {
  TIME_OUT = 800;
  isAddingNode = false;
  agreement = false;
  dataSource = [];
  agreementToDelete = false;
  codeCreCtr = new FormControl('', [
    this.validator.required(),
    this.validator.maxLength(5),
    this.validator.minLength(5)
  ]);
  nameCreCtr = new FormControl('');
  descriptionCreCtr = new FormControl('');
  nameCtr = new FormControl('');
  descriptionCtr = new FormControl('');
  codeSearchCtr = new FormControl('');
  nameSearchCtr = new FormControl('');
  descriptionSearchCtr = new FormControl('');
  expandedElement = null;
  columnsToDisplay = ['code', 'name', 'description', 'action'];
  isDeleteNodeMode = false;
  elementToDelete = null;
  codeCtr =  new FormControl('', [
    this.validator.required(),
    this.validator.maxLength(5),
    this.validator.minLength(5)
  ]);
  @ViewChild(MatPaginator) paginator: MatPaginator;
  resultLength = 0;
  pageSize = 0;

  constructor(
    public validator: ValidatorsService,
    private nodeService: NodeService,
    private spinner: NgxSpinnerService,
    private snackBar: MatSnackBar
  ) { }

  ngAfterViewInit(): void {
    this.searchTableData();
  }

  ngOnInit(): void {
  }

  addCre() {
    this.isAddingNode = !this.isAddingNode;
    this.agreement = false;
  }

  submitCre() {
    this.spinner.show();
    const data = {
      code: this.codeCreCtr.value,
      name: this.nameCreCtr.value,
      description: this.descriptionCreCtr.value
    };
    this.nodeService.create(data).subscribe(res => {
      this.spinner.hide();
      this.codeCreCtr.reset();
      this.nameCreCtr.reset();
      this.descriptionCreCtr.reset();
      this.closeCre();
      this.paginator.page.emit();
    }, err => {
      this.codeCreCtr.setErrors(this.validator.getErrorMessage(err.error.message));
      this.spinner.hide();
    });

  }

  isSubmitCreDisable() {
    return !this.agreement || this.codeCreCtr.value === '' || this.validator.isValid(this.codeCreCtr);
  }

  closeCre() {
    this.isAddingNode = false;
  }

  expanDetails(element) {
    return element === this.expandedElement ? 'expanded' : 'collapsed';
  }

  isHideEditBtn(element: any) {
    return this.isDeleteNodeMode && element === this.elementToDelete;
  }

  openElement(element) {
    this.closeCre();
    this.expandedElement = this.expandedElement === element ? null : element;
    this.agreement = false;
    this.codeCtr.setValue(element.code);
    this.nameCtr.setValue(element.name);
    this.descriptionCtr.setValue(element.description);
  }

  enableDeleteNode(element) {
    this.isDeleteNodeMode = true;
    this.elementToDelete = element;
  }

  deleteNode(element) {

  }

  isConfirmDeleteDisable(element) {
    return !this.agreementToDelete;
  }

  cancelDeleteNode(element) {
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
    this.nodeService.update(data).subscribe(res => {
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
          return this.nodeService.getListView(tableRequest);
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

}
