import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
import { ADDRESS_BACK_END } from 'src/app/const';
import { ProdottoCarrelloDTO } from 'src/app/models/ProdottoCarrelloDTO';
import { ProductDTO } from 'src/app/models/ProductDTO';

@Injectable({
  providedIn: 'root'
})
export class CarrelloService {
  prodottiObs : Observable<ProdottoCarrelloDTO[]> = new Observable<ProdottoCarrelloDTO[]>();
  constructor(private http : HttpClient) { }

  getProdotti(pageNumber: number=0, pageSize: number=10, sortBy: string="prezzo") :Observable<ProdottoCarrelloDTO[]>{

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization' : "Bearer "+window.localStorage.getItem('TOKEN')
    });
    
    let params = new HttpParams()
    .set('pageSize', pageSize.toString())
    .set('pageNumber', pageNumber.toString())
    .set("sortBy", sortBy);



    this.prodottiObs=this.http.get<ProdottoCarrelloDTO[]>(ADDRESS_BACK_END+"/carrello", { headers,params });
    this.prodottiObs.subscribe((value)=>{console.log(value)});
    console.log(this.prodottiObs)
    return this.prodottiObs;
  }

  addProdotto(prodotto: ProductDTO, qta:number) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + window.localStorage.getItem('TOKEN')
    });
    prodotto.qta= qta
    console.log(prodotto)
    return this.http.post(ADDRESS_BACK_END+ "/carrello", prodotto, {headers})
      .pipe(
        catchError((error: any) => {
          throw error; 
        })
      );
  }


  removeProdotto(prodotto: ProductDTO) {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ' + window.localStorage.getItem('TOKEN')
    });

    return this.http.post(ADDRESS_BACK_END+"/carrello/remove", prodotto, { headers })
  }
}



