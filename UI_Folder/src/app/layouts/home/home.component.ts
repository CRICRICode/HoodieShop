import { Component } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { ProductDTO } from 'src/app/models/ProductDTO';
import { ProdottiService } from 'src/app/services/product/prodotti.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
onIconClick() {
  this.router.navigate(['filteredSearch']);
}
  pageNumber : number=0;
  pageSize : number=100;

  prodotti! : Observable<ProductDTO[]>;
  prodottiSenzasize! : Observable<ProductDTO[]>;


  constructor(private prodottiService : ProdottiService, private router:Router){};

  ngOnInit(){
    this.prodotti = this.prodottiService.getProdotti(this.pageNumber, this.pageSize);
    this.prodotti.subscribe((lista: ProductDTO[]) => {
      const prodottiSingolaMisura: ProductDTO[] = [];
      const mySet: Set<string> = new Set();

      for (const prod of lista) {
        if (!mySet.has(prod.nome)) {
          prodottiSingolaMisura.push(prod);
          mySet.add(prod.nome);
        }
      }

      // Filtra gli elementi con qta diversa da 0
      this.prodottiSenzasize = new Observable((observer) => {
        observer.next(prodottiSingolaMisura.filter((prod) => prod.qta > 0));
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
    this.prodotti=this.prodottiService.getProdotti(this.pageNumber, this.pageSize);
    this.prodottiService.getProdotti(this.pageNumber, this.pageSize).subscribe((lista:ProductDTO[])=>{
      var prodottiSingolaMisura: ProductDTO[]=[];
      const mySet: Set<string> = new Set();
      for(const prod of lista)
      {
        if(! mySet.has(prod.nome))
        {
          prodottiSingolaMisura.push(prod);
          mySet.add(prod.nome);
        }
      }
      this.prodottiSenzasize=new Observable(observer => {
        observer.next(prodottiSingolaMisura.filter((prod => prod.qta > 0)));
        observer.complete();
      });
    });
  }
}
