import {User} from "./User";

export class BaseRequest {
  id: string;
  requestStatus: string;
  requestType: string;
  receivers: Array<User> = [];
  sender: User;
  rejecters: Array<User>;
  approvers: Array<User>;
}
