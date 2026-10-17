import { Component, OnInit } from '@angular/core';
import { Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  user: any = null;
  username: string = "";
  devMode:boolean=false;

  constructor(private router: Router) { }

  ngOnInit(): void {
    const token : string | null = localStorage.getItem('user');
    console.log("token", token);
    if (token == null || token == '') {
      this.redirectToLogin();
    } else {
      this.user = JSON.parse(token);
      console.log("user", this.user);
      if (this.user.username == null || this.user.id == null)
        this.redirectToLogin();
      this.username = this.user.username;
    }
  }

  handleUsernameEvent(username: string) {
    this.username = username;
  }

  handleDevModeEvent() {
    this.devMode = !this.devMode;
  }

  redirectToLogin() {
    this.router.navigate(['/login']);
  }

}
