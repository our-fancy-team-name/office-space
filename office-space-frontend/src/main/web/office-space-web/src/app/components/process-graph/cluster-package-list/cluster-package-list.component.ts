import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { STATUS } from 'src/app/enums/packageStatusEnum';
import { ProcessPackageService } from 'src/app/services/process-package.service';
import { SelectSearchComponent } from '../../select-search/select-search.component';

@Component({
  selector: 'app-cluster-package-list',
  templateUrl: './cluster-package-list.component.html',
  styleUrls: ['./cluster-package-list.component.scss']
})
export class ClusterPackageListComponent implements OnInit, AfterViewInit {

  displayType = SelectSearchComponent.displayTypeEnum.Text;
  quatity = new FormControl();
  options = [];
  clusterNodeId;
  status = [
    {
      value: STATUS.FAIL
    },
    {
      value: STATUS.WIP
    },
    {
      value: STATUS.PASS
    }
  ];

  @ViewChild('packageSelect') packageSelect: SelectSearchComponent;
  @ViewChild('statusSelect') statusSelect: SelectSearchComponent;

  constructor(
    private prcPkgService: ProcessPackageService,
    private spinner: NgxSpinnerService
  ) { }

  ngAfterViewInit(): void {

  }

  public setData(clusterNodeId) {
    this.clusterNodeId = clusterNodeId;
    this.prcPkgService.getValidPksToAdd(clusterNodeId).subscribe(res => {
      this.packageSelect.setOptions(res);
    });
  }

  ngOnInit(): void {
  }

  submit() {
    const processPackageDto = {
      packageId: this.packageSelect.getValue().packageId,
      clusterNodeId: this.clusterNodeId,
      amount: this.quatity.value,
      status: this.statusSelect.getValue().value
    };
    this.spinner.show();
    this.prcPkgService.addPkgToCltNode(processPackageDto).subscribe(res => {
      this.packageSelect.selectCtr.reset();
      this.quatity.reset();
      this.statusSelect.selectCtr.reset();
      this.spinner.hide();
    });
  }

  isSubmitCreDisable() {
    return this.quatity.value <= 0
    || this.quatity.value == null
    || this.packageSelect.getValue() == null
    || this.statusSelect.getValue() == null;
  }

}
