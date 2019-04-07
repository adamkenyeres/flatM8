import {Address} from "./Address";

export class Flat {
  id: string;
  address: Address;
  flatType: string;
  roomCount: number;
  capacity: number;
  userEmail: string;
  additionalDetails = [];
  flatMates = [];
}
