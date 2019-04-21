import {BaseRequest} from "./BaseRequest";
import {Flat} from "./Flat";

export class DeleteFlatRequest extends BaseRequest {
  flatToDelete: Flat;
}
