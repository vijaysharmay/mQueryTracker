import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  data: object;
  private API_URL = environment.API_URL

  constructor(private httpClient: HttpClient) {}

  private getData(){
    this.httpClient.get(`${this.API_URL}/get/data`, {
      params: {
        database: 'local',
        collection: 'c3'
      },
      observe: 'response'
    })
    .toPromise()
    .then(response => {
      this.data = response.body;
    })
    .catch(console.log);
  }

  ngOnInit() {
    // this.getData()
  }

}
