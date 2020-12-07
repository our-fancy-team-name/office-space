import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/services/auth/storage.service';

@Component({
  selector: 'app-role-select',
  templateUrl: './role-select.component.html',
  styleUrls: ['./role-select.component.scss']
})
export class RoleSelectComponent implements OnInit {

  userDetails;

  constructor(
    private storage: StorageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.userDetails = JSON.parse(this.storage.get(StorageService.USER_KEY)).userDetails;
    this.storage.set(StorageService.ROLE, '');
  }

  continue(role) {
    console.log(role);
    this.storage.set(StorageService.ROLE, role.authority);
    this.router.navigate(['/demo']);
  }

}
