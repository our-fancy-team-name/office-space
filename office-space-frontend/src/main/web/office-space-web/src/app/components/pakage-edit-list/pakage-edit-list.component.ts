import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { NgxSpinnerService } from 'ngx-spinner';
import { TableSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { StorageService } from 'src/app/services/auth/storage.service';
import { PackageService } from 'src/app/services/package.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { SelectSearchComponent } from '../select-search/select-search.component';

@Component({
  selector: 'app-pakage-edit-list',
  templateUrl: './pakage-edit-list.component.html',
  styleUrls: ['./pakage-edit-list.component.scss']
})
export class PakageEditListComponent implements OnInit {
  isAddingPkg = false;
  serialNumberCreCtr = new FormControl('', [
    this.validator.required()
  ]);
  url = `${this.storage.get(StorageService.API)}product/list-name`;
  tableSearchObject: TableSearchRequest = {
    columnSearchRequests: [
      {
        columnName: 'name',
        operation: DataBaseOperation.LIKE,
        term: '',
        isOrTerm: true
      },
      {
        columnName: 'partNumber',
        operation: DataBaseOperation.LIKE,
        term: '',
        isOrTerm: true
      }
    ],
    pagingRequest: {
      page: 0,
      pageSize: 6
    },
    sortingRequest: null
  };
  descriptionCreCtr = new FormControl();
  agreement = false;

  @ViewChild('prdCre') productCre: SelectSearchComponent;

  constructor(
    public validator: ValidatorsService,
    private storage: StorageService,
    private packageService: PackageService,
    private spinner: NgxSpinnerService
  ) { }

  ngOnInit(): void {
  }

  addCre() {
    this.isAddingPkg = !this.isAddingPkg;
    this.agreement = false;
  }

  submitCre() {
    this.spinner.show();
    const packageDto = {
      productId: this.productCre.getValue().id,
      serialNumber: this.serialNumberCreCtr.value,
      description: this.descriptionCreCtr.value
    };

    this.packageService.create(packageDto).subscribe(res => {
      this.serialNumberCreCtr.reset();
      this.descriptionCreCtr.reset('');
      this.productCre.setValue(null);
      this.spinner.hide();
      this.closeCre();
    }, err => {
      this.serialNumberCreCtr.setErrors(this.validator.getErrorMessage(err.error.message));
      this.spinner.hide();
    });

  }

  isSubmitCreDisable() {
    return (this.productCre?.getValue() || null) === null || !this.agreement || this.serialNumberCreCtr.value === '';
  }

  closeCre() {
    this.isAddingPkg = false;
  }
}
