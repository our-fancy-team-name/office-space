import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/services/auth/storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user: any;

  constructor(private storage: StorageService) { }

  ngOnInit(): void {
    this.user = JSON.parse(this.storage.get(StorageService.USER_KEY));
  }

  signOut() {
    this.storage.clear();
    location.reload();
  }

}
