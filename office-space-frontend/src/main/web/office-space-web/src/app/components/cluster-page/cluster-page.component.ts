import { Component, OnInit } from '@angular/core';
import { PERMISSION_CODE } from 'src/app/enums/permissionCode';
import { StorageService } from 'src/app/services/auth/storage.service';

@Component({
  selector: 'app-cluster-page',
  templateUrl: './cluster-page.component.html',
  styleUrls: ['./cluster-page.component.scss']
})
export class ClusterPageComponent implements OnInit {

  readonly permission = this.storage.get(StorageService.PERMISSION)?.split(',') || [];

  constructor(private storage: StorageService) { }

  ngOnInit(): void {
  }

  isDisplayClusterMng() {
    return this.permission.indexOf(PERMISSION_CODE.CLUSTER_EDIT) >= 0;
  }

  isDisplayNodeMng() {
    return this.permission.indexOf(PERMISSION_CODE.NODE_EDIT) >= 0;
  }

  isDisplayProcess() {
    return this.permission.indexOf(PERMISSION_CODE.PROCESS_EDIT) >= 0;
  }

}
