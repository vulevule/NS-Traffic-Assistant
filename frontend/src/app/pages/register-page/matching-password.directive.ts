import { Directive } from '@angular/core';
import { AbstractControl, FormGroup, NG_VALIDATORS, ValidationErrors, Validator, ValidatorFn } from '@angular/forms';

export const matchingPasswordValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  const pass1 = control.get('inputPassword');
  const pass2 = control.get('repeatPassword');

  return pass1 && pass2 && pass1.value !== pass2.value ? { 'matchingPassword': true } : null;
};

@Directive({
  selector: '[appMatchingPassword]',
  providers: [{ provide: NG_VALIDATORS, useExisting: MatchingPasswordDirective, multi: true }]
})
export class MatchingPasswordDirective implements Validator {

  validate(control: AbstractControl): ValidationErrors {
    return matchingPasswordValidator(control)
  }

}
