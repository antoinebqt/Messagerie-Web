import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from "@angular/core";

@Injectable()
export class RequestHelper {
  private static MESSAGE_PUBLISHER_BASE_URL = "https://polysnap-399912.oa.r.appspot.com/api/message-published";
  private static MESSAGE_RETRIEVER_BASE_URL = "https://app-21cff21c-1655-402a-9cee-e79086dddb81.cleverapps.io/api/message-retrieved";

  //private static MESSAGE_PUBLISHER_BASE_URL = "http://localhost:3001/api/message-published";
  //private static MESSAGE_RETRIEVER_BASE_URL = "http://localhost:3002/api/message-retrieved";

  constructor(private http: HttpClient) { }

  public createUser(username: string): Observable<any> {
    return this.postData(username, RequestHelper.MESSAGE_PUBLISHER_BASE_URL + '/create-user');
  }

  public userExists(username: string): Observable<any> {
    return this.postData(username, RequestHelper.MESSAGE_RETRIEVER_BASE_URL + '/user-exists');
  }

  public loginUser(username: string): Observable<any> {
    return this.postData(username, RequestHelper.MESSAGE_RETRIEVER_BASE_URL + '/login-user');
  }

  public getAllUsers(): Observable<any> {
    return this.getData(RequestHelper.MESSAGE_RETRIEVER_BASE_URL + '/all-users');
  }

  public getUpdatedChat(chatId: number, userId: number, lastMessageCreationDate: null | Date,): Observable<any> {
    return this.postData({chatId: chatId, userId: userId, lastMessageCreationDate: lastMessageCreationDate}, RequestHelper.MESSAGE_RETRIEVER_BASE_URL + '/update-chat');
  }

  public sendMessage(message: string, userId: string, chatId: string, timer: {value: number, unit: string}): Observable<any> {
    let time = 10;
    if (timer !== null && timer !== undefined) {
      time = timer.value;
      switch (timer.unit) {
        case "mn": time *= 60; break;
        case "h": time *= 3600; break;
      }
    }
    let data = {
      message: message,
      userId: userId,
      chatId: chatId,
      lifespan: time,
    }
    console.log(data);
    console.log(RequestHelper.MESSAGE_PUBLISHER_BASE_URL + '/send-message')
    return this.postData(data, RequestHelper.MESSAGE_PUBLISHER_BASE_URL + '/send-message');
  }

  public resetDB(){
    console.log(RequestHelper.MESSAGE_RETRIEVER_BASE_URL + '/reset-db')
    this.http.post(RequestHelper.MESSAGE_RETRIEVER_BASE_URL + '/reset-db', {}).subscribe(
      (response) => {
        console.log('Réponse du serveur : ', response);
      },
      (error) => {
        console.error('Erreur : ', error);
      }
    );
  }

  getChat(id: string, id2: string) {
    let data = {
      userId1: id,
      userId2: id2
    }
    return this.postData(data, RequestHelper.MESSAGE_RETRIEVER_BASE_URL + '/get-chat');
  }

  createChat(id: string, id2: string) {
    let data = {
      userId1: id,
      userId2: id2
    }
    return this.postData(data, RequestHelper.MESSAGE_PUBLISHER_BASE_URL + '/create-chat');
  }

  private postData(data: any, url: any): Observable<any> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    return this.http.post(url, data, { headers });
  }

  private getData(url: any): Observable<any> {
    return this.http.get(url);
  }
}
