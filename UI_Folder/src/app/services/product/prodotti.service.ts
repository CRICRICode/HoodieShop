import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ADDRESS_BACK_END } from 'src/app/const';
import { UserDTO } from 'src/app/models/UserDTO';
import { ProductDTO } from 'src/app/models/ProductDTO';

@Injectable({
  providedIn: 'root'
})
export class ProdottiService {
  prodottiObs : Observable<ProductDTO[]> = new Observable<ProductDTO[]>();
  constructor(private http : HttpClient) { }

  getProdotti(pageNumber: number=0, pageSize: number=10, sortBy: string="id") :Observable<ProductDTO[]>{

    let params = new HttpParams()
    .set('pageSize', pageSize.toString())
    .set('pageNumber', pageNumber.toString())
    .set("sortBy", sortBy);

    console.log(params)

    this.prodottiObs=this.http.get<ProductDTO[]>(ADDRESS_BACK_END+"/prodotti", { params });
    this.prodottiObs.subscribe();
    return this.prodottiObs;
  }

  getProdottiByNome(nomeProdotto : string) :Observable<ProductDTO[]>{
    
    return this.getProdottiFiltrati(0,10,"id",nomeProdotto);
  }

  getProdottiFiltrati(pageNumber: number=0, pageSize: number=10, sortBy: string="prezzo",
                      nome: string="", prezzoMax:number=5000, prezzoMin:number=0, brand:string="",
                      size:string=""): Observable<ProductDTO[]> {
    let params = new HttpParams()
    .set('pageSize', pageSize.toString())
    .set('pageNumber', pageNumber.toString())
    .set("sortBy", sortBy)
    .set('nome', nome)
    .set('prezzoMax', prezzoMax.toString())
    .set('prezzoMin', prezzoMin.toString())
    .set('brand', brand)
    .set('size', size)

    console.log(params)

    this.prodottiObs=this.http.get<ProductDTO[]>(ADDRESS_BACK_END+"/prodotti/filtrati", { params: params });
    this.prodottiObs.subscribe();
    return this.prodottiObs;
  }


  getProdottiById(idFornitore: string): Observable<ProductDTO[]> {
    const url = ADDRESS_BACK_END+idFornitore;
    return this.http.get<ProductDTO[]>(url);
  }
}



