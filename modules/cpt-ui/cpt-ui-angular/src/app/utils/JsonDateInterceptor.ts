import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {DateUtils} from "./DateUtils";

//@Injectable({providedIn: 'root'})
export class JsonDateInterceptor implements HttpInterceptor {


    //private static _isoDateFormat1 = /^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d*)?Z$/;
    private static _isoDateFormat2 = /^\d{4}-\d{2}-\d{2}$/;

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        return next.handle(req).pipe(map((val: HttpEvent<any>) => {
            if (val instanceof HttpResponse) {
                //const body = val.body;
                //this.convert(body);
            }
            return val;
        }));
    }


    public static isIsoDateString(value: any): boolean {
        //console.log("JsonDateInterceptor got called...:" + value);
        if (value === null || value === undefined) {
            return false;
        }
        if (typeof value === 'string') {
            return JsonDateInterceptor._isoDateFormat2.test(value);
        }
        return false;
    }

    public static convert(body: any) {

        if (body === null || body === undefined) {
            return body;
        }
        if (typeof body !== 'object') {
            return body;
        }
        for (const key of Object.keys(body)) {
            const value = body[key];
            if (JsonDateInterceptor.isIsoDateString(value)) {

                //let date = new Date(value);
                let date = DateUtils.newDate(value);
                //date.setHours(0 ,0, 0, 0);
                body[key] = date;
            } else if (typeof value === 'object') {
                JsonDateInterceptor.convert(value);
            }
        }
    }
}