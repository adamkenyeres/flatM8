import {FlatMateEntry} from "./FlatMateEntry";
import {User} from "./User";
import {BaseRequest} from "./BaseRequest";
import {Flat} from "./Flat";

export class DeleteMateRequest extends BaseRequest {
  mateToDelete: User;
  flat: Flat;
}
