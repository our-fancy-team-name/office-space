import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ProcessService } from 'src/app/services/process.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { ChipsComponent } from '../../chips/chips.component';

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
  node;
  rawData;

  @Output()
  closeEdit = new EventEmitter<any>();

  @ViewChild('chipInput') chipInput: ChipsComponent;
  @ViewChild('chipOutput') chipOutput: ChipsComponent;
  constructor(
    public validator: ValidatorsService,
    private processService: ProcessService
  ) { }

  public setData(data, nodeId: number) {
    this.rawData = data;
    this.agreement = false;
    this.node = data.nodes?.filter(i => i.id === nodeId)[0] || {};
    this.allChips = data.nodes?.map(i => (i.code));
    const nodeInputIds = data.clusterNodesPath?.filter(i => i.clusterNodeIdTo === nodeId).map(i => (i.clusterNodeIdFrom)) || [];
    const nodeOutputIds = data.clusterNodesPath?.filter(i => i.clusterNodeIdFrom === nodeId).map(i => (i.clusterNodeIdTo)) || [];
    this.chipInput.setValue(data.nodes?.filter(i => nodeInputIds.indexOf(i.id) >= 0).map(i => (i.code)));
    this.chipOutput.setValue(data.nodes?.filter(i => nodeOutputIds.indexOf(i.id) >= 0).map(i => (i.code)));
    this.codeCreCtr.setValue(this.node.code);
    this.nameCreCtr.setValue(this.node.name);
    this.descriptionCreCtr.setValue(this.node.description);
  }

  ngOnInit(): void {
  }

  submit() {
    const input = this.chipInput.getValue().map(code => this.rawData.nodes?.filter(e => e.code === code)[0])
      .map(e => ({
        clusterNodeIdTo: this.node.id,
        clusterNodeIdFrom: e.id
      }));
    const output = this.chipOutput.getValue().map(code => this.rawData.nodes?.filter(e => e.code === code)[0])
      .map(e => ({
        clusterNodeIdTo: e.id,
        clusterNodeIdFrom: this.node.id
      }));
    const clusterNodeEditDto = {
      id: this.node.id,
      input,
      output
    };
    this.processService.editClusterNode(clusterNodeEditDto).subscribe(res => {
      this.closeEdit.emit(true);
    });

  }

  isSubmitDisable() {
    return !this.agreement;
  }

  close() {
    this.closeEdit.emit(true);
  }

  remvoveFromCluster() {
    this.processService.removeNodeFromCluster(this.node.id).subscribe(res => {
      this.close();
    });
  }

}
