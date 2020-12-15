import { Component, OnInit } from '@angular/core';
import { PERMISSION_CODE } from 'src/app/enums/permissionCode';
import { StorageService } from 'src/app/services/auth/storage.service';

@Component({
  selector: 'app-product-page',
  templateUrl: './product-page.component.html',
  styleUrls: ['./product-page.component.scss']
})
export class ProductPageComponent implements OnInit {

  readonly permission = this.storage.get(StorageService.PERMISSION)?.split(',') || [];

  constructor(
    private storage: StorageService
  ) { }

  ngOnInit(): void {
  }

  isDisplayProductMng() {
    return this.permission.indexOf(PERMISSION_CODE.PRODUCT_EDIT) >= 0;
  }

}
