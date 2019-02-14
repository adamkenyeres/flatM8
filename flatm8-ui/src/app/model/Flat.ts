import {Address} from "./Address";

export class Flat {
  id: string;
  address: Address;
  flatType: string;
  roomCount: number;
  userEmail: string;
  additionalDetails = [];
}
