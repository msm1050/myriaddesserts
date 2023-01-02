import { Component, OnInit } from '@angular/core';
import { Item} from '../items';
import { ItemService } from '../item.service';
import { CartService } from '../cart.service';
import { Router } from '@angular/router';
import { Product } from '../product';
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  items: Item[] = [];
  products: Product[] =[];
  toggle = true;
  status = "Enable";

  constructor(private itemService: ItemService, private cartService: CartService) { }

  ngOnInit(): void {
    this.getItems();
  }

  selectedItem?: Item;
  selectedProducts?: Product[];

  onSelect(item: Item): void {
    this.selectedItem = item;
  }
  
  Customize(item:Item): void{
    this.selectedItem=item;
  }

  getItems(): void {
    this.itemService.getItems()
      .subscribe(items => this.items = items.slice(0,-1));
  }
  

  addToCart(item: Item) {  
    if(item.ingredients.length>0){this.cartService.checkCart(item);  
      this.cartService.addToCart(item);
      window.alert('Your product has been added to the cart!');}
    else {
      window.alert('Ingredient list is empty');
    }
    
  }

  getList(item: Item){
    (this.cartService.getList()).subscribe(products => this.products=products);
  }

  clear(item: Item){
      while (item.ingredients.length>0){
        item.ingredients.pop();
      }     
  }

  select(item: Item, product: Product){
    this.cartService.show(product.id);
    if (!item.ingredients.includes(product.name)){
      item.ingredients.push(product.name);
    }    
  }

  deselect(item: Item, product: Product){
    this.cartService.show(product.id);
    item.ingredients.splice(item.ingredients.indexOf(product.name),1)
  }

  
  
}