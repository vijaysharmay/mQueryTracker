import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Headers, RequestOptions } from '@angular/http';
import { environment } from '../../../environments/environment';
import { SourceRow, Error } from './sources.models';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import 'rxjs/add/observable/throw';
import { EmptyObservable } from 'rxjs/observable/EmptyObservable';
import { Observable } from 'rxjs/Observable';


@Component({
  selector: 'app-sources',
  templateUrl: './sources.component.html',
  styleUrls: ['./sources.component.scss']
})

export class SourcesComponent implements OnInit {

  private API_URL = environment.API_URL;

  source: SourceRow;
  sources: Array<SourceRow>;
  error: Error;
  sourceLoading: boolean;
  sourceStatus: boolean;
  resetFlag: boolean;

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) {
    this.source = new SourceRow();
    this.error = new Error();
    this.sources = [];
    this.sourceLoading = false;
    this.sourceStatus = false;
    this.resetFlag = true;
  }

  checkSourceBeforeAddOrTest(row){
    for (var key in row) {
      if (row[key] !== null && row[key] != ""){
        return false;
      }
    }
    return true;
  }

  getSources() {
    this.http.get(`${this.API_URL}/get/sources`).subscribe(data => {
      this.sources = Object.values(data);
      this.sources.push(new SourceRow());
    });
  }

  editSource(row){
    row.id = null;
  }

  resetSource(){
    this.sources[this.sources.length - 1] = new SourceRow()
  }

  updateSource(){
    if(this.checkSourceBeforeAddOrTest(this.source)){
      alert("Please add all details")
    }else{
      this.http.post(`${this.API_URL}/update/source`, JSON.stringify(this.source), this.httpOptions).subscribe(data => {
        if('message' in data){
          alert('Source already exists - Please add a unique (name, host, port) source')
        }else{
          let row = Object.assign(new SourceRow(), data)
          this.sources[this.source.id - 1] = row;
          this.source = new SourceRow();
          this.sourceStatus = false;
        }
      });
    }
  }

  addSource(row) {
    if(this.checkSourceBeforeAddOrTest(row)){
      alert("Please add all details")
    }else{
      row.id = 0;
      this.http.post(`${this.API_URL}/insert/source`, JSON.stringify(row), this.httpOptions).subscribe(data => {
        if('message' in data){
          alert('Source already exists - Please add a unique (name, host, port) source')
        }else{
          this.source = new SourceRow();
          let row = Object.assign(new SourceRow(), data)
          this.sources.pop();
          this.sources.push(row);
          this.sources.push(this.source);
          this.sourceStatus = false;
        }
      });
    }
  }

  testSource() {
    if(this.checkSourceBeforeAddOrTest(this.source)){
      alert("Please add all details")
    }else{
      this.resetFlag = false;
      this.sourceLoading = true;
      this.http.post(`${this.API_URL}/test/source`, JSON.stringify(this.source), this.httpOptions).map(data => data).catch(e =>{
        this.sourceStatus = false;
        this.sourceLoading = false;
        alert("Invalid Details Found.")
        this.resetFlag = true;
        return new EmptyObservable<Response>();
      }).subscribe(result => {
        this.sourceStatus = (result as any).status
        this.sourceLoading = false;
        this.resetFlag = true;
      })
    }
  }

  deleteSource(source: SourceRow) {
    this.http.post(`${this.API_URL}/delete/source`, JSON.stringify(source), this.httpOptions).subscribe(data => {
      this.source = new SourceRow();
      this.sources = this.sources.filter(obj => obj !== source);
    });
  }

  ngOnInit() {
    this.getSources();
  }

}
