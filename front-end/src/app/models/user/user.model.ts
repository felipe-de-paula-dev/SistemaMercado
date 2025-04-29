export class User {
  id?: string;
  login: string;
  password: string;
  role: string | null;

  constructor(id: string, login: string, password: string, role: string) {
    this.id = id;
    this.login = login;
    this.password = password;
    this.role = role;
  }
}
