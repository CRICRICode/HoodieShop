import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserDTO } from 'src/app/models/UserDTO';
import { AutenticazioneService } from 'src/app/services/auth/autenticazione.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  min_length_password : number=5;
  hide = true;
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('',[Validators.required, Validators.minLength(this.min_length_password)]);

  nome = new FormControl('', [Validators.required]);
  cognome = new FormControl('', [Validators.required]);

  constructor(private auth : AutenticazioneService,  private router : Router){}

  getErrorMessage() {
    if (this.email.hasError('required')) { //required è il validatore
      return 'Devi inserire una mail';
    }

    return this.email.hasError('email') ? 'email non valida' : '';
  }

  getErrorMessagePassword(){
    if(this.password.hasError('required')){
      return 'Devi inserire una password';
    }

    return this.password.hasError('minlength') ? 'la password deve essere di almeno 5 caratteri' : '';
  }

  getErrorMessageNome() {
    if (this.nome.hasError('required')) { //required è il validatore
      return 'Devi inserire un nome';
    }
    return '';
  }

  getErrorMessageCognome() {
    if (this.cognome.hasError('required')) { //required è il validatore
      return 'Devi inserire un cognome';
    }
    return '';
  }

  registra(){
    const userDTO = new UserDTO();
    userDTO.nome = this.nome.value || ''; //se il valore è null dà una stringa vuota
    userDTO.password = this.password.value || '';
    userDTO.email = this.email.value || '';
    userDTO.cognome = this.cognome.value || '';
    this.auth.register(userDTO);
    this.router.navigate(['/login'])
  }
}
