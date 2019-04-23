import {BaseCriteria} from "./BaseCriteria";

export class RoomCriteria {
  criteria: BaseCriteria;
  capacity: number;
  size: number;
  additionalDetails: Array<String> = [];
}
