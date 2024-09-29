import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { OrdineDTO } from 'src/app/models/OrdineDTO';
import { AutenticazioneService } from 'src/app/services/auth/autenticazione.service';
import { OrdiniService } from 'src/app/services/order/ordini.service';

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.css']
})
export class OrderComponent {
  ordini!: Observable<OrdineDTO[]>;

  constructor(private ordiniService : OrdiniService, private router:Router, private auth:AutenticazioneService){};

  ngOnInit(){
    this.ordini=this.ordiniService.getProdotti();
  }
}
