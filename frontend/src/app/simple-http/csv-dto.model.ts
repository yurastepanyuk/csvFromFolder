export class CsvDto {

  constructor(
    public name: string,
    public columns: number,
    public rows: number,
    public emptys: number,
    public separator: string,
    public columnsData: string[]
  ) {
  }

// {
//   "name": "input.csv",
//   "columns": 3,
//   "rows": 3,
//   "emptys": 0,
//   "separator": ",",
//   "columnsData": [
//     {
//       "columnName": "a",
//       "type": "Integer",
//       "epmties": 0
//     },
//     {
//       "columnName": "b",
//       "type": "String",
//       "epmties": 0
//     },
//     {
//       "columnName": "d",
//       "type": "Float",
//       "epmties": 0
//     }
//     ]
// }

}
