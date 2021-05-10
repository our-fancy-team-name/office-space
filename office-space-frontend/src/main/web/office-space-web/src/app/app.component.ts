import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { AfterContentInit, Component, isDevMode, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { StorageService } from './services/auth/storage.service';
import { NgxSpinnerService } from 'ngx-spinner';
import { TranslateService } from '@ngx-translate/core';
import { LANGUAGES } from './enums/languagesEnum';
import { SideMenuService } from './services/side-menu.service';
import { MENU_ITEM } from './enums/menuItemEnum';
import { Router } from '@angular/router';
import { AuthService } from './services/auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterContentInit {
  title = 'office-space-web';
  isExpanded = true;
  marginLeft = '200px';
  isLoggedIn = false;
  isSelectedRole = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username: string;
  languageSelected = this.storage.get(StorageService.LANGUAGE) || LANGUAGES.EN;
  eng;
  vie;
  showVersion = isDevMode();
  version: any = {};

  menuItem = [];

  menuLength;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(
    private storage: StorageService,
    private authService: AuthService,
    private breakpointObserver: BreakpointObserver,
    private spinner: NgxSpinnerService,
    private translate: TranslateService,
    private sideMenuService: SideMenuService,
    private router: Router
  ) {
    this.sideMenuService.sideMenuSubject.subscribe(event => {
      this.menuItem = event;
      this.updateMenuLength();
    });
  }

  ngAfterContentInit(): void {
    this.changeLang(null);
    this.spinner.hide();
    setTimeout(() => {
      this.sideMenuService.updateMenuByPermission();
    }, 800);
    this.updateMenuLength();
  }

  updateMenuLength() {
    this.menuLength = 'calc(100vh - ' + (128 + this.menuItem.length * 56) + 'px)';
  }
  ngOnInit(): void {
    this.spinner.show();
    this.storage.set(StorageService.API, isDevMode() ? environment.api : `${window.location.origin}/api/`);
    this.isLoggedIn = !!this.storage.get(StorageService.TOKEN_KEY);
    this.isSelectedRole = !!this.storage.get(StorageService.ROLE);
    if (isDevMode()) {
      this.authService.version().subscribe(res => {
        this.version = res;
      });
    }
    if (this.isLoggedIn) {
      const user = JSON.parse(this.storage.get(StorageService.USER_KEY));
      this.username = user.userDetails.username;
    }
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
    if (this.languageSelected === LANGUAGES.EN) {
      this.eng = 'English';
      this.vie = 'Vietnamese';
    } else {
      this.eng = 'Tiếng Anh';
      this.vie = 'Tiếng Việt';
    }
    this.storage.set(StorageService.LANGUAGE, this.languageSelected);
    this.translate.use(this.languageSelected);
  }

  openMenuItem(item) {
    switch (item.title) {
      case MENU_ITEM.MANAGE_AUTHORITY.title:
        this.router.navigate(['/role-manage']);
        break;
      case MENU_ITEM.MANAGE_PRODUCT.title:
        this.router.navigate(['/product-manage']);
        break;
      case MENU_ITEM.MANAGE_CLUSTER.title:
        this.router.navigate(['/cluster-manage']);
        break;
      case MENU_ITEM.MANAGE_RDF.title:
        this.router.navigate(['/rdf-manage']);
        break;
      case MENU_ITEM.MANAGE_NEWPAGE.title:
        this.router.navigate(['/new-manage']);
        break;
      default:
        break;
    }

  }

}
