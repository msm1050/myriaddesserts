import { Injectable } from '@angular/core';
import { Item } from './items';

@Injectable({
  providedIn: 'root'
})
export class CheckoutService {

  constructor() { }

  sum!:number;

  checkprice(items: Item[]){
    this.sum=0;
    for (let i = 0; i < items.length; i++) {
      this.sum =this.sum+(items[i].price*items[i].quantity);}
    return this.sum;
  }
}
