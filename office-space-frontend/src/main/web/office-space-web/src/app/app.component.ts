import { Component, isDevMode, OnInit } from '@angular/core';
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

  isLoggedIn = false;
  showAdminBoard = false;
  showModeratorBoard = false;
  username: string;

  constructor(private storage: StorageService, private aut: AuthService) { }

  ngOnInit(): void {
    this.storage.set(StorageService.API, isDevMode() ? environment.api : `${window.location.origin}/api/`);
    this.isLoggedIn = !!this.storage.get(StorageService.TOKEN_KEY);

    if (this.isLoggedIn) {
      const user = JSON.parse(this.storage.get(StorageService.USER_KEY));
      this.username = user.username;
    }
  }

}
