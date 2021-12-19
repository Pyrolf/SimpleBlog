import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { matchValidator } from 'src/app/form-validator';
import { AuthService } from '../auth.service';
import { RegisterPayload } from '../register-payload';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  registerForm: FormGroup;
  registerPayload: RegisterPayload;
  usernameTaken: boolean;
  emailTaken: boolean;

  constructor(private formBuilder: FormBuilder, private authService: AuthService) {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [
        Validators.required,
        Validators.minLength(8),
        matchValidator('confirmPassword', true)]],
      confirmPassword: ['', [
        Validators.required,
        matchValidator('password')
      ]]
    });
    this.registerPayload = {
      username: '',
      email: '',
      password: ''
    };
    this.usernameTaken = false;
    this.emailTaken = false;
  }

  ngOnInit(): void {
  }

  onSubmit(): void {
    if (this.registerForm.get('password')!.value === this.registerForm.get('confirmPassword')!.value) {
      this.registerPayload.username = this.registerForm.get('username')!.value;
      this.registerPayload.email = this.registerForm.get('email')!.value;
      this.registerPayload.password = this.registerForm.get('password')!.value;

      this.authService.register(this.registerPayload).subscribe({
        complete: () => {
          console.log('register success');
          window.location.href = window.location.origin + '/register-success';
        },
        error: (e) => {
          console.error(e.error.message)
          if (e.error.message === 'Username taken') {
            this.usernameTaken = true;
            this.emailTaken = false;
          } else if (e.error.message === 'Email taken') {
            this.usernameTaken = false;
            this.emailTaken = true;
          }
        }
      });
      return;
    }
    console.log('register failed: passwords mismatch')
  }
}
