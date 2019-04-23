export class Error {
  private message: string;

  constructor(private message: string) {
    this.message = message;
  }

  getMessage() {
    return this.message;
  }
}
