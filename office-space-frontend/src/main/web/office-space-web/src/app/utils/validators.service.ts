import { Injectable } from '@angular/core';
import { AbstractControl, ValidatorFn } from '@angular/forms';
import { TranslateService } from '@ngx-translate/core';

@Injectable({
  providedIn: 'root'
})
export class ValidatorsService {

  constructor(private t: TranslateService) { }

  public isValid(formControl: AbstractControl): boolean {
    return formControl.invalid && (formControl.dirty || formControl.touched);
  }

  public required(message?: string): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any | null } => {
      if (!control.value) {
        return { message: message ? message : 'VALIDATOR.FIELD_REQUIRED' };
      }
      return null;
    };
  }

  public maxLength(max: number, message?: string): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any | null } => {
      if (control.value && control.value.length > max) {
        return { message: message ? message : 'VALIDATOR.MAX_LENGTH'};
      }
      return null;
    };
  }

  public minLength(min: number, message?: string ): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any | null } => {
      if (control.value && control.value.length < min) {
        return { message: message ? message : 'VALIDATOR.MIN_LENGTH'};
      }
    };
  }
}
