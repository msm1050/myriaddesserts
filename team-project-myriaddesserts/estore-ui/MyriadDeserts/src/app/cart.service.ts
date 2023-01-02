import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Injectable } from '@angular/core';
import { Item } from "./items";
import { Product } from './product';
import { ProductService } from './product.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private cartsUrl = 'http://localhost:8080/carts'
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  constructor(private http: HttpClient, private productservice: ProductService){}
  items: Item[] = [];
  request!: Request;
  

  addToCart(item: Item) { 
    this.checkCart(item);   
    this.items.push(item);
  }

  addCart(request: Request): Observable<Request> {
    return this.http.post<Request>(this.cartsUrl, request, this.httpOptions);
  }

  checkCart(item: Item){
    if(this.items.includes(item,0)){
      this.items.pop();
      item.quantity=item.quantity+1;
    }
  }

  removeItem(index: number){
      this.items.splice(index, 1);
  }

  getItems() {
    return this.items;
  }

 
  updateCart(items: Item[]){
    this.items=items;
  }

  getList(){
    return this.productservice.getProducts()
  }

  show(id: number){
    return this.productservice.getProduct(id);
  }

}