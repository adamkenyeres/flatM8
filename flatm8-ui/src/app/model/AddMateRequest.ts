import {BaseRequest} from "./BaseRequest";
import {User} from "./User";
import {FlatMateEntry} from "./FlatMateEntry";
import {Flat} from "./Flat";

export class AddMateRequest extends BaseRequest {
  mateToAdd: User;
  flat: Flat;
}
