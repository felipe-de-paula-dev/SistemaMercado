import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import {
  ArrowLeftCircle,
  LucideAngularModule,
  X,
  IdCard,
  Loader2,
  Users,
  LockIcon,
  Briefcase,
} from 'lucide-angular';
import { UserService } from '../../../services/user/user.service';
import { SwalService } from '../../../services/swal/swal-service.service';
import { User } from '../../../models/user/user.model';

@Component({
  selector: 'app-criar-usuario',
  standalone: true,
  imports: [LucideAngularModule, FormsModule],
  templateUrl: './criar-usuario.component.html',
  styleUrl: './criar-usuario.component.css',
})
export class CriarUsuarioComponent {
  readonly VoltarIcon = ArrowLeftCircle;
  readonly FecharIcon = X;
  readonly UserIcon = Users;
  readonly CardIcon = IdCard;
  readonly LoaderIcon = Loader2;
  readonly LockIcon = LockIcon;
  readonly CargoIcon = Briefcase;

  @Output() fechar = new EventEmitter<void>();
  @Output() atualizar = new EventEmitter<void>();

  constructor(
    private userService: UserService,
    private swalService: SwalService,
  ) {}

  fecharModal() {
    this.fechar.emit();
  }

  //

  user: User = {
    login: '',
    password: '',
    role: null,
  };

  adicionarUsuario() {
    const error = this.userService.validarUsuario(
      this.user.login,
      this.user.password,
      this.user.role,
    );

    if (error) {
      this.swalService.error('Erro!', error);
      return;
    }

    this.userService.postUsers(this.user).subscribe({
      next: (res) => {
        this.swalService.success('Usuario Adicionado!', res);
        this.fecharModal();
        this.atualizar.emit();
      },
      error: (err) => {
        this.swalService.error('Erro!', err.error);
      },
    });
  }
}
