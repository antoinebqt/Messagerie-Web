import {Component, OnInit, EventEmitter, Output, Input} from '@angular/core';
import {RequestHelper} from "../../services/RequestHelper";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  @Input() username: string = "";
  users : any = [];

  userToChat: any | undefined;

  @Output() usernameEvent: EventEmitter<string> = new EventEmitter<string>();

  constructor(private requestHelper: RequestHelper) { }

  ngOnInit(): void {
    this.requestHelper.getAllUsers().subscribe((data: any) => {
      this.users = data;
    });
  }

  emitUsernameEvent() {
    this.username = this.userToChat.username;
    this.usernameEvent.emit(this.username);
  }

  resetDB() {
    this.requestHelper.resetDB();
  }
}
