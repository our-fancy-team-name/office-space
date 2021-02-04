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

  updateMenuByPermission() {
    const menuItem = [];
    const permission = this.storage.get(StorageService.PERMISSION)?.split(',') || [];
    if (permission.indexOf(PERMISSION_CODE.ROLE_EDIT) >= 0 || permission.indexOf(PERMISSION_CODE.USER_EDIT) >= 0) {
      menuItem.push(MENU_ITEM.MANAGE_AUTHORITY);
    }
    if (permission.indexOf(PERMISSION_CODE.PRODUCT_EDIT) >= 0 || permission.indexOf(PERMISSION_CODE.PACKAGE_EDIT) >= 0 ) {
      menuItem.push(MENU_ITEM.MANAGE_PRODUCT);
    }
    if (permission.indexOf(PERMISSION_CODE.CLUSTER_EDIT) >= 0 || permission.indexOf(PERMISSION_CODE.NODE_EDIT) >= 0 ) {
      menuItem.push(MENU_ITEM.MANAGE_CLUSTER);
    }
    menuItem.push(MENU_ITEM.MANAGE_RDF);
    this.sideMenuSubject.next(menuItem);
  }
}
