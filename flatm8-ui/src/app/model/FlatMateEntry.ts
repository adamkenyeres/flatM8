import {Flat} from "./Flat";
import {User} from "./User";
import {RoomCriteria} from "./RoomCriteria";

export class FlatMateEntry {
  id: string;
  flat: Flat;
  roomCriteria: RoomCriteria;
  photos: Array<string> = [];
}
