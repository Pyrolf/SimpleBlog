import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { LoginPayload } from '../login-payload';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginPayload: LoginPayload;
  usernameNotFound: boolean;
  passwordIncorrect: boolean;

  constructor(private formBuilder: FormBuilder, private authService: AuthService) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [
        Validators.required,
        Validators.minLength(8)
      ]]
    });
    this.loginPayload = {
      username: '',
      password: ''
    };
    this.usernameNotFound = false;
    this.passwordIncorrect = false;
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.loginPayload.username = this.loginForm.get('username')!.value;
    this.loginPayload.password = this.loginForm.get('password')!.value;

    this.authService.login(this.loginPayload).subscribe({
      complete: () => {
        console.log('login success');
        window.location.href = window.location.origin;
      },
      error: (e) => {
        console.log('login failed');
        console.error(e.error.message)
        if (e.error.message === 'Username Not Found') {
          this.usernameNotFound = true;
          this.passwordIncorrect = false;
        } else if (e.error.message === 'Access Denied') {
          this.usernameNotFound = false;
          this.passwordIncorrect = true;
        }
      }
    });
  }
}
