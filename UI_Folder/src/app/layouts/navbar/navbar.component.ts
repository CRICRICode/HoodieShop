import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AutenticazioneService } from 'src/app/services/auth/autenticazione.service';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

goToCart() {
  if(!this.auth.isLogged()){
    this.router.navigate(['login'])
  }else{
    this.router.navigate(['cart'])
  }
}
  title = 'WatchStore';

  constructor(private tokenService: TokenService, public auth: AutenticazioneService, public router:Router) {}


  logOut(){
    this.auth.logout();
    this.router.navigate(['home'])
  }

  logIn(){
    this.router.navigate(['/login']);
  }

  registrati(){
    this.router.navigate(['/register']);
  }

  account(){
    this.router.navigate(['profile']);
  }
}