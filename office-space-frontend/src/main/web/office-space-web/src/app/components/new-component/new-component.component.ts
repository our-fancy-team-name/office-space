import { Component, OnInit } from '@angular/core';
import { Log } from 'src/app/dtos/log';
import { StorageService } from 'src/app/services/auth/storage.service';
import { LogService } from 'src/app/services/log.service';

@Component({
  selector: 'app-new-component',
  templateUrl: './new-component.component.html',
  styleUrls: ['./new-component.component.scss']
})
export class NewComponentComponent implements OnInit {

  constructor(private logService: LogService, private storageService: StorageService) { }
  logDTO: Log;
  recommandedItemList = [];
  itemList = [
    { 'id': 'eraser:', 'name': 'Erase' },
    { 'id': 'wheel:', 'name': 'Wheel' },
    { 'id': 'steering_wheel:', 'name': 'SteeringWheel' },
    { 'id': 'table:', 'name': 'Table' },
    { 'id': 'chair:', 'name': 'Chair' },
    { 'id': 'tshirt:', 'name': 'Tshirt' },
    { 'id': 'pants:', 'name': 'Pants' },
    { 'id': 'pc:', 'name': 'PC' },
    { 'id': 'laptop:', 'name': 'Laptop' },
    { 'id': 'icecream:', 'name': 'IceCream' },
    { 'id': 'flag:', 'name': 'Flag' },
    { 'id': 'stair:', 'name': 'Stair' },
    { 'id': 'sun:', 'name': 'Sun' },
    { 'id': 'road:', 'name': 'Road' },
    { 'id': 'ram:', 'name': 'Ram' },
    { 'id': 'cpu:', 'name': 'CPU' },
    { 'id': 'ssd:', 'name': 'SSD' },
    { 'id': 'cloud:', 'name': 'Cloud' },
    { 'id': 'coin:', 'name': 'Coin' },
    { 'id': 'water:', 'name': 'Water' },
    { 'id': 'leg:', 'name': 'Leg' },
    { 'id': 'arm:', 'name': 'Arm' },
    { 'id': 'face:', 'name': 'Face' },
    { 'id': 'mouse:', 'name': 'Mouse' },
    { 'id': 'browser:', 'name': 'Browser' },
    { 'id': 'software:', 'name': 'Software' },
    { 'id': 'money:', 'name': 'Money' },
    { 'id': 'phone:', 'name': 'Phone' },
    { 'id': 'tv:', 'name': 'TV' },
    { 'id': 'light:', 'name': 'Light' },
    { 'id': 'bed:', 'name': 'Bed' },
    { 'id': 'ac:', 'name': 'AC' },
    { 'id': 'refrigerator:', 'name': 'Refrigerator' },
    { 'id': 'bus:', 'name': 'Bus' },
    { 'id': 'moon:', 'name': 'Moon' },
    { 'id': 'motorbike:', 'name': 'Motorbike' },
    { 'id': 'bicycle:', 'name': 'Bicycle' },
    { 'id': 'pool:', 'name': 'Pool' },
    { 'id': 'calculator:', 'name': 'Calculator' },
    { 'id': 'fan:', 'name': 'Fan' },
    { 'id': 'lamp:', 'name': 'Lamp' },
    { 'id': 'perfume:', 'name': 'Perfume' },
    { 'id': 'door:', 'name': 'Door' },
    { 'id': 'window:', 'name': 'Window' },
    { 'id': 'bathroom:', 'name': 'Bathroom' },
    { 'id': 'book:', 'name': 'Book' },
    { 'id': 'bag:', 'name': 'Bag' },
    { 'id': 'pen:', 'name': 'Pen' },
    { 'id': 'ruler:', 'name': 'Ruler' }
  ]
  ngOnInit(): void {
    for (var i = 0; i < 10; i++) {
      this.recommandedItemList.push(this.itemList[Math.floor(Math.random() * this.itemList.length)]);
    }
  }

  buyButtonOnClick(data: any) {
    this.logDTO = {
      userId: 1,
      millisecondsTime: new Date().getTime(),
      message: `${data.id}${data.name}`
    }
    this.logService.insertLog(this.logDTO).subscribe(ahihi => {
      console.log(ahihi);
    });
  }
}
