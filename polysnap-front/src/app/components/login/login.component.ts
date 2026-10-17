import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {RequestHelper} from "../../services/RequestHelper";
import {NotificationService} from "../../services/notification.service";

@Component({
  selector: 'app-idle',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  username: string = '';
  loginMode: boolean = true;
  isLoading: boolean = false;

  constructor(private router: Router, private requestHelper: RequestHelper, private notificationService: NotificationService) {}

  ngOnInit() {
    clearInterval();
  }

  login(){
    if (this.username === '') {
      this.notificationService.notifyError('Please enter an username');
      return;
    }

    this.isLoading = true;
    this.requestHelper.loginUser(this.username).subscribe((data: any) => {
      this.isLoading = false;
      const user = {
        id: data.id,
        username: data.username,
      };
      console.log("User found", user);
      this.notificationService.notifyInfo('Welcome back ' + user.username + ' !');
      localStorage.setItem('user', JSON.stringify(user));
      this.router.navigate(['/home']);
    }, (error) => {
      this.isLoading = false;
      this.notificationService.notifyError('User not found');
    });
  }

  register(){
    if (this.username === '') {
      this.notificationService.notifyError('Please enter an username');
      return;
    }

    this.isLoading = true;
    this.requestHelper.userExists(this.username).subscribe((data: any) => {
      console.log("data", data);
      if (data === true) {
        this.notificationService.notifyError('User already exists');
        this.isLoading = false;
        return;
      } else {
        this.requestHelper.createUser(this.username).subscribe((data: any) => {
          this.isLoading = false;
          const user = {
            id: data.id,
            username: data.username,
          };
          console.log("User created", user);
          this.notificationService.notifyInfo('User created successfully');
          this.username = '';
          this.loginMode = true;
        });
      }
    }, (error) => {
        this.isLoading = false;
        this.notificationService.notifyError(error);
    });
  }
}
