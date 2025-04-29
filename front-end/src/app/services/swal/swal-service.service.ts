// src/app/services/swal/swal.service.ts
import { Injectable } from '@angular/core';
import Swal from 'sweetalert2';

@Injectable({
  providedIn: 'root',
})
export class SwalService {
  success(title: string, text: string) {
    return Swal.fire({
      icon: 'success',
      title,
      text,
      timer: 2500,
      showConfirmButton: false,
      toast: true,
      position: 'top-end',
      background: '#4BB543',
      color: '#fff',
      width: '450px',
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });
  }

  error(title: string, text: string) {
    return Swal.fire({
      icon: 'error',
      title,
      text,
      timer: 2500,
      showConfirmButton: false,
      toast: true,
      width: '450px',
      position: 'top-end',
      background: '#B22222',
      color: '#fff',
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });
  }

  warning(title: string, text: string) {
    return Swal.fire({
      icon: 'warning',
      title,
      text,
      timer: 2500,
      showConfirmButton: false,
      toast: true,
      position: 'top-end',
      background: '#B8860B',
      width: '450px',
      color: '#fff',
      timerProgressBar: true,
      didOpen: (toast) => {
        toast.addEventListener('mouseenter', Swal.stopTimer);
        toast.addEventListener('mouseleave', Swal.resumeTimer);
      },
    });
  }

  question(title: string, text: string) {
    return Swal.fire({
      icon: 'question',
      title,
      text,
      timer: 3000,
      showConfirmButton: true,
      confirmButtonText: 'Ok',
      confirmButtonColor: '#007BFF',
      background: '#F4F6F9',
      position: 'center',
      color: '#333',
      timerProgressBar: true,
    });
  }

  confirm(titulo: string, texto: string): Promise<boolean> {
    return Swal.fire({
      title: titulo,
      text: texto,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Sim',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#28A745',
      cancelButtonColor: '#DC3545',
      background: '#F9F9F9',
      color: '#333',
      showLoaderOnConfirm: true,
      preConfirm: () => {
        return new Promise((resolve) => {
          resolve(true);
        });
      },
    }).then((result) => result.isConfirmed);
  }

  confirmAndWrite(
    titulo: string,
    texto: string,
    confirmar: string,
  ): Promise<boolean> {
    return Swal.fire({
      icon: 'warning',
      title: `<strong>${titulo}</strong>`,
      text: texto,
      input: 'text',
      inputPlaceholder: `Digite ${confirmar} para excluir`,
      showCancelButton: true,
      confirmButtonText: 'Deletar',
      cancelButtonText: 'Cancelar',
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      preConfirm(inputValue) {
        if (inputValue !== confirmar) {
          Swal.showValidationMessage(`Você precisa digitar ${confirmar}`);
          return false;
        }
        return true;
      },
    }).then((result) => result.isConfirmed && result.value === true);
  }
}
