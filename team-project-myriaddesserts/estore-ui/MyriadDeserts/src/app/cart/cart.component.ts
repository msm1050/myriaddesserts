import { Component, OnInit } from '@angular/core';
import { CartService } from '../cart.service';
import { Location } from '@angular/common';
import { Item } from '../items';
import { CheckoutService } from '../checkout.service';
import { Action } from 'rxjs/internal/scheduler/Action';
declare let alertify: any;

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  items = this.cartService.getItems(); 
  tprice!: Number;
  bool!: Boolean;
  b!: Boolean;

  constructor(private cartService: CartService, private location: Location, private checkoutservice: CheckoutService) { }

  ngOnInit(): void {
  }

  clearCart() {
    this.items = [];
    return this.items;
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {       
      this.cartService.updateCart(this.items);          
 }

 removeItem(index: number) {
  this.cartService.removeItem(index);
}


  preview_checkout(items: Item[]){    
    this.tprice=this.checkoutservice.checkprice(items);
    alertify.alert().setting({
      'message': "Total Price is: "+this.tprice
    }).show();
  }

  checkout(items: Item[]){      
    this.tprice=this.checkoutservice.checkprice(items);
    alertify.alert().setting({
      'message': "Checkout confirmed with total Price being: "+this.tprice
    }).show() 
    return this.items=[]; 
    
 }
}

  

