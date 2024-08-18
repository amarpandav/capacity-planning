import {CanMatchFn, RedirectCommand, Router, Routes} from '@angular/router';
import {inject} from "@angular/core";
import {HomeComponent} from "./home/home.component";
import {UnauthorizedComponent} from "./unauthorized/unauthorized.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {HelpMeComponent} from "./help-me/help-me.component";


const isAuthorized: CanMatchFn = (route, segments) => {
    const router = inject(Router);

    /*const authorized = Math.random();
    console.log("authorized:"+authorized)
    if (authorized < 0.5) {
      return true;
    }*/
    //TODO: check if user is authorized
    const authorized = true;
    if (authorized) {
        return true;
    }
    return new RedirectCommand(router.parseUrl('/unauthorized'))
}

export const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        canMatch: [isAuthorized]
    },
    {
        path: 'help',
        component: HelpMeComponent
    },
    {
        path: 'unauthorized',
        component: UnauthorizedComponent,
        canMatch: []
    },
    {
        path: "**",
        component: PageNotFoundComponent
    }
];


