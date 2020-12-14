import { animate, state, style, transition, trigger } from '@angular/animations';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatPaginator } from '@angular/material/paginator';
import { NgxSpinnerService } from 'ngx-spinner';
import { merge, Observable } from 'rxjs';
import { catchError, map, startWith, switchMap, debounceTime } from 'rxjs/operators';
import { ColumnSearchRequest, TablePagingRequest, TableSearchRequest } from 'src/app/dtos/tableSearch';
import { PERMISSION_CODE } from 'src/app/enums/permissionCode';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { PermissionService } from 'src/app/services/permission.service';
import { RoleService } from 'src/app/services/role.service';
import { UserService } from 'src/app/services/user.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { ChipsComponent } from '../chips/chips.component';

@Component({
  selector: 'app-role-edit-list',
  templateUrl: './role-edit-list.component.html',
  styleUrls: ['./role-edit-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class RoleEditListComponent implements OnInit, AfterViewInit {
  readonly TIME_OUT = 800;

  // permission------------------------------------------
  permissionObjects = [];
  checked = true;
  // mat chip ----------------------------------------------------
  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  chipCtrl = new FormControl();
  filteredChips: Observable<string[]>;
  chips: string[];
  allChips: string[];
  @ViewChild('chipInput') chipInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  dataSource: [] = [];
  columnsToDisplay = ['code', 'description', 'users', 'action'];
  expandedElement = null;
  agreement = false;
  codeCtr = new FormControl('', [
    this.validator.required()
  ]);
  codeCreCtr = new FormControl('', [
    this.validator.required()
  ]);
  descriptionCreCtr = new FormControl();
  codeSearchCtr = new FormControl();
  descriptionSearchCtr = new FormControl();
  userSearchCtr = new FormControl();
  descriptionCtr = new FormControl('');
  resultLength = 0;
  pageSize = 0;
  isAddingRole = false;
  isDeleteRoleMode = false;
  elementToDelete;
  agreementToDelete = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild('chipCreate') chipCre: ChipsComponent;

  constructor(
    private userService: UserService,
    public validator: ValidatorsService,
    private roleService: RoleService,
    private spinner: NgxSpinnerService,
    private permissionService: PermissionService
  ) {
    Object.entries(PERMISSION_CODE).forEach(
      ([key, value]) => this.permissionObjects.push({
            code: value,
            check: false
          })
    );
  }
  ngAfterViewInit(): void {
    this.initialData();
    this.searchTableData();
  }

  ngOnInit(): void {
  }

  initialData() {
    this.spinner.show();
    this.codeSearchCtr.setValue(this.codeSearchCtr.value);
    this.userService.getAllUsers().subscribe((res: any) => {
      this.allChips = res.content.map(item => item.username);
      this.filteredChips = this.chipCtrl.valueChanges.pipe(
          startWith(''),
          map((chip: string | '') => chip ? this._filter(chip) : this.allChips.slice()));
      this.spinner.hide();
    });
  }

  searchTableData() {
    merge(
      this.codeSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.descriptionSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.userSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
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
              columnName: 'description',
              operation: DataBaseOperation.LIKE,
              term: this.descriptionSearchCtr.value || ''
            },
            {
              columnName: 'users',
              operation: DataBaseOperation.LIKE,
              term: this.userSearchCtr.value || ''
            }
          ];
          pageRequest.page = this.paginator.pageIndex;
          pageRequest.pageSize = this.paginator.pageSize;
          tableRequest.pagingRequest = pageRequest;
          tableRequest.columnSearchRequests = colRequest;
          return this.roleService.getRoleUserListView(tableRequest);
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

  openElement(element) {
    this.closeCre();
    this.chips = element.users?.split(',') || [];
    this.permissionService.findAllPermissionByRole(element.code).subscribe(res => {
      const perm = res.map((i: any) => i.code);
      this.permissionObjects.forEach(item => {
        item.check = perm.indexOf(item.code) >= 0;
      });
      this.codeCtr.setValue(element.code);
      this.descriptionCtr.setValue(element.description);
      this.expandedElement = this.expandedElement?.code === element?.code ? null : element;
      this.agreement = false;
    });
  }

  closeElement() {
    this.expandedElement = null;
    this.agreement = false;
  }

  submit(element) {
    const permissionDto = this.permissionObjects.filter(i => i.check).map(i => i.code);
    const users = this.chips;
    const roleUserUpdateDto = {
      roleDto: {
        id: element.id,
        authority: this.codeCtr.value,
        description: this.descriptionCtr.value,
        isUsing: false
      },
      permissionDto,
      users
    };
    this.spinner.show();
    this.roleService.updateRoleUser(roleUserUpdateDto).subscribe(res => {
      this.closeElement();
      this.initialData();
    }, err => {
      this.codeCtr.setErrors(this.validator.getErrorMessage(err));
      this.spinner.hide();
    });
  }

  expanDetails(element) {
    return element?.code === this.expandedElement?.code ? 'expanded' : 'collapsed';
  }

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if (this.allChips.indexOf(value) < 0) {
      input.value = '';
      return;
    }

    if (this.chips.indexOf(value) >= 0) {
      input.value = '';
      return;
    }

    // Add our chip
    if ((value || '').trim()) {
      this.chips.push(value.trim());
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }

    this.chipCtrl.setValue(null);
  }

  remove(chip: string): void {
    const index = this.chips.indexOf(chip);
    if (index >= 0) {
      this.chips.splice(index, 1);
    }
  }

  selected(event: MatAutocompleteSelectedEvent): void {
    if (this.chips.indexOf(event.option.viewValue) >= 0) {
      this.chipCtrl.setValue(null);
      this.chipInput.nativeElement.value = '';
      event.option.value = '';
      return;
    }
    this.chips.push(event.option.viewValue);
    this.chipInput.nativeElement.value = '';
    this.chipCtrl.setValue(null);
  }

  private _filter(value: string): string[] {
    return this.allChips.filter(chip => chip.toLowerCase().indexOf(value) === 0);
  }

  isSubmitDisable() {
    if (!this.agreement || this.validator.isValid(this.codeCtr)) {
      return true;
    }
    return false;
  }

  submitCre() {
    const permissionDto = this.permissionObjects.filter(i => i.check).map(i => i.code);
    const users = this.chipCre.getValue();
    const roleUserUpdateDto = {
      roleDto: {
        authority: this.codeCreCtr.value,
        description: this.descriptionCreCtr.value,
        isUsing: false
      },
      permissionDto,
      users
    };
    this.spinner.show();
    this.roleService.createRoleUser(roleUserUpdateDto).subscribe(res => {
      this.closeCre();
      this.initialData();
    }, err => {
      this.codeCreCtr.setErrors(this.validator.getErrorMessage(err));
      this.spinner.hide();
    });
  }

  isSubmitCreDisable() {
    if (this.codeCreCtr.value === '' || !this.agreement || this.validator.isValid(this.codeCreCtr)) {
      return true;
    }
    return false;
  }

  closeCre() {
    this.isAddingRole = false;
  }

  addCre() {
    this.isAddingRole = !this.isAddingRole;
    if (this.isAddingRole) {
      this.expandedElement = null;
      this.permissionObjects.forEach(i => i.check = false);
    }
  }

  isHideEditBtn(element) {
    return this.isDeleteRoleMode && element === this.elementToDelete;
  }

  enableDeleteRole(element) {
    this.isDeleteRoleMode = true;
    this.elementToDelete = element;
    this.agreementToDelete = false;
    this.expandedElement = null;
  }

  cancelDeleteRole(element) {
    this.elementToDelete = null;
    this.isDeleteRoleMode = false;
    this.agreementToDelete = false;
  }

  isConfirmDeleteDisable(element) {
    return this.agreementToDelete === false;
  }

  deleteRole(element) {
    this.spinner.show();
    this.roleService.deleteRole(element.id).subscribe(res => {
      this.initialData();
    });
  }
}
