import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ValidatorsService } from 'src/app/utils/validators.service';

@Component({
  selector: 'app-rdf-edit-list',
  templateUrl: './rdf-edit-list.component.html',
  styleUrls: ['./rdf-edit-list.component.scss']
})
export class RdfEditListComponent implements OnInit {
  isAddingRdf = false;
  subjectCreCtr = new FormControl('', this.validator.required());


  constructor(
    public validator: ValidatorsService
  ) { }

  ngOnInit(): void {
  }

  addCre() { 
    this.isAddingRdf = !this.isAddingRdf;
  }

}
