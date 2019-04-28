import { Component, OnInit } from '@angular/core';
import {User} from "../../model/User";
import {AppService} from "../../service/AppService";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {ChatMessage} from "../../model/ChatMessage";
import {ChatService} from "../../service/ChatService";
import {ChatContact} from "../../model/ChatContact";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(private app: AppService, private chatService: ChatService) { }

  loggedInUser: User;
  contacts: Array<ChatContact>;
  selectedConvo: ChatContact;
  currentMessages: Array<ChatMessage> = [];

  ngOnInit() {
    this.app.getUserLoggedInUser().subscribe(resp => {
      this.app.getUserByEmail(resp["name"]).subscribe(userResp => {
        this.loggedInUser = <User>userResp;
        this.contacts = this.loggedInUser.contacts;
      });
    });
  }

  refreshMessages() {
    this.currentMessages = [];
    this.app.getUserLoggedInUser().subscribe(resp => {
      this.app.getUserByEmail(resp["name"]).subscribe(userResp => {
        this.loggedInUser = <User>userResp;
        this.contacts = this.loggedInUser.contacts;
        console.log(this.loggedInUser);
      });
    });
    this.chatService.getMessagesByReceiver(this.loggedInUser).subscribe(msgs => {
      for (let msg of <ChatMessage[]>msgs) {
        if (msg.chatContact.contactEntry.id == this.selectedConvo.contactEntry.id &&
          msg.receivers.filter(u => u.email === this.selectedConvo.senderEmail).length > 0) {
          this.currentMessages.push(msg);
        }
      }
    });

    this.currentMessages.sort((msg1, msg2) => msg1.timestamp > msg2.timestamp ? -1 : 1);
  }

  selectConvo(c: ChatContact) {
    this.selectedConvo = c;
    this.refreshMessages();
  }

  sendMsg(msg) {
    let msgObj = this.assembleMsg(msg);
    let array = [];

    this.app.getUserByEmail(this.selectedConvo.senderEmail).subscribe(user => {
      for (let u of this.selectedConvo.contactEntry.flat.flatMates) {
        array.push(u);
      }
      array.push(user);
      msgObj.receivers = array;
      console.log(msgObj);
      this.chatService.createMsg(msgObj).subscribe(resp => {
        this.refreshMessages();
      }, err => {

      });
    });
  }

  assembleMsg(msgText) {
    let msg = new ChatMessage();
    let contact = new ChatContact();
    contact.senderEmail = this.selectedConvo.senderEmail;
    contact.contactEntry = this.selectedConvo.contactEntry;

    msg.chatContact = contact;
    msg.sender = this.loggedInUser;
    msg.timestamp = new Date(Date.now());
    msg.message = msgText;

    return msg;
  }
}
