import { ENTER, COMMA } from '@angular/cdk/keycodes';
import { AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatAutocomplete, MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatChipInputEvent } from '@angular/material/chips';
import { Observable } from 'rxjs';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-chips',
  templateUrl: './chips.component.html',
  styleUrls: ['./chips.component.scss']
})
export class ChipsComponent implements OnInit {

  @Input() allChips;
  @Input() initialChips?;

  visible = true;
  selectable = true;
  removable = true;
  separatorKeysCodes: number[] = [ENTER, COMMA];
  chipCtrl = new FormControl();
  fillterdChips: Observable<string[]>;
  chips: string[] = ['dang'];
  @ViewChild('chipInput') chipInput: ElementRef<HTMLInputElement>;
  @ViewChild('auto') matAutocomplete: MatAutocomplete;

  constructor() {
  }

  ngOnInit(): void {
    this.chips = this.initialChips || [];
    this.fillterdChips = this.chipCtrl.valueChanges.pipe(
      startWith(''),
      map((chip: string | '') => chip ? this._filter(chip) : this.allChips.slice()));
  }

  getValue() {
    return this.chips;
  }

  setValue(value: string[]) {
    this.chips = value;
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
    return this.allChips?.filter(chip => chip.toLowerCase().indexOf(value) === 0);
  }

}
