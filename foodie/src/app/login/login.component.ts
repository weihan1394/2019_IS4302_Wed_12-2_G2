import { AuthenticationService } from './../_services/authentication.service';
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  returnUrl:string = "";

  validation_messages = {
    'email': [
      { type: 'required', message: 'Email is required.' },
      { type: 'email', message: 'Invalid email.' }
    ],
    'password': [
      { type: 'required', message: 'Password is required.' }
    ],
  }

  loginForm: FormGroup;

  constructor(private authenticationService: AuthenticationService, private router: Router, 
    private formBuilder: FormBuilder, private messageService: MessageService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.returnUrl =  this.route.snapshot.queryParamMap.get('returnUrl'); 
    this.loginForm = this.formBuilder.group({
      email: new FormControl('', {
        validators: [Validators.required, Validators.email]
      }),
      password: new FormControl('', Validators.compose([
        Validators.required
      ])),
    });
  }

  getEmailErrorMessage() {
    return this.loginForm.get('email').hasError('required') ? 'Email is required' :
        this.loginForm.get('email').hasError('email') ? 'Not a valid email' :
            '';
  }

  onSubmit() {
    if (this.loginForm.valid) {
      let formValues = this.loginForm.value;
      let email = formValues.email;
      let password = formValues.password;
      this.authenticationService.login(email, password).subscribe(
        res => {
          if(this.returnUrl) {
            this.router.navigate([this.returnUrl]);
          } else {
            this.router.navigate(['/home']);
          }
          
        }, error => {
          // this.error(error)
          // error(error.error.message)
          this.messageService.add({ severity: 'error', summary: 'Login Error', detail: error });
        }
      )
    } else {
      Object.keys(this.loginForm.controls).forEach(field => {
        const control = this.loginForm.get(field);
        control.markAsTouched({ onlySelf: true });
      });
    }
  }

  switchPage(page){

  }

}
