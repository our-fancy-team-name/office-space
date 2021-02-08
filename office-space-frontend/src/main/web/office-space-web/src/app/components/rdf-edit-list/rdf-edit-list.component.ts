import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ClusterNode, Edge, Node } from '@swimlane/ngx-graph';
import { Subject } from 'rxjs';
import { RdfService } from 'src/app/services/rdf.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { SelectSearchComponent } from '../select-search/select-search.component';
import { D3ForceDirectedCustomLayout } from './D3ForceDirectedCustomLayout';

@Component({
  selector: 'app-rdf-edit-list',
  templateUrl: './rdf-edit-list.component.html',
  styleUrls: ['./rdf-edit-list.component.scss']
})
export class RdfEditListComponent implements OnInit {
  isAddingRdf = false;
  subjectCreCtr = new FormControl('', this.validator.required());
  objectCreCtr = new FormControl('', this.validator.required());
  namespaces = [];
  iris = [];
  isValueLiteral = false;
  layout: D3ForceDirectedCustomLayout = new D3ForceDirectedCustomLayout();

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


  constructor(
    public validator: ValidatorsService,
    private rdfService: RdfService
  ) { }

  ngOnInit(): void {
    this.rdfService.getDefinedNamespace().subscribe(res => {
      this.namespaces = res;
    });
    this.rdfService.getDefinedIRLsNoFilter().subscribe(res => {
      this.iris = res;
    });
    this.rdfService.getAll().subscribe(res => {
      console.log(res);
      res.forEach(element => {
        this.toNode(element.object);
        this.toPath(element.object, element.subject, element.predicate);
        this.toNode(element.subject);
      });
    });
  }

  toNode(data) {
    this.nodes.push({
      id: this.hash(data.namespace + '#' + data.localName),
      label: data.namespace + '#' + data.localName
    } as Node);
  }

  toPath(object, subject, predicate) {
    this.links.push({
      source: this.hash(object.namespace + '#' + object.localName),
      target: this.hash(subject.namespace + '#' + subject.localName),
      label: predicate.namespace
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
