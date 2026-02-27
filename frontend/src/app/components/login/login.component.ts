import { Component } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username = '';
  password = '';
  error = '';

  constructor(private http: HttpClient, private router: Router) {}

  login() {
    const token = btoa(this.username + ':' + this.password);

    const headers = new HttpHeaders({
      'Authorization': 'Basic ' + token
    });

    this.http.get('/api/base/students', { headers: headers }).subscribe({
      next: (response) => {
        console.log('Вход успешен!', response);
        this.router.navigate(['/students']);
      },
      error: (err) => {
        console.error('Ошибка входа', err);
        this.error = 'Неверный логин или пароль';
      }
    });
  }
}
