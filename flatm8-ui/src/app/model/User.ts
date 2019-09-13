import {FlatMateEntry} from "./FlatMateEntry";
import {ChatContact} from "./ChatContact";

export class User {
  password: string;
  email: string;
  userName: string;
  firstName: string;
  lastName: string;
  tenantType: string;
  tenantTypeComment: string;
  tenantStayType: string;
  tenantStayTypeComment: string;
  age: number;
  roles = [];
  avatarPath: string;
  contacts: Array<ChatContact>;
}

