import { animate, state, style, transition, trigger } from '@angular/animations';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { TableSearchRequest } from 'src/app/dtos/tableSearch';
import { UserService } from 'src/app/services/user.service';
import { ValidatorsService } from 'src/app/utils/validators.service';

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
export class UserEditListComponent implements OnInit {

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
  agreement = false;

  constructor(
    private userService: UserService,
    public validator: ValidatorsService
  ) { }

  ngOnInit(): void {
    this.initialData();
  }

  initialData() {
    this.userService.getUserRoleListView(new TableSearchRequest()).subscribe((res: any) => {
      this.dataSource = res.content;
    });
    this.filteredChips = this.chipCtrl.valueChanges.pipe(
      startWith(''),
      map((chip: string | '') => chip ? this._filter(chip) : this.allChips.slice()));
  }

  expanDetails(element) {
    return element === this.expandedElement ? 'expanded' : 'collapsed';
  }

  isHideEditBtn(element) {
    return this.isDeleteMode;
  }

  openElement(element) {
    this.expandedElement = this.expandedElement === element ? null : element;
  }

  enableDeleteUser(element) {

  }

  deleteUser(element) {

  }

  isConfirmDeleteDisable(element) {

  }

  cancelDeleteUser(element) {

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

  }

  isSubmitDisable() {

  }

  closeElement() {
  }

}
