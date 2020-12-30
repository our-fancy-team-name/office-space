import { animate, state, style, transition, trigger } from '@angular/animations';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatPaginator } from '@angular/material/paginator';
import { NgxSpinnerService } from 'ngx-spinner';
import { merge, Observable } from 'rxjs';
import { catchError, debounceTime, map, startWith, switchMap } from 'rxjs/operators';
import { ColumnSearchRequest, TablePagingRequest, TableSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { RoleService } from 'src/app/services/role.service';
import { UserService } from 'src/app/services/user.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { ChipsComponent } from '../chips/chips.component';

@Component({
  selector: 'app-user-edit-list',
  templateUrl: './user-edit-list.component.html',
  styleUrls: ['./user-edit-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class UserEditListComponent implements OnInit, AfterViewInit {

  readonly TIME_OUT = 800;

  dataSource: [];
  usernameSearchCtr = new FormControl();
  nameSearchCtr = new FormControl();
  emailSearchCtr = new FormControl();
  rolesSearchCtr = new FormControl();
  columnsToDisplay = ['username', 'name', 'email', 'roles', 'action'];
  expandedElement = null;
  resultLength = 0;
  pageSize = 0;
  agreementToDelete = false;
  isDeleteMode = false;

  usernameCtr = new FormControl('', [
    this.validator.required()
  ]);
  usernameCreCtr = new FormControl('', [
    this.validator.required()
  ]);
  passwordCreCtr = new FormControl('', [
    this.validator.required(),
    this.validator.minLength(5)
  ]);
  passwordRepeatCreCtr = new FormControl('', [
    this.validator.required(),
    this.validator.minLength(5)
  ]);
  isMaleCre = false;
  addressCreCtr = new FormControl();
  firstnameCreCtr = new FormControl('');
  lastnameCreCtr = new FormControl('');
  phoneCreCtr = new FormControl();
  altPhoneCreCtr = new FormControl();
  emailCreCtr = new FormControl();
  isAddingUser = false;

  addressCtr = new FormControl();
  firstnameCtr = new FormControl();
  lastnameCtr = new FormControl();
  phoneCtr = new FormControl();
  altPhoneCtr = new FormControl();
  emailCtr = new FormControl();

  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  chipCtrl = new FormControl();
  filteredChips: Observable<string[]>;
  chips: string[] = [];
  allChips: string[] = ['dang', 'dang2'];
  @ViewChild('chipInput') chipInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;
  @ViewChild('chipsCre') chipCreat: ChipsComponent;
  agreement = false;
  isMale = false;
  elementToDelete = null;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(
    private userService: UserService,
    public validator: ValidatorsService,
    private roleService: RoleService,
    private spinner: NgxSpinnerService
  ) { }
  ngAfterViewInit(): void {
    this.searchTableData();
  }

  ngOnInit(): void {
    this.initialData();
  }

  searchTableData() {
    merge(
      this.usernameSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.emailSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.rolesSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.nameSearchCtr.valueChanges.pipe(debounceTime(this.TIME_OUT)),
      this.paginator.page
    ).pipe(
        startWith({}),
        switchMap(() => {
          this.spinner.show();
          const tableRequest = new TableSearchRequest();
          const pageRequest = new TablePagingRequest();
          const colRequest: ColumnSearchRequest[] = [
            {
              columnName: 'username',
              operation: DataBaseOperation.LIKE,
              term: this.usernameSearchCtr.value || ''
            },
            {
              columnName: 'name',
              operation: DataBaseOperation.LIKE,
              term: this.nameSearchCtr.value || ''
            },
            {
              columnName: 'email',
              operation: DataBaseOperation.LIKE,
              term: this.emailSearchCtr.value || ''
            },
            {
              columnName: 'roles',
              operation: DataBaseOperation.LIKE,
              term: this.rolesSearchCtr.value || ''
            }
          ];
          pageRequest.page = this.paginator.pageIndex;
          pageRequest.pageSize = this.paginator.pageSize;
          tableRequest.pagingRequest = pageRequest;
          tableRequest.columnSearchRequests = colRequest;
          return this.userService.getUserRoleListView(tableRequest);
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

  initialData() {
    this.spinner.show();
    this.roleService.getAllRoleCode().subscribe(res => {
      this.allChips = res;
      this.filteredChips = this.chipCtrl.valueChanges.pipe(
        startWith(''),
        map((chip: string | '') => chip ? this._filter(chip) : this.allChips.slice()));
      this.spinner.hide();
    });
  }

  expanDetails(element) {
    return element === this.expandedElement ? 'expanded' : 'collapsed';
  }

  isHideEditBtn(element) {
    return this.isDeleteMode && element === this.elementToDelete;
  }

  openElement(element) {
    this.agreement = false;
    this.isAddingUser = false;
    this.expandedElement = this.expandedElement === element ? null : element;
    if (this.expandedElement === null) {
      return;
    }
    this.userService.getUserDetails(element.id).subscribe((res: any) => {
      this.usernameCtr.setValue(res.username);
      this.emailCtr.setValue(res.email);
      this.chips = element.roles?.split(',') || [];
      this.firstnameCtr.setValue(res.firstName || '');
      this.lastnameCtr.setValue(res.lastName || '');
      this.addressCtr.setValue(res.address);
      this.phoneCtr.setValue(res.phone);
      this.altPhoneCtr.setValue(res.alternatePhone);
      this.isMale = res.gender === 'MALE';
    });
  }

  enableDeleteUser(element) {
    this.elementToDelete = element;
    this.isDeleteMode = true;
    this.agreementToDelete = false;
  }

  deleteUser(element) {
    this.spinner.show();
    this.userService.deleteUser(element.id).subscribe(res => {
      this.paginator.page.emit();
    });
  }

  isConfirmDeleteDisable(element) {
    return !this.agreementToDelete;
  }

  cancelDeleteUser(element) {
    this.isDeleteMode = false;
    this.elementToDelete = null;
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

  submit(element) {
    this.spinner.show();
    const userDto = {
      id: element.id,
      username: this.usernameCtr.value,
      email: this.emailCtr.value || '',
      firstName: this.firstnameCtr.value,
      lastName: this.lastnameCtr.value,
      phone: this.phoneCtr.value,
      alternatePhone: this.altPhoneCtr.value,
      address: this.addressCtr.value,
      gender: this.isMale ? 'MALE' : 'FEMALE',
    };
    const roles = this.chips;
    const roleUserUpdateDto = {
      userDto,
      roles
    };
    this.userService.update(roleUserUpdateDto).subscribe(res => {
      this.closeElement();
      this.spinner.hide();
      this.initialData();
    }, err => {
      this.usernameCtr.setErrors(this.validator.getErrorMessage(err.error.message));
      this.spinner.hide();
    });
  }

  isSubmitDisable() {
    return this.usernameCtr.value === '' || this.agreement === false;
  }

  closeElement() {
    this.expandedElement = null;
  }

  addCre() {
    this.isAddingUser = !this.isAddingUser;
    this.closeElement();
    this.agreement = false;
  }

  submitCre() {
    if (this.passwordCreCtr.value !== this.passwordRepeatCreCtr.value) {
      this.passwordRepeatCreCtr.setErrors({message: `${this.validator.VALIDATOR}.NOT_THE_SAME_PASSWORD`});
      return;
    }
    const userDto = {
      username: this.usernameCreCtr.value,
      password: this.passwordCreCtr.value,
      email: this.emailCreCtr.value || '',
      firstName: this.firstnameCreCtr.value,
      lastName: this.lastnameCreCtr.value,
      phone: this.phoneCreCtr.value,
      alternatePhone: this.altPhoneCreCtr.value,
      address: this.addressCreCtr.value,
      gender: this.isMaleCre ? 'MALE' : 'FEMALE'
    };
    const roles = this.chipCreat.getValue();
    const roleUserUpdateDto = {
      userDto,
      roles
    };
    this.spinner.show();
    this.userService.createUser(roleUserUpdateDto).subscribe(res => {
      this.usernameCreCtr.reset();
      this.passwordCreCtr.reset();
      this.passwordRepeatCreCtr.reset();
      this.emailCreCtr.reset();
      this.firstnameCreCtr.reset();
      this.lastnameCreCtr.reset();
      this.phoneCreCtr.reset();
      this.altPhoneCreCtr.reset();
      this.addressCreCtr.reset();
      this.isMaleCre = false;
      this.closeCre();
      this.spinner.hide();
      this.paginator.page.emit();
    }, err => {
      this.usernameCreCtr.setErrors(this.validator.getErrorMessage(err.error.message));
      this.spinner.hide();
    });
  }

  isSubmitCreDisable() {
    if ( this.usernameCreCtr.value === ''
    || this.passwordCreCtr.value === ''
    || this.passwordRepeatCreCtr.value === ''
    || this.agreement === false) {
      return true;
    }
    if (this.validator.isValid(this.passwordCreCtr) || this.validator.isValid(this.passwordRepeatCreCtr)) {
      return true;
    }
  }

  closeCre() {
    this.isAddingUser = false;
  }

}
