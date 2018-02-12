
import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';

@Injectable()
export class SimpleHttpServiceService {

  constructor(private http: Http) { }

  // Гет запрос и маппинг респонсов
  getData(folder: string): Observable<any[]>  {
    const people$ = this.http.get('http://localhost:8080/showcsv/' + folder).map((response: Response) => {
      return this.mapPersons(response);
    } );

    return people$;

  }
// РАспаковка респонсов и маппинг объектов
  mapPersons(response: Response): any[] {
    if (response.json().message){
      console.log("response.json()", response.json());
      const errorObj: any[] = [response.json()];
      console.log("Array.from(response.json())", errorObj);
      return errorObj;
    }
    // The response of the API has a results
    // property with the actual results
    return response.json().map(this.toObject);
  }
// Преобразование объекта json в объект класса
  toObject(item: any): any {

    const object = {
      name: item.name,
      columns: item.columns,
      rows: item.rows,
      emptys: item.emptys,
      separator: item.separator,
      columnsData: item.columnsData.map((curObj) => {
        return {
          columnName: curObj.columnName,
          type: curObj.type,
          epmties: curObj.epmties
        }
      })
    };
    console.log('Parsed object:', object);
    return object;
  }

  toObjectTwo(item: any): any {
    const object = {
      columnName: item.columnName,
      type: item.type,
      epmties: item.epmties
      }
    console.log('Parsed object two:', object);
    return object;
  }
}
