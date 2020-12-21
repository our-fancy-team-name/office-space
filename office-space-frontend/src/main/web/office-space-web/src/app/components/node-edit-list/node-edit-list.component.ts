import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { NgxSpinnerService } from 'ngx-spinner';
import { ClusterService } from 'src/app/services/cluster.service';
import { NodeService } from 'src/app/services/node.service';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-node-edit-list',
  templateUrl: './node-edit-list.component.html',
  styleUrls: ['./node-edit-list.component.scss']
})
export class NodeEditListComponent implements OnInit {

  isAddingNode = false;
  agreement = false;
  codeCreCtr = new FormControl('', [
    this.validator.required(),
    this.validator.maxLength(5),
    this.validator.minLength(5)
  ]);
  nameCreCtr = new FormControl('');
  descriptionCreCtr = new FormControl('');

  constructor(
    public validator: ValidatorsService,
    private nodeService: NodeService,
    private spinner: NgxSpinnerService,
    private snackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
  }

  addCre() {
    this.isAddingNode = !this.isAddingNode;
    this.agreement = false;
  }

  submitCre() {
    this.spinner.show();
    const data = {
      code: this.codeCreCtr.value,
      name: this.nameCreCtr.value,
      description: this.descriptionCreCtr.value
    };
    this.nodeService.create(data).subscribe(res => {
      this.spinner.hide();
      this.closeCre();

    }, err => {
      this.codeCreCtr.setErrors(this.validator.getErrorMessage(err.error.message));
      this.spinner.hide();
    });

  }

  isSubmitCreDisable() {
    return !this.agreement || this.codeCreCtr.value === '' || this.validator.isValid(this.codeCreCtr);
  }

  closeCre() {
    this.isAddingNode = false;
  }

}
