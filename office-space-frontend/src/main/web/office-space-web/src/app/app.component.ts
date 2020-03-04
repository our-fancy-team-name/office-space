import { Component, OnInit } from '@angular/core';
import { StorageService } from './services/auth/storage.service';
import { AuthService } from './services/auth/auth.service';

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

  constructor(private tokenStorageService: StorageService, private aut: AuthService) { }

  ngOnInit(): void {
    this.isLoggedIn = !!this.tokenStorageService.get(StorageService.TOKEN_KEY);

    if (this.isLoggedIn) {
      const user = JSON.parse(this.tokenStorageService.get(StorageService.USER_KEY));
      this.username = user.username;
    }
  }

}
