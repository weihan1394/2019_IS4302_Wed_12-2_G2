import { AuthenticationService } from '../_services/authentication.service';
import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
@Injectable({
    providedIn: 'root'
})
export class JwtInterceptor {
    constructor(private authenticationService: AuthenticationService) {

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let currentUser = this.authenticationService.currentUserValue;
        if (currentUser && currentUser.token) {
            req = req.clone({
                setHeaders: {
                    Authorization: `Bearer ${currentUser.token}`
                }
            });
        }
        return next.handle(req);
    }
}
