import { AuthenticationService } from './../_services/authentication.service';
import { Router, RouterStateSnapshot } from '@angular/router';
import { DataService } from './../_services/data.service';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss']
})
export class LayoutComponent implements OnInit {

  title: string;
  displayTitle: boolean;
  constructor(private dataService: DataService, private router: Router, private authenticationService:AuthenticationService) { }

  ngOnInit() {
    this.displayTitle = false;
  }

  ngAfterViewInit() {
    this.dataService.currentTitle.subscribe(title => setTimeout(() => {
      this.title = title;
      this.displayTitle = (this.title == '') ? false : true;
    }))
  }

  clickSignOut() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

  back() {
    let route = this.router.url;
    let routeSplit = route.split("/");
    console.log(routeSplit)
    // route = 
    this.router.navigate(["/" + routeSplit[1]]);
  }
}
