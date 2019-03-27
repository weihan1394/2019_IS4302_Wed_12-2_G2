import { AuthenticationService } from '../_services/authentication.service';
import { Injectable } from '@angular/core';
import { Router, ActivatedRouteSnapshot, RouterStateSnapshot, CanActivate, NavigationEnd } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  currentUrl: string;
  previousUrl: string;
  constructor(private authenticationService: AuthenticationService, private router: Router) {
    router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        this.previousUrl = this.currentUrl;
        this.currentUrl = event.url;
        console.error(this.previousUrl);
        console.error(this.currentUrl);
      };
    });
  }

  canActivateChild(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    return this.canActivate(route, state);
  }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    const currentUser = this.authenticationService.currentUserValue;
    if (!currentUser) {
      // not logged in so redirect to login page with the return url
      this.router.navigate(['/login'], { queryParams: { returnUrl: state.url } });
      return false;
    } else {
      if(state.url == "/") {
        if(currentUser.role.toString() == "FARMER") {
          this.router.navigate(['/farmer']);
        } else if(currentUser.role.toString() == "PRODUCER") {
          this.router.navigate(['/producer']);
        } else if(currentUser.role.toString() == "DISTRIBUTOR") {
          this.router.navigate(['/distributor']);
        } else {
          this.router.navigate(['/retailer']);
        }
        return true;
      } 
      return this.checkRights(currentUser, state);
    }

  }

  checkRights(currentUser, state): boolean {
    let path = state.url.toString();
    path = path.substring(1);
    let userRole = currentUser.role;
    if(path.toUpperCase().includes(userRole.toUpperCase())) {
      return true;
    } else {
      this.router.navigate(['/PageNotFound']);
      return false;
    }
  }
}


