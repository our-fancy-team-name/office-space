import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-new-component',
  templateUrl: './new-component.component.html',
  styleUrls: ['./new-component.component.scss']
})
export class NewComponentComponent implements OnInit {

  constructor() { }

  recommandedItemList = [];
  itemList = [
    { 'id': '1', 'name': '1' },
    { 'id': '2', 'name': '2' },
    { 'id': '3', 'name': '3' },
    { 'id': '4', 'name': '4' },
    { 'id': '5', 'name': '5' },
    { 'id': '6', 'name': '6' },
    { 'id': '7', 'name': '7' },
    { 'id': '8', 'name': '8' },
    { 'id': '9', 'name': '9' },
    { 'id': '10', 'name': '10' },
    { 'id': '11', 'name': '11' },
    { 'id': '12', 'name': '12' },
    { 'id': '13', 'name': '13' },
    { 'id': '14', 'name': '14' },
    { 'id': '15', 'name': '15' },
    { 'id': '16', 'name': '16' },
    { 'id': '17', 'name': '17' },
    { 'id': '18', 'name': '18' },
    { 'id': '19', 'name': '19' },
    { 'id': '20', 'name': '20' },
    { 'id': '21', 'name': '21' },
    { 'id': '22', 'name': '22' },
    { 'id': '23', 'name': '23' },
    { 'id': '24', 'name': '24' },
    { 'id': '25', 'name': '25' },
    { 'id': '26', 'name': '26' },
    { 'id': '27', 'name': '27' },
    { 'id': '28', 'name': '28' },
    { 'id': '29', 'name': '29' },
    { 'id': '30', 'name': '30' }
  ]
  ngOnInit(): void {
    for (var i = 0; i < 10; i++) {
      this.recommandedItemList.push(this.itemList[Math.floor(Math.random() * this.itemList.length)]);
    }
  }
}
