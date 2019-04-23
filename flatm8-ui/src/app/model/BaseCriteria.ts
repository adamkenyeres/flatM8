export class BaseCriteria {
  genderCriteria: string;
  lifestyleCriteria: string;
  ageCriteria: number;
  ageOffset: number;
  roomTypeCriteria: string;

  constructor (private ageOffsetDefault: number) {
    this.ageOffset = ageOffsetDefault;
  }
}
