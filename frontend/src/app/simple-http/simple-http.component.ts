import { Component, OnInit } from '@angular/core';
//импорт http библиотеки
import {Http, Response} from '@angular/http';
import {Observable} from "rxjs";
import {SimpleHttpServiceService} from "../simple-http-service.service";

@Component({
  selector: 'app-simple-http',
  templateUrl: './simple-http.component.html',
  styleUrls: ['./simple-http.component.css']
})
export class SimpleHttpComponent implements OnInit {

  data: Object;
  loading: boolean;
  //инжект Http в переменную http
  constructor(private http: Http, private httpServ: SimpleHttpServiceService) { }

  ngOnInit() {
  }

    makeRequest():void{
      this.loading = true;
    this.http.request('http://localhost:8080/showcsv/folder')
      .subscribe((res: Response) => {
        this.data = res.json();
        this.loading = false;
      });
  }
  makeRequestTwo(folder: string):void{
    this.loading = true;
    this.http.request('http://localhost:8080/showcsv/' + folder)
      .subscribe((res: Response) => {
        this.data = res.json();
        this.loading = false;
      });
  }



}
