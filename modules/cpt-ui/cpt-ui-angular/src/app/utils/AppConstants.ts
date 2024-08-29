/**
 * App constants which can be references generally
 * from the application, we do not use IOC for that
 * since we are in typescript anyway
 */
export class AppConstants {

  static monthNames = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];

  //read more http://momentjs.com/docs/#/displaying/as-javascript-date/

  static TIMEZONE = "Europe/Zurich";

  static LOCALE = "de-CH";

  //By default ts supports ISO date and converting non-ISO string date to Date object in ts is extremely difficult and hence not recommended.
  //Lets always us ISO 8601 https://www.w3.org/TR/NOTE-datetime

  static ISO_DATE_FORMAT = 'YYYY.MM.DD' //2024-08-13


  //this date format MUST be equal to the server side setting as well (see AppConfig.java)
  /*static DATE_FORMAT = 'dd.MM.YYYY'//"DD.MM.YYYY";

  //this date format MUST be equal to the server side setting as well (see AppConfig.java)
  static DATE_TIME_FORMAT = "dd.MM.YYYY H:mm Z";//"DD.MM.YYYY H:mm Z";

  //this display format to show date and time without the timezone information
  static DATE_TIME_FORMAT_REDUCED = "dd.MM.YYYY HH:mm";//"DD.MM.YYYY HH:mm";


  static TIME_FORMAT = "HH:mm";//HH:mm:ss
*/


}
