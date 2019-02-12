export class User {
  password: string;
  passwordConfirm: string;
  email: string;
  firstName: string;
  lastName: string;
  tenantType: string;
  tenantTypeComment: string;
  tenantStayType: string;
  tenantStayTypeComment: string;
  age: number;
  additionalDetails = [];
  roles = [];
}

