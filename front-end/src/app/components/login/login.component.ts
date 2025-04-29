import { Component, EventEmitter, Output } from '@angular/core';
import { LucideAngularModule, Mail, Lock, User } from 'lucide-angular';
import { SwalService } from '../../services/swal/swal-service.service';
import { LoginService } from '../../services/login/login.service';
import { FormsModule } from '@angular/forms';
import { SessionStorageService } from '../../services/sessionStorage/session-storage.service';
import { TokenService } from '../../services/token/token.service';

@Component({
  selector: 'app-login',
  imports: [LucideAngularModule, FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  readonly userIcon = User;
  readonly passIcon = Lock;

  constructor(
    private swalAlert: SwalService,
    private loginService: LoginService,
    private sessionStorage: SessionStorageService,
    private tokenService: TokenService,
  ) {}

  @Output() LiberarAcesso = new EventEmitter<void>();

  login = {
    login: '',
    password: '',
  };

  token: string = '';

  ngOnInit(): void {
    this.tokenService.validarToken().subscribe({
      next: () => {
        this.LiberarAcesso.emit();
      },
      error: (err) => {
        console.warn('Token inválido ou erro na validação', err);
      },
    });
  }

  entrar() {
    this.loginService.postLogin(this.login).subscribe({
      next: (res) => {
        console.log(res);
        var caractere = this.login.login.charAt(0).toUpperCase();
        this.swalAlert.success(
          'Entrou',
          res.role[0] == 'ROLE_EMPLOYEE'
            ? 'Bem Vindo(a) Vendedor'
            : res.role[1] == 'ROLE_EMPLOYEE' && res.role[0] == 'ROLE_ADMIN'
              ? 'Bem Vindo(a) Administrador'
              : '',
        );
        this.sessionStorage.salvar('token', res.token);
        this.sessionStorage.salvar('nome', caractere);
        setTimeout(() => {
          this.LiberarAcesso.emit();
          window.location.reload();
        }, 1000);
      },
      error: (err) => {
        this.swalAlert.error('Erro', 'Não Foi Possivel Entrar');
      },
    });
  }

  setToken(token: string): void {
    sessionStorage.setItem('login', token);
  }
}
