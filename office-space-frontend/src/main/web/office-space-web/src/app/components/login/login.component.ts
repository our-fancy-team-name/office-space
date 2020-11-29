import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { timeout } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth/auth.service';
import { StorageService } from 'src/app/services/auth/storage.service';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public form: FormGroup;
  public errorMessage = '';
  readonly timeOut = 1; //seconds

  constructor(
    private validator: ValidatorsService,
    private router: Router,
    private authService: AuthService,
    private tokenStorage: StorageService
  ) { }

  ngOnInit(): void {
    if (this.tokenStorage.get(StorageService.TOKEN_KEY)) {
      this.router.navigate(['/select-role']);
    }
    this.form = new FormGroup({
      username: new FormControl(null, this.validator.required('LOGIN.USERNAME_RQ')),
      password: new FormControl(null, [this.validator.required('LOGIN.PASSWORD_RQ'), this.validator.minLength(5)])
    }, { updateOn: 'change' });
  }

  get username() {
    return this.form.get('username');
  }

  get password() {
    return this.form.get('password');
  }

  get usernameValid() {
    return this.validator.isValid(this.username);
  }

  get passwordValid() {
    return this.validator.isValid(this.password);
  }

  onSubmit() {
    if (!this.form.valid) {
      return;
    }
    this.form.disable();
    this.errorMessage = '';
    this.authService.login(this.form.value).subscribe(
      data => {
        this.tokenStorage.set(StorageService.TOKEN_KEY, data.token);
        this.tokenStorage.set(StorageService.USER_KEY, JSON.stringify(data));
        this.router.navigate(['/select-role']);
      },
      err => {
        this.errorMessage = err.error.message + '. Please wait 5 seconds...';
      }
    ).add(() => {
      setTimeout(() => {
        this.form.enable();
        location.reload();
      }, this.timeOut * 1000);
    });
  }

}
