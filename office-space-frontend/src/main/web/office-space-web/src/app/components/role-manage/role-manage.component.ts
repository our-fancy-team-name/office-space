import { Component, OnInit } from '@angular/core';
import { PERMISSION_CODE } from 'src/app/enums/permissionCode';
import { StorageService } from 'src/app/services/auth/storage.service';

@Component({
  selector: 'app-role-manage',
  templateUrl: './role-manage.component.html',
  styleUrls: ['./role-manage.component.scss']
})
export class RoleManageComponent implements OnInit {

  readonly permission = this.storage.get(StorageService.PERMISSION)?.split(',') || [];

  constructor(
    private storage: StorageService
  ) { }

  ngOnInit(): void {
  }

  isDisplayRoleMng() {
    return this.permission.indexOf(PERMISSION_CODE.ROLE_EDIT) >= 0;
  }

  isDisplayUserMng() {
    return this.permission.indexOf(PERMISSION_CODE.USER_EDIT) >= 0;
  }

}
