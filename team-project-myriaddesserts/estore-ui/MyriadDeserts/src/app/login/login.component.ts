import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  

  loginForm!: FormGroup
  constructor(private formBuilder: FormBuilder, private _http: HttpClient, private router: Router, private cartservice: CartService) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: [''],
      password: ['']
    })
  }

  //defining Login Method
  logIn() {
    this._http.get<any>("http://localhost:3000/signups").subscribe(res => {
      const user = res.find((a: any) => {
        return a.email === this.loginForm.value.email && a.password === this.loginForm.value.password
      })
      if(this.loginForm.value.email == "admin" && this.loginForm.value.password == "1234567890"){
        alert("Welcome Staff");
        this.loginForm.reset();
        this.router.navigate(['products'])
      }
      else if (user) {
        alert("Login is Successful");  
       this.loginForm.reset();
       this.router.navigate(['dashboard'])
      } 
      else {
        alert("Login Failed");
      }
    }
    )
  }

  logOut(){
    this.router.navigate(['/login'])
  }

}
