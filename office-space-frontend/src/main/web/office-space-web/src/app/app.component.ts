import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, isDevMode, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { StorageService } from './services/auth/storage.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { TranslateService } from '@ngx-translate/core';
import { LANGUAGES } from './enums/languagesEnum';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'office-space-web';
  isExpanded = true;
  marginLeft = '200px';
  isLoggedIn = false;
  isSelectedRole = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username: string;
  languageSelected;

  menuItem = [];
  // [
  //   {
  //     title: 'EMPLOYEE_LIST',
  //     icon: 'people',
  //     isActive: false,
  //   },
  //   {
  //     title: 'EMPLOYEE_LIST',
  //     icon: 'people',
  //     isActive: false
  //   }
  // ];

  menuLength;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    private storage: StorageService,
    private breakpointObserver: BreakpointObserver,
    private spinner: NgxSpinnerService,
    private translate: TranslateService
  ) { }

  ngOnInit(): void {
    this.spinner.show();
    this.menuLength = 'calc(100vh - ' + (128 + this.menuItem.length * 56) + 'px)';
    this.storage.set(StorageService.API, isDevMode() ? environment.api : `${window.location.origin}/api/`);
    this.isLoggedIn = !!this.storage.get(StorageService.TOKEN_KEY);
    this.isSelectedRole = !!this.storage.get(StorageService.ROLE);

    if (this.isLoggedIn) {
      const user = JSON.parse(this.storage.get(StorageService.USER_KEY));
      this.username = user.userDetails.username;
    }
    setTimeout(() => {
      this.spinner.hide();
      this.languageSelected = LANGUAGES.EN;
    }, 500);
  }

  toggleSideBar() {
    this.isExpanded = !this.isExpanded;
    this.marginLeft = this.isExpanded ? '205px' : '72px';
  }

  logOut() {
    this.storage.clear();
    location.reload();
  }

  changeLang(event) {
    this.translate.use(this.languageSelected);
  }

}
