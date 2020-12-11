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
import { RoleService } from 'src/app/services/role.service';
import { UserService } from 'src/app/services/user.service';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-role-edit-list',
  templateUrl: './role-edit-list.component.html',
  styleUrls: ['./role-edit-list.component.scss',],
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

  dataSource: [] = [];
  columnsToDisplay = ['code', 'description', 'users', 'action'];
  expandedElement = null;
  agreement = false;
  codeCtr = new FormControl('',[
    this.validator.required()
  ]);
  codeSearchCtr = new FormControl();
  descriptionSearchCtr = new FormControl();
  userSearchCtr = new FormControl();
  descriptionCtr = new FormControl('');
  resultLength = 0;
  pageSize = 0;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private userService: UserService,
    public validator: ValidatorsService,
    private roleService: RoleService,
    private spinner: NgxSpinnerService,
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
  }

  ngOnInit(): void {
    
  }

  initialData() {
    this.enableTableFilter();
    this.spinner.show();
    this.searchTableData();
    this.userService.getAllUsers().subscribe((res: any) => {
      this.allChips = res.content.map(item => item.username);
      this.filteredFruits = this.chipCtrl.valueChanges.pipe(
        startWith({}),
        map((chip: string | null) => chip ? this._filter(chip) : this.allChips.slice()));
        this.spinner.hide();
    })
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
          const tableRequest = new TableSearchRequest();
          const pageRequest = new TablePagingRequest();
          const colRequest: ColumnSearchRequest[] = [
            {
              columnName: "code",
              operation: DataBaseOperation.LIKE,
              term: this.codeSearchCtr.value || ''
            },
            {
              columnName: "description",
              operation: DataBaseOperation.LIKE,
              term: this.descriptionSearchCtr.value || ''
            },
            {
              columnName: "users",
              operation: DataBaseOperation.LIKE,
              term: this.userSearchCtr.value || ''
            }
          ]
          pageRequest.page = this.paginator.pageIndex;
          pageRequest.pageSize = this.paginator.pageSize;
          tableRequest.pagingRequest = pageRequest;
          tableRequest.columnSearchRequests = colRequest;
          return this.userService.getRoleUserListView(tableRequest);
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
      });
  }
  
  openElement(element) {
    this.chips = element.users?.split(',') || [];
    this.userService.findAllPermissionByRole(element.code).subscribe(res => {
      const perm = res.map((i: any) => i.code)
      this.permissionObjects.forEach(item => {
        item.check = perm.indexOf(item.code) >= 0;
      })
      this.codeCtr.setValue(element.code);
      this.descriptionCtr.setValue(element.description);
      this.expandedElement = this.expandedElement?.code === element?.code ? null : element;
      this.agreement = false;
      if(this.expandedElement === null) {
        this.enableTableFilter();
      } else {
        this.disableTableFilter();
      }
    })
  }

  disableTableFilter() {
    this.codeSearchCtr.disable();
    this.descriptionSearchCtr.disable();
    this.userSearchCtr.disable();
  }

  enableTableFilter() {
    this.codeSearchCtr.enable();
    this.descriptionSearchCtr.enable();
    this.userSearchCtr.enable();
  }

  closeElement() {
    this.expandedElement = null;
    this.agreement = false;
    this.enableTableFilter();
  }

  submit(element) {
    const permissionDto = this.permissionObjects.filter(i => i.check).map(i => {return {code: i.code}});
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
    }
    this.spinner.show();
    this.roleService.updateRoleUser(roleUserUpdateDto).subscribe(res => {
      this.closeElement();
      this.initialData();
    })
  }

  expanDetails(element) {
    return element?.code === this.expandedElement?.code ? 'expanded' : 'collapsed';
  }



  // mat chip ----------------------------------------------------

  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  chipCtrl = new FormControl();
  filteredFruits: Observable<string[]>;
  chips: string[];
  allChips: string[];
  @ViewChild('chipInput') chipInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  add(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if(this.allChips.indexOf(value) < 0) {
      input.value = '';
      return;
    }

    if(this.chips.indexOf(value) >=0) {
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

  // permission------------------------------------------
  permissionObjects = [];
  checked = true;

  isSubmitDisable() {
    if (!this.agreement) return true;
    if (this.validator.isValid(this.codeCtr)) return true;
    return false;
  }
}
