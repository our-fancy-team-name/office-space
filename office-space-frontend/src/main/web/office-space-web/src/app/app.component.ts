import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Component, isDevMode, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
import { environment } from 'src/environments/environment';
import { AuthService } from './services/auth/auth.service';
import { StorageService } from './services/auth/storage.service';

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
  showAdminBoard = false;
  showModeratorBoard = false;
  username: string;

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay()
    );

  constructor(private storage: StorageService, private aut: AuthService, private breakpointObserver: BreakpointObserver) { }

  ngOnInit(): void {
    this.storage.set(StorageService.API, isDevMode() ? environment.api : `${window.location.origin}/api/`);
    this.isLoggedIn = !!this.storage.get(StorageService.TOKEN_KEY);

    if (this.isLoggedIn) {
      const user = JSON.parse(this.storage.get(StorageService.USER_KEY));
      this.username = user.username;
    }
  }

  ahihi() {
    this.isExpanded = !this.isExpanded;
    this.marginLeft = this.isExpanded ? '205px' : '72px';
  }

}
