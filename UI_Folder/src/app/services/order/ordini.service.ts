import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ADDRESS_BACK_END } from 'src/app/const';
import { OrdineDTO } from 'src/app/models/OrdineDTO';
import { ProdottoCarrelloDTO } from 'src/app/models/ProdottoCarrelloDTO';
import { ProductDTO } from 'src/app/models/ProductDTO';
import { ResponseMessage } from 'src/app/models/responseEntity';


@Injectable({
  providedIn: 'root'
})
export class OrdiniService {

  constructor(private http : HttpClient) { }

  ordini : Observable<OrdineDTO[]> = new Observable<OrdineDTO[]>();

  getProdotti(pageNumber: number=0, pageSize: number=10, sortBy: string="prezzo") :Observable<OrdineDTO[]>{

    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization' : "Bearer "+window.localStorage.getItem('TOKEN')
    });
    

    this.ordini=this.http.get<OrdineDTO[]>(ADDRESS_BACK_END+"/utenti/ordini", { headers});
    this.ordini.subscribe((value)=>{console.log(value)});
    return this.ordini;
  }

  acquista(prodotti:ProdottoCarrelloDTO[]){ 
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : "Bearer "+window.localStorage.getItem('TOKEN')
    });

    console.log(prodotti)
    return this.http.post(ADDRESS_BACK_END+"/carello/acquista", prodotti, {headers}) 
  }

  public newPurchase(purchase : OrdineDTO){
    const headers = new HttpHeaders({
      'Authorization' : "Bearer "+window.localStorage.getItem('TOKEN')
    });
    

    return this.http.post<ResponseMessage>(ADDRESS_BACK_END+"/carrello/acquista",purchase,{headers})
  }

}
