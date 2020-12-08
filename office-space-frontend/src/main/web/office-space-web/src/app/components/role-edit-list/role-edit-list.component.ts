import { trigger, state, style, transition, animate } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { TableSearchRequest } from 'src/app/dtos/tableSearch';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-role-edit-list',
  templateUrl: './role-edit-list.component.html',
  styleUrls: ['./role-edit-list.component.scss',],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class RoleEditListComponent implements OnInit {

  dataSource: [] = [];
  columnsToDisplay = ['code', 'description', 'users', 'action'];
  expandedElement = null;

  constructor(
    private userService: UserService
  ) { }

  ngOnInit(): void {
    this.userService.getRoleUserListView(new TableSearchRequest()).subscribe(res => {
      this.dataSource = res.content
    })
  }

}
