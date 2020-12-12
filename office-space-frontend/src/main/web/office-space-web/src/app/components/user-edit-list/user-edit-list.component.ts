import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-user-edit-list',
  templateUrl: './user-edit-list.component.html',
  styleUrls: ['./user-edit-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class UserEditListComponent implements OnInit {

  dataSource: [];
  usernameSearchCtr = new FormControl();
  nameSearchCtr = new FormControl();
  emailSearchCtr = new FormControl();
  columnsToDisplay = ['username', 'name', 'email', 'action'];
  expandedElement = null;
  resultLength = 0;
  pageSize = 0;

  constructor() { }

  ngOnInit(): void {
  }

  expanDetails(element) {
    return element?.code === this.expandedElement?.code ? 'expanded' : 'collapsed';
  }

}
