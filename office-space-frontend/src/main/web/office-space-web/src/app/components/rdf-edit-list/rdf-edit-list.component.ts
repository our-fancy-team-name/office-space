import { Component, OnInit, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { RdfService } from 'src/app/services/rdf.service';
import { ValidatorsService } from 'src/app/utils/validators.service';
import { SelectSearchComponent } from '../select-search/select-search.component';

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

  @ViewChild('namespaceSubject') namespaceSubject: SelectSearchComponent;
  @ViewChild('predicate') predicate: SelectSearchComponent;
  @ViewChild('namespaceObject') namespaceObject: SelectSearchComponent;


  constructor(
    public validator: ValidatorsService,
    private rdfService: RdfService
  ) { }

  ngOnInit(): void {
    this.rdfService.getDefinedNamespace().subscribe(res => {
      this.namespaces = res;
    })
    this.rdfService.getDefinedIRLsNoFilter().subscribe(res => {
      this.iris = res;
    })
  }

  addCre() { 
    this.isAddingRdf = !this.isAddingRdf;
  }

  submitCre(){
    const rdfCreateDto = {
      subject : {
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
    }
    this.rdfService.create(rdfCreateDto).subscribe(res => {

    })
  }

  isSubmitCreDisable(){}

  closeCre(){
    this.isAddingRdf = false;
  }

}
