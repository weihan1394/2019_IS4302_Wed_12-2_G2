import { User } from './../_model/user';
import { Observable, BehaviorSubject } from 'rxjs';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { map, catchError } from 'rxjs/operators';
import { throwError } from "rxjs";
import { default as decode } from 'jwt-decode'

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/x-www-form-urlencoded' })
};
@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  baseUrl = "/api/";
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private httpClient: HttpClient) {
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem('currentUser')));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(email: string, password: string): Observable<any> {
    let body = new URLSearchParams();
    body.set("email", email);
    body.set("password", password);
    return this.httpClient.post<any>(this.baseUrl + "Login", body.toString(), httpOptions).pipe(
      map(rsp => {
        const tokenPayLoad = decode(rsp.success);
        let user:User = tokenPayLoad.LoggedInUser;
        user.token = rsp;
        if (user && tokenPayLoad) {
          localStorage.setItem('currentUser', JSON.stringify(user));
          this.currentUserSubject.next(user);
        }

        return user;
      }), catchError(this.handleError)
    )
  }

  logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('currentUser');
    this.currentUserSubject.next(null);
  }

  private handleError(error: HttpErrorResponse) {
    console.log(error);
    // if (error.error instanceof ErrorEvent) {
    //   console.error("An unknown error has occurred:", error.error.message);
    // } else {
    //   console.error(
    //     " A HTTP error has occurred: " +
    //     `HTTP ${error.status}: ${error.error.message}`
    //   );
    // }

    return throwError(error);
  }
}
