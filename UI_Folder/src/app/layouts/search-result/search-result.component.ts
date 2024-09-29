import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ProductDTO } from 'src/app/models/ProductDTO';
import { ProdottiService } from 'src/app/services/product/prodotti.service';

@Component({
  selector: 'app-search-result',
  templateUrl: './search-result.component.html',
  styleUrls: ['./search-result.component.css']
})
export class SearchResultComponent {
  constructor( private route: ActivatedRoute, private prodottiService: ProdottiService, private router:Router){}

  pageNumber : number=0;
  pageSize : number=5;

  nome : string =""
  prezzoMax! : number;
  prezzoMin! : number;
  brand! : string;
  size! : string;

  prodottiSenzasize! : Observable<ProductDTO[]>;

  ngOnInit(){
    this.route.queryParams.subscribe(params => {
      this.nome = params['nome'];
      this.prezzoMax = params['prezzoMax'];
      this.prezzoMin = params['prezzoMin'];
      this.brand = params['brand'];
      this.size = params['size'];
    });
      this.prodottiService.getProdottiFiltrati(this.pageNumber, this.pageSize, "prezzo", this.nome,
                                              this.prezzoMax, this.prezzoMin, this.brand, this.size).subscribe((lista:ProductDTO[])=>{
      var prodottiMonosize: ProductDTO[]=[];
      const mySet: Set<string> = new Set();
      for(const prod of lista)
      {
        if(! mySet.has(prod.nome))
        {
          prodottiMonosize.push(prod);
          mySet.add(prod.nome);
        }
      }
      this.prodottiSenzasize=new Observable(observer => {
        observer.next(prodottiMonosize);
        observer.complete();
      });
    });
  }

  viewProduct(nomeProdotto : String)
  {
    this.router.navigate(['/product'], { queryParams: { nomeProdotto } });
  }

  onPageChange(event: PageEvent) {
    this.pageSize = event.pageSize;
    this.pageNumber = event.pageIndex;
    this.prodottiService.getProdottiFiltrati(this.pageNumber, this.pageSize, "prezzo", this.nome,
                                              this.prezzoMax, this.prezzoMin, this.brand, this.size).subscribe((lista:ProductDTO[])=>{
      var prodottiMonosize: ProductDTO[]=[];
      const mySet: Set<string> = new Set();
      for(const prod of lista)
      {
        if(! mySet.has(prod.nome))
        {
          prodottiMonosize.push(prod);
          mySet.add(prod.nome);
        }
      }
      this.prodottiSenzasize=new Observable(observer => {
        observer.next(prodottiMonosize);
        observer.complete();
      });
    });
  }
}
