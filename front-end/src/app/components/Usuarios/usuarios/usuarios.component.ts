import { Component } from '@angular/core';
import { User } from '../../../models/user/user.model';
import { UserService } from '../../../services/user/user.service';
import {
  IdCard,
  LockIcon,
  LucideAngularModule,
  Plus,
  Search,
  Subtitles,
  Users,
} from 'lucide-angular';
import { CommonModule } from '@angular/common';
import { SwalService } from '../../../services/swal/swal-service.service';
import { TokenService } from '../../../services/token/token.service';
import { FormsModule } from '@angular/forms';
import { CriarUsuarioComponent } from '../criar-usuario/criar-usuario.component';

@Component({
  selector: 'app-usuarios',
  standalone: true,
  imports: [
    LucideAngularModule,
    CommonModule,
    FormsModule,
    CriarUsuarioComponent,
  ],
  templateUrl: './usuarios.component.html',
  styleUrl: './usuarios.component.css',
})
export class UsuariosComponent {
  readonly UserIcon = Users;
  readonly CardIcon = IdCard;
  readonly PlusIcon = Plus;
  readonly LockedIcon = LockIcon;
  readonly SearchIcon = Search;
  readonly MaleIcon = Subtitles;

  constructor(
    private userService: UserService,
    private swalService: SwalService,
    private tokenService: TokenService,
  ) {}

  Usuarios: User[] = [];
  roles: string[] = [];

  permitir = false;

  carregarRoles() {
    this.tokenService.getRoles().subscribe((data: string[]) => {
      this.roles = data;
      this.permitir = this.roles.includes('ROLE_ADMIN');
    });
  }

  criarUsuario: boolean = false;

  abrirUsuario() {
    this.criarUsuario = true;
  }

  fecharUsuario() {
    this.criarUsuario = false;
  }

  getUsuarios() {
    this.userService.getUsers().subscribe((data) => {
      this.Usuarios = data;
    });
  }

  ngOnInit(): void {
    this.getUsuarios();
    this.carregarRoles();
  }

  async deletarUsuarios(id: string | undefined) {
    const res = await this.swalService.confirmAndWrite(
      'Você Tem Certeza?',
      'O Usuario Será Excluido.',
      'CONFIRMAR',
    );

    if (res) {
      if (id) {
        this.userService.deleteUsers(id).subscribe({
          next: (res) => {
            this.swalService.success('Sucesso', res);
            this.getUsuarios();
          },
          error: (err) => {
            this.swalService.error('Erro', err.error);
          },
        });
      }
    }
  }

  search: string = '';

  searchByName() {
    this.userService.getUsersByName(this.search).subscribe((data: User[]) => {
      this.Usuarios = data;
    });
  }
}
