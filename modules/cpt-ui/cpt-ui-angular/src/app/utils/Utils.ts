export class Utils {

  static replaceAll (param: (any )[], oldValue: any, newValue: any): (any )[] {
    return param.map( (v: number | string)=> {
      return v === oldValue ? newValue:  v;
    });
  }

  static toEnum(enumObject: any, strKey: string) {
    //e.g. TaskStatus [filteredTaskKey as keyof typeof TaskStatus]
    return enumObject [strKey as keyof typeof enumObject]
  }
  static enumToArray(enumObject: any) {
    //console.log(enumObject); //prints {
    /*"OPEN": "OPEN",
      "INPROGRESS": "IN-PROGRESS",
      "CLOSED": "CLOSED"
    }*/
    //console.log(Object.keys(enumObject));//get all keys:
    /* [
    "OPEN",
      "INPROGRESS",
      "CLOSED"
    ]*/

    /*let strings = Object.keys(enumObject)
      .filter(k => {
        console.log("key is:" + k);
        let b = !isNaN(+k);
        console.log("b is:" + b);
      });
    console.log(strings);*/

    /*return Object.keys(enumObject)
      //.filter(k => isNaN(+k)): Filter for not at number, we don't need it.
      .map(key => ({key: key, value: enumObject[key]}));*/


    return Object.keys(enumObject)
      //.map(k => ({key: k, value: enumObject[k]}));
      .map (k => {
        return {key: k, value: enumObject[k]}
      });
  }

}
