import { Component, OnInit } from '@angular/core';
import {User} from "../../model/User";
import {AppService} from "../../service/AppService";
import {FlatMateEntry} from "../../model/FlatMateEntry";
import {ChatMessage} from "../../model/ChatMessage";
import {ChatService} from "../../service/ChatService";
import {ChatContact} from "../../model/ChatContact";
import {DomSanitizer} from "@angular/platform-browser";
import {Router} from "@angular/router";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css']
})
export class ChatComponent implements OnInit {

  constructor(private app: AppService, private chatService: ChatService,
              private sanitizer: DomSanitizer, private router: Router) { }

  loggedInUser: User;
  contacts: Array<ChatContact>;
  selectedConvo: ChatContact;
  currentMessages: Array<ChatMessage> = [];
  avatars: Map<string, string> = new Map<string, string>();
  imageType = "data:image/JPEG;base64,";

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
      this.avatars = new Map();
      for (let msg of <ChatMessage[]>msgs) {
        this.app.getUserByEmail(msg.sender.email).subscribe(refreshedUser => {
          this.app.getUserAvatarByUser(refreshedUser).subscribe(avatar => {
            this.avatars.set(msg.sender.email, avatar["content"]);
          });
        });
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

  sendMsg(msg, msgBox: HTMLInputElement) {
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
    msgBox.value = "";
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

  getAvatar(user) {
    return this.sanitizer.bypassSecurityTrustUrl(this.imageType + this.avatars.get(user['email']));
  }

  slashnRedirect(email: string) {
    this.router.navigateByUrl('slashn?email='+email);
  }
}
