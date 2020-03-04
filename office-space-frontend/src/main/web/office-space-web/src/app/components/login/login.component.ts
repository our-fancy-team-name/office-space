import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
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

  constructor(
    private validator: ValidatorsService,
    private router: Router,
    private authService: AuthService,
    private tokenStorage: StorageService
  ) { }

  ngOnInit(): void {
    if (this.tokenStorage.get(StorageService.TOKEN_KEY)) {
      this.router.navigate(['/demo']);
    }
    this.form = new FormGroup({
      username: new FormControl(null, this.validator.required()),
      password: new FormControl(null, [this.validator.required(), this.validator.minLength(5)])
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
        this.router.navigate(['/demo']);
      },
      err => {
        this.errorMessage = err.error.message;
      }
    ).add(() => {
      this.form.enable();
    });
  }

}
