export class Login {
  login: String;
  password: String;
  role?: String;

  constructor(login: string, password: string, role: String) {
    this.login = login;
    this.password = password;
    this.role = role;
  }
}
