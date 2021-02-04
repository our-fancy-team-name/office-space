import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { RdfService } from 'src/app/services/rdf.service';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-rdf-edit-list',
  templateUrl: './rdf-edit-list.component.html',
  styleUrls: ['./rdf-edit-list.component.scss']
})
export class RdfEditListComponent implements OnInit {
  isAddingRdf = false;
  subjectCreCtr = new FormControl('', this.validator.required());
  namespaces = [];


  constructor(
    public validator: ValidatorsService,
    private rdfService: RdfService
  ) { }

  ngOnInit(): void {
    this.rdfService.getDefinedNamespace().subscribe(res => {
      this.namespaces = res;
    })
  }

  addCre() { 
    this.isAddingRdf = !this.isAddingRdf;
  }

}
