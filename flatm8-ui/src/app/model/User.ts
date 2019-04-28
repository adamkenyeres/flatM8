import {FlatMateEntry} from "./FlatMateEntry";
import {ChatContact} from "./ChatContact";

export class User {
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  tenantType: string;
  tenantTypeComment: string;
  tenantStayType: string;
  tenantStayTypeComment: string;
  age: number;
  roles = [];
  contacts: Array<ChatContact> = [];
}

