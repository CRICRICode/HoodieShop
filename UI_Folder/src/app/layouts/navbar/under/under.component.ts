import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AutenticazioneService } from 'src/app/services/auth/autenticazione.service';

@Component({
  selector: 'app-under',
  templateUrl: './under.component.html',
  styleUrls: ['./under.component.css']
})
export class UnderComponent {

  constructor(public auth:AutenticazioneService, private router:Router){}

  public newProduct(){
    this.router.navigate(["newProduct"])
  }

  public productList(){
    this.router.navigate(["list"])
  }
}
