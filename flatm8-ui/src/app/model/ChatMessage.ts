import {User} from "./User";
import {FlatMateEntry} from "./FlatMateEntry";

export class ChatMessage {
  message: string;
  sender: User;
  entry: FlatMateEntry;
  timestamp: Date;
  receivers: Array<User> = [];
}
