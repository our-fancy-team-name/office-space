import { AfterContentInit, AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { TranslateService } from '@ngx-translate/core';
import { ClusterNode, Edge, Node } from '@swimlane/ngx-graph';
import { merge, Observable, Subject } from 'rxjs';
import { catchError, debounceTime, map, startWith, switchMap } from 'rxjs/operators';
import { TableSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { StorageService } from 'src/app/services/auth/storage.service';
import { ProcessService } from 'src/app/services/process.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { SelectSearchComponent } from '../select-search/select-search.component';
import { ClusterNodeEditComponent } from './cluster-node-edit/cluster-node-edit.component';

@Component({
  selector: 'app-process-graph',
  templateUrl: './process-graph.component.html',
  styleUrls: ['./process-graph.component.scss']
})
export class ProcessGraphComponent implements OnInit, AfterViewInit, AfterContentInit {
  readonly TIMEOUT = 200;
  viewWidth;
  viewHeight;
  center: Subject<boolean> = new Subject();
  reset: Subject<any> = new Subject();
  panToNode: Subject<any> = new Subject();
  zoomToFit: Subject<any> = new Subject();
  changeSize = new Subject();
  draggingEnabled = false;
  panningEnabled = true;
  enableZoom = true;
  @ViewChild('ngxGraphWrapper') graphWrapper: ElementRef;
  @ViewChild('clusterSelector') clusterSelector: SelectSearchComponent;
  @ViewChild(ClusterNodeEditComponent) clusterNodeEdit: ClusterNodeEditComponent;
  clusters: ClusterNode[] = [];
  nodes: Node[] = [];
  links: Edge[] = [];
  isHideMap = true;
  addingNote = false;
  graphDto = null;
  isHideEditor = true;

  url = `${this.storage.get(StorageService.API)}cluster/list-view`;

  tableSearchObject: TableSearchRequest = {
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
    pagingRequest: {
      page: 0,
      pageSize: 6
    },
    sortingRequest: null
  };

  constructor(
    public validator: ValidatorsService,
    private storage: StorageService,
    private processService: ProcessService,
    private snackBar: MatSnackBar,
    private translate: TranslateService
  ) { }

  toNode(data) {
    this.nodes.push({
      id: `n${data.id}`,
      label: data.code
    } as Node);
  }

  toCluster(data, childNodeIds) {
    this.clusters = [];
    this.clusters.push({
      id: `c${data.id}`,
      label: new Array(data.code, data.name).join(' - '),
      childNodeIds
    } as ClusterNode);
  }

  toPath(data) {
    this.links.push({
      id: `e${data.id}`,
      source: `n${data.clusterNodeIdFrom}`,
      target: `n${data.clusterNodeIdTo}`,
      label: data.label
    });
  }

  resetGraph() {
    this.nodes = [];
    this.clusters = [];
    this.links = [];
  }

  setData(data) {
    this.resetGraph();
    data.nodes?.forEach(i => this.toNode(i));
    this.toCluster(data.cluster, this.nodes?.map(i => i.id) || []);
    data.clusterNodesPath?.forEach(i => this.toPath(i));
  }

  ngAfterContentInit(): void {
  }

  ngAfterViewInit(): void {
    this.changeSize.pipe(debounceTime(this.TIMEOUT)).subscribe(res => {
      this.setSizeForGraph();
    });
    merge(
      this.clusterSelector.selectCtr.valueChanges,
      this.reset
    ).pipe(
      startWith({}),
      switchMap((data) => {
        if (this.clusterSelector.getValue() === '') {
          return new Observable();
        }
        return this.processService.getByClusterId(this.clusterSelector.getValue().id);
      }),
      map(data => {
        this.graphDto = data;
        return data;
      }),
      catchError(() => {
        return new Observable();
      })
    ).subscribe(data => {
      this.isHideMap = false;
      this.setSizeForGraph();
      this.setData(data);
    });
  }

  centerGraph() {
    this.center.next(true);
  }

  refreshGraph() {
    this.reset.next();
  }

  zoomGraph() {
    this.zoomToFit.next(true);
  }

  setSizeForGraph() {
    this.viewWidth = this.graphWrapper.nativeElement.offsetWidth;
    this.viewHeight = this.graphWrapper.nativeElement.offsetHeight;
    this.centerGraph();
  }

  ngOnInit(): void {
  }

  nodeClicked(event, node) {
    this.panToNode.next(node.id);
    this.clusterNodeEdit.setData(this.graphDto, +node.id.substring(1));
    this.isHideEditor = false;
  }

  scroll(el: HTMLElement) {
    setTimeout(() => {
      el.scrollIntoView({behavior: 'smooth'});
    }, 100);
  }

  clusterClicked(event, cluster) {
    console.log(event);
    console.log(cluster);
  }

  onContainerResized(event) {
    this.changeSize.next();
  }

  openAddNote() {
    this.addingNote = !this.addingNote;
  }

  addNodeIntoCluster(node) {
    const clusterId = this.graphDto.cluster.id;
    const nodeId = node.id;
    const graphDto = {
      cluster: {
        id: clusterId
      },
      nodes: [
        {
          id: nodeId
        }
      ]
    };
    this.processService.addNodeToCluster(graphDto).subscribe(res => {
      this.refreshGraph();
    }, err => {
      this.translate.get(this.validator.getErrorMessage(err.error.message).message).subscribe(mes => {
        this.snackBar.open(mes, '', {
          duration: 5000
        });
      });
    });
  }
}
