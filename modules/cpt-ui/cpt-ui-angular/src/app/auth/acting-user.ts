import {HttpHandlerFn, HttpRequest} from "@angular/common/http";
import {environment} from "../../environments/environment";

/**
 * Stand-in for real login (#16): the logged-in user is hard-coded.
 */
export const LOGGED_IN_EMPLOYEE_ID = "10000001"; //Amar Pandav

/**
 * Header the backend reads to know who is acting. Trusted as is, so it is not security; it goes away with #16.
 */
export const ACTING_EMPLOYEE_ID_HEADER = "X-Acting-Employee-Id";

/**
 * Adds the acting user's employee ID to every API request.
 */
export function actingUserInterceptor(request: HttpRequest<unknown>, next: HttpHandlerFn) {
    if (!request.url.startsWith(environment.apiUrl)) {
        return next(request);
    }
    return next(request.clone({
        headers: request.headers.set(ACTING_EMPLOYEE_ID_HEADER, LOGGED_IN_EMPLOYEE_ID)
    }));
}
