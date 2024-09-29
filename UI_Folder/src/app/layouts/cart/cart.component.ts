import { HttpErrorResponse, HttpHeaderResponse, HttpResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { OrdineDTO } from 'src/app/models/OrdineDTO';
import { ProdottoCarrelloDTO } from 'src/app/models/ProdottoCarrelloDTO';
import { ProductDTO } from 'src/app/models/ProductDTO';
import { AutenticazioneService } from 'src/app/services/auth/autenticazione.service';
import { CarrelloService } from 'src/app/services/cart/carrello.service';
import { OrdiniService } from 'src/app/services/order/ordini.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent {
  pageNumber: number = 0;
  pageSize: number = 5;

  prodotti!: Observable<ProdottoCarrelloDTO[]>;
  prodottiDaAcquistare: ProdottoCarrelloDTO[] = [];
  

  constructor(
    private carrelloService: CarrelloService,
    private router: Router,
    private auth: AutenticazioneService,
    private ordiniService: OrdiniService
  ) {}

  ngOnInit() {
    this.getProdottiInCarrello();
  }

  getProdottiInCarrello() {
    this.prodotti = this.carrelloService.getProdotti(this.pageNumber, this.pageSize);
    console.log(this.prodotti)
    this.carrelloService.prodottiObs.subscribe((lista : ProdottoCarrelloDTO[])=>{
      for(const prodCar of lista)
      {
        var prodTmp=prodCar;
        prodTmp.qta=prodCar.qta;
        prodTmp.prezzo=prodCar.prezzo;
        this.prodottiDaAcquistare.push(prodTmp);
        
      }})
    console.log("GET PRODOTTI")
    console.log(this.prodottiDaAcquistare)
  }

  viewProduct(nomeProdotto: String) {
    this.router.navigate(['product'], { queryParams: { nomeProdotto } });
  }

  onPageChange(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.pageNumber = event.pageIndex;
    this.getProdottiInCarrello();
  }

  incrementa(input: string) {
    return (Number(input) + 1).toString();
  }

  decrementa(input: string) {
    return (Number(input) - 1).toString();
  }

  rimuoviDalCarrello(prodottoCarrello: ProdottoCarrelloDTO, qta: string) {
    const prodotto = prodottoCarrello.prodotto;
    prodotto.qta = Number(qta);

    this.carrelloService.removeProdotto(prodotto).subscribe(
      (response: any) => {
        const status: number = response.status;
        console.log('Stato della risposta:', status);

        // Puoi fare ulteriori operazioni in base allo stato della risposta
        if (response instanceof HttpErrorResponse || status === 200) {
          console.log('La richiesta è stata elaborata correttamente.');
          alert('Rimozione avvenuta con successo!');

          // Aggiorna la pagina dopo la rimozione con successo
          window.location.reload();
        } else {
          console.log('Stato della risposta non gestito.');
        }
      },
      (error: any) => {
        console.log('La richiesta è stata elaborata correttamente.');
        window.location.reload();
      }
    );
  }


  

  acquista() {
    let prodotti;
    const ordineDTO : OrdineDTO = new OrdineDTO();
    ordineDTO.data=new Date;
    ordineDTO.dettagli=this.prodottiDaAcquistare;
    this.ordiniService.newPurchase(ordineDTO).subscribe(response=>{
      if(response.object==null){
        alert(response.message)
      }
      else{
        alert(response.message)
        this.prodottiDaAcquistare = []
        this.getProdottiInCarrello();
        this.router.navigate(['order-confirmation'])
      }
    });
  }

  isLogged() {
    return this.auth.isLogged();
  }
}
