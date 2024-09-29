import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticazioneService } from 'src/app/services/auth/autenticazione.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent {
  constructor(public auth:AutenticazioneService, private router:Router){}


  getNome(){
    return this.auth.getName();
  }

  isAdmin(){
    return this.auth.getRole();
  }

  getCognome()
  {
    return this.auth.getSurname(); //TODO
  }

  goToCarrello()
  {
    this.router.navigate(['cart']);
  }
  goToOrdini()
  {
    this.router.navigate(['order']);
  }
  goToAdmin()
  {
    this.router.navigate(["newProduct"]);
  }
}

