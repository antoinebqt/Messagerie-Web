import {
  Component,
  ElementRef,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import {NavigationExtras, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {RequestHelper} from "../../services/RequestHelper";
import {NotificationService} from "../../services/notification.service";

interface Message {
  id: number;
  snapUserId: number;
  messageBody: string;
  lifespan: number;
  readTime: Date;
  creationDate: Date;
}

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit,OnChanges {

  @ViewChild('scrollingDiv') private scrollingDiv: ElementRef | undefined;

  messages: Message[] = [];
  newMessage: string = '';

  showLifespanBubble: boolean = false;
  selectedMessage: number | undefined;

  @Input() devMode:boolean=false;

  @Input() username: string = "";

  @Input() user: any = null;

  @Output() devModeEvent: EventEmitter<boolean> = new EventEmitter<boolean>();

  urlFileHandler: string = 'https://app-7576e78e-9a44-44e3-bdeb-6751cc07939f.cleverapps.io';

  fileToSend: any;

  fileSelected: any;

  isLoading: boolean = false;

  messageIsFile: boolean = false;

  messageIsLocation: boolean = false;

  location: any;


  //urlFileHandler: string = 'http://localhost:8080';

  userToChat: any | undefined;

  users=[ {id: 0, username: ""} ]

  timersAvailable = [{value: 10, unit: "s"}, {value: 1, unit: "mn"}, {value: 10, unit: "mn"},
    {value: 30, unit: "mn"}, {value: 1, unit: "h"}, {value: -1, unit: "Never"}];

  timer: any = this.timersAvailable[0];

  chat: any = null;


  updateInterval: NodeJS.Timeout | undefined;

  videoPath: string= "https://cellar-c2.services.clever-cloud.com/polysnap/production_id_4328789 (2160p).mp4";
  autoScrollActivated: boolean=true;
  locationToDisplay: any=[];


  constructor(private router: Router,
              private http: HttpClient,
              private requestHelper: RequestHelper,
              private notificationService: NotificationService) {}

  ngOnChanges(changes: SimpleChanges): void {
    this.scrollToBottom();
  }

  ngOnInit(): void {
    this.requestHelper.getAllUsers().subscribe((data: any) => {
      this.users = data.filter((user: { id: any; }) => user.id !== this.user.id);
    });

    this.updateInterval = setInterval(() => {
      this.updateChat();
    }, 5000);
  }

  chooseUserToChat() {
    console.log("userToChat", this.userToChat)
    this.isLoading = true;
    this.requestHelper.getChat(this.user.id, this.userToChat.id).subscribe((data: any) => {
      this.isLoading = false;
      this.chat = data;
      console.log("chat", this.chat);
      this.messages = data.messageList;
      console.log("messages", this.messages);
      setTimeout(() => {
        this.scrollToBottom();
      }, 500);
    }, (error: any) => {
      console.log("creating chat");
      this.requestHelper.createChat(this.user.id, this.userToChat.id).subscribe((data: any) => {
        this.isLoading = false;
        this.chat = data;
        console.log("chat", this.chat);
        this.messages = [];
        console.log("messages", this.messages);
      });
    });
  }

  updateChat() {
    console.log("TEMPS: " + new Date())
    if (this.chat != null) {
      let lastMessageCreationDate: null | Date = this.messages.length === 0 ? null : this.messages[this.messages.length - 1].creationDate;

      console.log("lastMessageCreationDate : ", lastMessageCreationDate)
      this.requestHelper.getUpdatedChat(this.chat.id, this.user.id, lastMessageCreationDate).subscribe((data: any) => {
        let newMessagesToAdd: Message[] = data;
        console.log("newMessagesToAdd : ", newMessagesToAdd)
        // for (let i = 0; i < this.messages.length; i++) {
        //   if (this.messages[i].readTime != null
        //       && this.messages[i].readTime != undefined
        //       && (new Date(this.messages[i].creationDate).getTime() + this.messages[i].lifespan * 1000) < (new Date().getTime() - 7200000)) {
        //     this.messages.splice(i, 1);
        //     i--;
        //   }
        // }
        newMessagesToAdd.forEach((incomingMessage: Message) => {
          this.messages.push(incomingMessage);
        });
        this.messages.sort((a: any, b: any) => a.creationDate - b.creationDate);
      });
    }
    setTimeout(() => {
      this.scrollToBottom();
    }, 500);
  }

  async sendMessage() {
    let messageToSend= this.newMessage;
    if (this.messageIsLocation){
      messageToSend = this.location;
      console.log("location", messageToSend);
    }

    if (this.messageIsFile) {
      await this.uploadImage();
      this.cancelMessage();
    } else if (messageToSend.trim() !== '') {
      this.requestHelper.sendMessage(messageToSend, this.user.id, this.chat.id, this.timer).subscribe(
        (data: any) => {
          this.updateChat();
        }
      );
      setTimeout(() => {
        this.updateChat();
      }, 1000);
      console.log("messages", this.messages);
      this.cancelMessage();
      this.newMessage = '';
    }
  }

  redirectToLogin() {
    clearInterval(this.updateInterval);
    const dataToSend = {
      myString: this.username
    };
    const navigationExtras: NavigationExtras = {
      queryParams: dataToSend
    };
    this.router.navigate(['/login'], navigationExtras);
  }


  changeDevMode() {
    this.devModeEvent.emit();
  }

  onFileChange(event: any) {
    const imageFile = event.target.files[0];

    // Check if the file size exceeds the limit (for example, 5 MB)
    const maxSizeInBytes = 50 * 1024 * 1024; // 5 MB
    if (imageFile.size > maxSizeInBytes) {
      // File is too large, create a notification here
      this.notificationService.notifyError('File is too large. Please choose a smaller file');
      this.cancelMessage();
      return;
    }
    console.log(imageFile + " " + imageFile.name)
    const formData = new FormData();
    formData.append('image', imageFile,imageFile.name);
    formData.append('userId', this.user.id.toString());
    formData.append('chatId', this.chat.id.toString());
    this.fileToSend=formData;
    this.messageIsFile=true;
  }

  async uploadImage() {
    let time = 10;
    if (this.timer !== null && this.timer !== undefined) {
      time = this.timer.value;
      switch (this.timer.unit) {
        case "mn": time *= 60; break;
        case "h": time *= 3600; break;
      }
    }
    if (this.fileToSend) {
      console.log(time)
      this.fileToSend.append('lifespan', time);
      try {
        console.log('Uploading image' + this.fileToSend)
        const response = await this.http.post(this.urlFileHandler + '/upload',this.fileToSend).toPromise();
        console.log('Image uploaded successfully', response);
      } catch (error) {
        console.error('Error uploading image', error);
      }
    }
  }

  // Cette fonction gère le clic sur le message
  onClickMessage(messageId: number) {
    console.log("messageId", messageId);
    if(this.selectedMessage === messageId) {
      this.showLifespanBubble = !this.showLifespanBubble;
    }
    this.selectedMessage = messageId;
  }

  isMessageImage(message: string): boolean {
    if (!message) return false;
    return message.match(/\.(jpeg|jpg|gif|png|PNG|JPEG|GIF|JPG)$/) !== null;
  }

  isMessageAudio(message: string): boolean {
    if (!message) return false;
    return message.match(/\.(mp3)$/) !== null;
  }

  isMessageVideo(message: string): boolean {
    if (!message) return false;
    return message.match(/\.(mp4)$/) !== null;
  }

  isMessagePos(message: string): boolean {
    if (!message) return false;
    const regex = /%pos%/;
    return regex.test(message);
  }


  getCurrentLocation(): Promise<GeolocationPosition> {
    return new Promise((resolve, reject) => {
      if ('geolocation' in navigator) {
        navigator.geolocation.getCurrentPosition(
          position => resolve(position),
          error => reject(error)
        );
      } else {
        reject(new Error('Geolocation is not available.'));
      }
    });
  }

  sendLocation(): void {
    console.log('Getting location...')
    this.getCurrentLocation()
      .then(position => {
        this.messageIsLocation=true;
        this.location= '%pos%' + position.coords.latitude + '%pos%' + position.coords.longitude;
        console.log(this.location);
      })
      .catch(error => {
        console.error('Error getting location:', error.message);
      });
  }

  getPosition(message:string):void{
    let regex = /%pos%/;
    let position = message.split(regex);
    //change the lat and long to int
    let lat=parseFloat(position[1]);
    let long=parseFloat(position[2]);
    this.locationToDisplay=[long,lat];
  }


  getUrl(message:string): string {
    // Check if the URL has a prefix, if not, prepend 'http://'
    if (message && !message.startsWith('http')&& !message.startsWith('https') && !message.startsWith('//')) {
      return '//' + message;
    }
    return message;
  }


  cancelMessage() {
    console.log("reset input")
    this.messageIsFile=false;
    this.fileToSend=null;
    this.fileSelected=null;
    this.messageIsLocation=false;
    this.location=null;
  }

  scrollToBottom(): void {
    console.log("scroll")
    if (this.autoScrollActivated && this.scrollingDiv)
    {
      const scrollContainer = this.scrollingDiv.nativeElement;
      // Scroll to the bottom of the div
      scrollContainer.scroll({
        top: scrollContainer.scrollHeight,
        left: 0,
        behavior: 'smooth'
      });
    }
  }
}
