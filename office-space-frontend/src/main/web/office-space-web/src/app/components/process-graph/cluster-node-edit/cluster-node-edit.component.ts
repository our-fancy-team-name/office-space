import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-cluster-node-edit',
  templateUrl: './cluster-node-edit.component.html',
  styleUrls: ['./cluster-node-edit.component.scss']
})
export class ClusterNodeEditComponent implements OnInit {
  allChips  = [];
  agreement = false;
  codeCreCtr = new FormControl({value: '', disabled: true}, [
    this.validator.required(),
    this.validator.maxLength(5),
    this.validator.minLength(5)
  ]);
  nameCreCtr = new FormControl({value: '', disabled: true});
  descriptionCreCtr = new FormControl({value: '', disabled: true});

  constructor(
    public validator: ValidatorsService
  ) { }

  public setData(data, nodeId: number) {
    const node = data.nodes?.filter(i => i.id === nodeId)[0] || {};
    this.codeCreCtr.setValue(node.code);
    this.nameCreCtr.setValue(node.name);
    this.descriptionCreCtr.setValue(node.description);
    this.allChips = data.nodes?.map(i => (i.code));
    console.log(node);
  }

  ngOnInit(): void {
  }

  submit() {

  }

  isSubmitDisable() {

  }

  close() {

  }

}
