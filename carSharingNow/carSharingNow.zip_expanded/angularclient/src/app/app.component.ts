import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'car Sharing Now';
  users : any;

  constructor(private http: HttpClient) {
    http.get('/users/all').subscribe((data: {}) => this.users = data);
  }
}
