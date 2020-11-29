import { Component, OnInit } from '@angular/core';
import { StorageService } from 'src/app/services/auth/storage.service';

@Component({
  selector: 'app-role-select',
  templateUrl: './role-select.component.html',
  styleUrls: ['./role-select.component.scss']
})
export class RoleSelectComponent implements OnInit {

  userDetails;

  constructor(
    private storage: StorageService
  ) { }

  ngOnInit(): void {
    this.userDetails = JSON.parse(this.storage.get(StorageService.USER_KEY)).userDetails;
  }

}
