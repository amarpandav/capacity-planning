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
  }

  public static parseISODate(dateISO?: string | null) {
    //expected format is AppConstants.ISO_DATE_FORMAT for e.g. '2024-08-13'

    if (dateISO) {
      return new Date(dateISO);
    }
    return null
  }

  public static formatToISODate(date: Date) {
    //https://dockyard.com/blog/2020/02/14/you-probably-don-t-need-moment-js-anymore
    //more info https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Intl/DateTimeFormat
    //
    //let formatted = new Intl.DateTimeFormat("en_US").format(date);
    //let formatted = new Intl.DateTimeFormat(AppConstants.LOCALE, {year:'numeric', month:'2-digit', day:'2-digit' }).format(date); 13.08.2024
     // YYYY-MM-DD 2024-08-13
    //without {day:'2-digit', month:'2-digit', year:'numeric' } -> 13.8.2024
    //with {day:'2-digit', month:'2-digit', year:'numeric' } -> 13.08.2024
    return new Intl.DateTimeFormat("fr-CA", {year: 'numeric', month: '2-digit', day: '2-digit'}).format(date);
  }

  public static currentDay(): number {
    return new Date().getDay();
  }

  public static currentMonth(): number {
    return new Date().getMonth();
  }

  public static currentYear(): number {
    return new Date().getFullYear();
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
