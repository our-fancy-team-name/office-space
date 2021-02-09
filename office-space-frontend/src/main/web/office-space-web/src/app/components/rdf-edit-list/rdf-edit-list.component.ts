import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ClusterNode, Edge, Node } from '@swimlane/ngx-graph';
import { Subject } from 'rxjs';
import { debounceTime } from 'rxjs/operators';
import { TableSearchRequest } from 'src/app/dtos/tableSearch';
import { DataBaseOperation } from 'src/app/enums/tableSearchEnum';
import { StorageService } from 'src/app/services/auth/storage.service';
import { RdfService } from 'src/app/services/rdf.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { SelectSearchComponent } from '../select-search/select-search.component';
import { ColaForceDirectedLayout } from './ColaForceDirectedCustomLayout';

@Component({
  selector: 'app-rdf-edit-list',
  templateUrl: './rdf-edit-list.component.html',
  styleUrls: ['./rdf-edit-list.component.scss']
})
export class RdfEditListComponent implements OnInit, AfterViewInit {
  readonly TIMEOUT = 200;
  isAddingRdf = false;
  subjectCreCtr = new FormControl('', this.validator.required());
  objectCreCtr = new FormControl('', this.validator.required());
  namespaces = [];
  iris = [];
  isValueLiteral = false;
  layout: ColaForceDirectedLayout = new ColaForceDirectedLayout();
  url = `${this.storage.get(StorageService.API)}rdf/iris/search`;

  tableSearchObject: TableSearchRequest = {
    columnSearchRequests: [
      {
        columnName: 'name',
        operation: DataBaseOperation.LIKE,
        term: '',
        isOrTerm: true
      }
    ],
    pagingRequest: {
      page: 0,
      pageSize: 100
    },
    sortingRequest: null
  };

  @ViewChild('namespaceSubject') namespaceSubject: SelectSearchComponent;
  @ViewChild('predicate') predicate: SelectSearchComponent;
  @ViewChild('namespaceObject') namespaceObject: SelectSearchComponent;


  @ViewChild('ngxGraphWrapper') graphWrapper: ElementRef;

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
  clusters: ClusterNode[] = [];
  nodes: Node[] = [];
  links: Edge[] = [];
  nodeId: Set<string> = new Set();


  constructor(
    public validator: ValidatorsService,
    private rdfService: RdfService,
    private storage: StorageService
  ) { }

  ngAfterViewInit(): void {
    this.changeSize.pipe(debounceTime(this.TIMEOUT)).subscribe(res => {
      this.setSizeForGraph();
    });
  }

  ngOnInit(): void {
    this.rdfService.getDefinedNamespace().subscribe(res => {
      this.namespaces = res;
    });
    this.rdfService.getDefinedIRLsNoFilter().subscribe(res => {
      this.iris = res;
    });
    this.rdfService.getAll().subscribe(res => {
      res.forEach(element => {
        const objectId = this.hash(element.object.namespace + '#' + element.object.localName);
        const subjectId = this.hash(element.subject.namespace + '#' + element.subject.localName);
        if (!this.nodeId.has(objectId)) {
          this.toNode(element.object);
          this.nodeId.add(objectId);
        }
        if (!this.nodeId.has(subjectId)) {
          this.toNode(element.subject);
          this.nodeId.add(subjectId);
        }
        this.toPath(objectId, subjectId, element.predicate);
      });
      this.setSizeForGraph();
    });
  }

  onContainerResized(event) {
    this.changeSize.next();
  }

  toNode(data) {
    this.nodes.push({
      id: this.hash(data.namespace + '#' + data.localName),
      label: data.namespace + '#' + data.localName
    } as Node);
  }

  toPath(objectId, subjectId, predicate) {
    this.links.push({
      source: objectId,
      target: subjectId,
      label: predicate.namespace + '#' + predicate.localName
    });
  }

  hash(value): string {
    let hash = 0;
    for (let i = 0; i < value.length; i++) {
      // tslint:disable-next-line: no-bitwise
      hash = ((hash << 5) - hash) + value.charCodeAt(i);
      // tslint:disable-next-line: no-bitwise
      hash |= 0; // Convert to 32bit integer
    }
    return '' + hash;
  }

  addCre() {
    this.isAddingRdf = !this.isAddingRdf;
  }

  submitCre() {
    const rdfCreateDto = {
      subject: {
        namespace: this.namespaceSubject.getValue(),
        localName: this.subjectCreCtr.value
      },
      predicate: {
        namespace: this.predicate.getValue().iri.namespace,
        localName: this.predicate.getValue().iri.localName,
      },
      object: {
        namespace: this.namespaceObject.getValue(),
        localName: this.objectCreCtr.value
      }
    };
    this.rdfService.create(rdfCreateDto).subscribe(res => {
      this.closeCre();
    });
  }

  isSubmitCreDisable() { }

  closeCre() {
    this.isAddingRdf = false;
  }

  setSizeForGraph() {
    this.viewWidth = this.graphWrapper.nativeElement.offsetWidth;
    this.viewHeight = this.graphWrapper.nativeElement.offsetHeight;
    this.centerGraph();
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

}
