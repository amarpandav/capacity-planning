/**
 * App constants which can be references generally
 * from the application, we do not use IOC for that
 * since we are in typescript anyway
 */
export class DateUtils {

  public static isWeekend(date?: Date | null, dateAsStr?: string | null) {
    if (date) {
      //console.log(date);
      return date.getDay() === 0 || date.getDay() === 6;
    } else {
      let date1 = DateUtils.parseISODate(dateAsStr);
      //console.log(date1);
      return date1?.getDay() === 0 || date1?.getDay() === 6;
    }
    return false;
  }

  public static parseISODate(dateISO?: string | null) {
    //expected format is AppConstants.ISO_DATE_FORMAT for e.g. '2024-08-13'

    if (dateISO) {
      let date = new Date(dateISO);
      return date;
    }
    return null
  }

  public static formatToISODate(date: Date) {
    //https://dockyard.com/blog/2020/02/14/you-probably-don-t-need-moment-js-anymore
    //more info https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/DateTimeFormat
    //
    //let formatted = new Intl.DateTimeFormat("en_US").format(date);
    //let formatted = new Intl.DateTimeFormat(AppConstants.LOCALE, {year:'numeric', month:'2-digit', day:'2-digit' }).format(date); 13.08.2024
    let formatted = new Intl.DateTimeFormat("fr-CA", {year: 'numeric', month: '2-digit', day: '2-digit'}).format(date); // YYYY-MM-DD 2024-08-13

    //without {day:'2-digit', month:'2-digit', year:'numeric' } -> 13.8.2024
    //with {day:'2-digit', month:'2-digit', year:'numeric' } -> 13.08.2024
    return formatted;
  }


  /*
    public static isTodayOrPast(date: Date): boolean {
      date.is
      return moment(date).isSame(moment(new Date()), 'day') || moment(date).isBefore(moment(new Date()));
    }


    public static isTodayOrFuture(date: Date): boolean {
      return moment(date).isSame(moment(new Date()), 'day') || moment(date).isAfter(moment(new Date()));
    }
  */
}
