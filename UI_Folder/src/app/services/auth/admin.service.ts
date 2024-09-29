import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ADDRESS_BACK_END } from 'src/app/const';
import { ProductDTO } from 'src/app/models/ProductDTO';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http : HttpClient) { }

  addProdotto(prodotto: ProductDTO)
  {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization' : "Bearer "+window.localStorage.getItem('TOKEN')
    });
    const body=prodotto.toString()
    var ret
    return this.http.post<any>(ADDRESS_BACK_END+"/admin/prodotto", prodotto, {headers});
  }
}
