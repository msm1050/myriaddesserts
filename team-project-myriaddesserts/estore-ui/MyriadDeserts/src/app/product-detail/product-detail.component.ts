import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { Product } from '../product';
import { ProductService } from '../product.service';
import { CheckboxControlValueAccessor } from '@angular/forms';
declare let alertify: any;

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: [ './product-detail.component.css' ]
})
export class ProductDetailComponent implements OnInit {
  product: Product | undefined;  

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getProduct();
  }

  getProduct(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.productService.getProduct(id)
      .subscribe(product => this.product = product);
  }

  goBack(): void {
    this.location.back();
  }

  Check() {    
    if((this.product?.quantity)==0){      
      this.runCustomAlert(this.product.name); 
    }         
  }

  runCustomAlert(name: string){
    alertify.alert().setting({
      'closable': true,
      'resizable': true,
      'message': "Reorder Inventory Item: "+name
    }).show()
  }

  save(): void {
    if (this.product) {      
      this.productService.updateProduct(this.product).subscribe(() => this.goBack());      
    }      
  }
}