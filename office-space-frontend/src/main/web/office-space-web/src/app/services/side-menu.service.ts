import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { MENU_ITEM } from '../enums/menuItemEnum';
import { PERMISSION_CODE } from '../enums/permissionCode';
import { StorageService } from './auth/storage.service';

@Injectable({
  providedIn: 'root'
})
export class SideMenuService {

  public sideMenuSubject: BehaviorSubject<any> = new BehaviorSubject<any>([]);


  constructor(
    private storage: StorageService
  ) { }

  setMenu(items) {
    this.sideMenuSubject.next(items);
  }

  updateMenuByPermission() {
    const permission = this.storage.get(StorageService.PERMISSION)?.split(',') || [];
    if (permission.indexOf(PERMISSION_CODE.ROLE_EDIT) >= 0 || permission.indexOf(PERMISSION_CODE.ROLE_DELETE) >= 0) {
      this.sideMenuSubject.next(Array.of(MENU_ITEM.MANAGE_AUTHORITY));
    }
  }
}
