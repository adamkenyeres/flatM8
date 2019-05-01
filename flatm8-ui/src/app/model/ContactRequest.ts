import {FlatMateEntry} from "./FlatMateEntry";
import {User} from "./User";
import {BaseRequest} from "./BaseRequest";

export class ContactRequest extends BaseRequest {
  entry: FlatMateEntry;
}
