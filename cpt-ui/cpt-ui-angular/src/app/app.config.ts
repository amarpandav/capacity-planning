import {ApplicationConfig, provideZoneChangeDetection} from '@angular/core';
import {provideRouter, withComponentInputBinding, withRouterConfig} from '@angular/router';

import {routes} from './app.routes';
import {HttpEventType, HttpHandlerFn, HttpRequest, provideHttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {provideToastr} from "ngx-toastr";
import {provideAnimations} from "@angular/platform-browser/animations";
import {DatePipe} from "@angular/common";
//import {TaskService} from "./section1/service-samples/sample1/task.service";

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes, withComponentInputBinding(), withRouterConfig({
      paramsInheritanceStrategy: 'always'
      }
    )),
    //withComponentInputBinding(): is needed for dynamic routing
    //paramsInheritanceStrategy: dynamic param values are also injected into child routes.
    provideHttpClient(),
    provideAnimations(), // required animations providers
    provideToastr(), // Toastr providers
    DatePipe,
    //provideHttpClient(withInterceptors([loggingInterceptor])),
  ]

  //providers: [TaskService, provideZoneChangeDetection({ eventCoalescing: true }), provideRouter(routes)]

  //How to inject service
  //Option 2 - not recommended - remove -> "@Injectable({providedIn: 'root'})" from "export class TaskService {"
  //and instead include that service in providers as shown  above.

  //Option 2 - not recommended - remove -> "@Injectable({providedIn: 'root'})" from "export class TaskService {"
  //and instead include that service in component under providers array
  /*for e.g.
    templateUrl: './service-sample1.component.html',
  styleUrl: './service-sample1.component.scss',
  providers: [TaskService]
    })
    export class ServiceSample1Component {
    Downside:
    1. TaskService is available only for ServiceSample1Component
    2. if ServiceSample1Component is used second time on same page then there would be in-total 2 instances of TaskService
    */
};

/*
* In case you wanna intercept incoming or outgoing requests/responses.
* You can modify request/response.
* */
function loggingInterceptor(
  request: HttpRequest<unknown>,
  next: HttpHandlerFn
) {
   const newRequest = request.clone({
     headers: request.headers.set('X-DEBUG', 'TESTING')
   });
  console.log('[Outgoing new Request]');
  console.log(newRequest);
  return next(newRequest).pipe(
    tap({
      next: event => {
        if (event.type === HttpEventType.Response) {
          console.log('[Incoming Response]');
          console.log(event.status);
          console.log(event.body);
        }
      }
    })
  );
}

