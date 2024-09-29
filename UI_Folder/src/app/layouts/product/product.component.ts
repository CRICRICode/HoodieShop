import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BehaviorSubject, Observable, interval } from 'rxjs';
import { ProductDTO } from 'src/app/models/ProductDTO';
import { AutenticazioneService } from 'src/app/services/auth/autenticazione.service';

import { CarrelloService } from 'src/app/services/cart/carrello.service';
import { ProdottiService } from 'src/app/services/product/prodotti.service';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {
  nome!: string;
  prodotti!: ProductDTO[];
  prodotto: ProductDTO = new ProductDTO();
  prodottoSelezionato!: ProductDTO;
  isAddingToCart: boolean = false;
  menuItemSelected = false;
  isButtonClicked = false;
  //quantita = new FormControl(0, [Validators.min(0),Validators.pattern(/^[0-9]+$/)]);
  quantita = 0


  constructor(private prodottiService: ProdottiService, private route: ActivatedRoute, private carrelloService: CarrelloService, private router: Router, public auth:AutenticazioneService) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.nome = params['nomeProdotto'];

      this.prodottiService.getProdottiByNome(this.nome).subscribe(prodotti => {
        console.log(prodotti)
        console.log(params)
        if (prodotti.length > 0) {
          console.log(this.prodotto)
          this.prodotti = prodotti;
          this.prodotto = this.prodotti[0];
        }
      });
    });

  }

  onMenuItemClicked(prod: ProductDTO) {
    // Aggiungi eventuali logiche aggiuntive se necessario
    this.menuItemSelected = true;
    this.prodottoSelezionato = prod;
    this.quantita = 1;
    this.isDisabledSubjectDec.next(true)
  }

  aggiungiAlCarrello() {
    if (!this.auth.isLogged()) {
      alert("Registrati/Accedi per aggiungere al carrello!")
      this.router.navigate(['login']);
    } else {
      this.prodottoSelezionato.qta = this.quantita;
      this.isAddingToCart = true;
      this.carrelloService.addProdotto(this.prodottoSelezionato, this.quantita).subscribe(() => {
          this.isAddingToCart = false;
          this.isButtonClicked = true; // Imposta la variabile su true dopo il ritardo
          alert("Prodotto aggiunto al carrello")
          this.router.navigate(['']);
      });
    }
  }

  private isDisabledSubjectInc = new BehaviorSubject<boolean>(false);

  public isDisabledInc$: Observable<boolean> = this.isDisabledSubjectInc.asObservable();

  incrementa() {
    /*
    if((this.quantita || 0)<this.prodottoSelezionato.qta)
    this.quantita.setValue((this.quantita.value || 0)+1);
    */
    this.quantita = this.quantita + 1;
    if (this.quantita === this.prodottoSelezionato.qta) {
      this.isDisabledSubjectInc.next(true)
    }
    if (this.quantita !== 1) {
      this.isDisabledSubjectDec.next(false)
    }
  }

  private isDisabledSubjectDec = new BehaviorSubject<boolean>(false);

  public isDisabledDec$: Observable<boolean> = this.isDisabledSubjectDec.asObservable();

  decrementa() {
    /*
    this.quantita.setValue((this.quantita.value || 2)-1);
    */
    this.quantita = this.quantita - 1;
    if (this.quantita === 1) {
      this.isDisabledSubjectDec.next(true)
    }
    if (this.quantita !== this.prodottoSelezionato.qta) {
      this.isDisabledSubjectInc.next(false)
    }
  }



}