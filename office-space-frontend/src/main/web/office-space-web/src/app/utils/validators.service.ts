import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ValidatorsService {

  constructor() { }

  public isValid(formControl: AbstractControl): boolean {
    return formControl.invalid && (formControl.dirty || formControl.touched);
  }

  public required(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any | null } => {
      if (!control.value) {
        return { message: 'field required' };
      }
      return null;
    };
  }

  public maxLength(max: number): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any | null } => {
      if (control.value && control.value.length > max) {
        return { message: `maximum length allow is ${max}`};
      }
      return null;
    };
  }

  public minLength(min: number): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any | null } => {
      if (control.value && control.value.length < min) {
        return { message: `minimum length allow is ${min}`};
      }
    };
  }
}
