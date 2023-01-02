import { Component, OnInit } from '@angular/core';
import { Item } from '../items';
import { ITEMS} from '../list-items';

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {  

  items = ITEMS;
  selectedItem?: Item;

  onSelect(item: Item): void {
    this.selectedItem = item;
  }

  constructor() { }

  ngOnInit(): void {
  }

 
  
}
