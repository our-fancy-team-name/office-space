import { AfterViewInit, Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormControl } from '@angular/forms';
import { debounceTime } from 'rxjs/operators';
import { TableSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { NodeService } from 'src/app/services/node.service';

@Component({
  selector: 'app-node-picker',
  templateUrl: './node-picker.component.html',
  styleUrls: ['./node-picker.component.scss']
})
export class NodePickerComponent implements OnInit, AfterViewInit {

  @Output()
  picked = new EventEmitter<any>();

  readonly tableSearchObject: TableSearchRequest = {
    pagingRequest: {
      page: 0,
      pageSize: 10
    },
    columnSearchRequests: [
      {
        columnName: 'code',
        operation: DataBaseOperation.LIKE,
        term: '',
        isOrTerm: true
      },
      {
        columnName: 'name',
        operation: DataBaseOperation.LIKE,
        term: '',
        isOrTerm: true
      }
    ],
    sortingRequest: null
  };

  nodes = [];
  codeSearchCtr = new FormControl('');

  constructor(
    private nodeService: NodeService
  ) { }

  ngAfterViewInit(): void {
    this.codeSearchCtr.valueChanges.pipe(debounceTime(500)).subscribe(res => {
      const data = JSON.parse(JSON.stringify(this.tableSearchObject));
      data.columnSearchRequests.forEach(e => e.term = this.codeSearchCtr.value);
      this.nodeService.getListView(data).subscribe(res2 => {
        this.nodes = res2.content;
      });
    });
    this.codeSearchCtr.setValue('');
  }

  ngOnInit(): void {
  }

  pickedNode(item) {
    this.picked.emit(item);
  }

}
