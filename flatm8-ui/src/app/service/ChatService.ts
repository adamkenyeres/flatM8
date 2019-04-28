import {Injectable} from "@angular/core";
import {HttpClient} from "@angular/common/http";
import {User} from "../model/User";
import {ChatMessage} from "../model/ChatMessage";

@Injectable()
export class ChatService {
  constructor(private http: HttpClient) {
  }

  getMessagesBySender(sender: User) {
    return this.http.post("http://localhost:8080/chatMessages/getAllForSender/", sender);
  }

  getMessagesByReceiver(rec: User) {
    return this.http.post("http://localhost:8080/chatMessages/getAllForReciever/", rec);
  }

  createMsg(msg: ChatMessage) {
    return this.http.post("http://localhost:8080/chatMessages/", msg);
  }
}
