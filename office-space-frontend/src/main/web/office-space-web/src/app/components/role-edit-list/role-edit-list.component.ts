import { animate, state, style, transition, trigger } from '@angular/animations';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';
import { TableSearchRequest } from 'src/app/dtos/tableSearch';
import { PERMISSION_CODE } from 'src/app/enums/permissionCode';
import { UserService } from 'src/app/services/user.service';

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
export class RoleEditListComponent implements OnInit {

  dataSource: [] = [];
  columnsToDisplay = ['code', 'description', 'users', 'action'];
  expandedElement = null;

  constructor(
    private userService: UserService
  ) {
    Object.entries(PERMISSION_CODE).forEach(
      ([key, value]) => this.permissionObjects.push({
            code: value,
            check: false
          })
    );
  }

  ngOnInit(): void {
    this.userService.getRoleUserListView(new TableSearchRequest()).subscribe(res => {
      this.dataSource = res.content;
    })
    this.userService.getAllUsers().subscribe((res: any) => {
      this.allChips = res.content.map(item => item.username);
      this.filteredFruits = this.chipCtrl.valueChanges.pipe(
        startWith(null),
        map((chip: string | null) => chip ? this._filter(chip) : this.allChips.slice()));
    })
  }
  
  openElement(element) {
    this.chips = element.users?.split(',') || [];
    this.userService.findAllPermissionByRole(element.code).subscribe(res => {
      const perm = res.map((i: any) => i.code)
      this.permissionObjects.forEach(item => {
        item.check = perm.indexOf(item.code) >= 0;
      })
      this.expandedElement = this.expandedElement?.code === element?.code ? null : element
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
      this.chipInput.nativeElement.value = '';
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
}
