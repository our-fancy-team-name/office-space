import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from 'src/app/services/auth/storage.service';
import { PermissionService } from 'src/app/services/permission.service';
import { SideMenuService } from 'src/app/services/side-menu.service';

@Component({
  selector: 'app-role-select',
  templateUrl: './role-select.component.html',
  styleUrls: ['./role-select.component.scss']
})
export class RoleSelectComponent implements OnInit {

  userDetails;

  constructor(
    private storage: StorageService,
    private router: Router,
    private permissionService: PermissionService,
    private sideMenu: SideMenuService
  ) { }

  ngOnInit(): void {
    this.userDetails = JSON.parse(this.storage.get(StorageService.USER_KEY)).userDetails;
    this.storage.set(StorageService.ROLE, '');
    this.storage.set(StorageService.PERMISSION, '');
    this.sideMenu.updateMenuByPermission();
  }

  continue(role) {
    this.storage.set(StorageService.ROLE, role.authority);
    this.permissionService  .findAllPermissionByRole(role.authority).subscribe((item: []) => {
      this.storage.set(StorageService.PERMISSION, item.map((i: any) => i.code));
      this.sideMenu.updateMenuByPermission();
    });
    this.router.navigate(['/demo']);
  }

}
