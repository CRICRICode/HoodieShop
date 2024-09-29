import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent {
  nome = new FormControl('');
  prezzoMax = new FormControl('5000', [Validators.min(0),Validators.pattern(/^[0-9]+$/)]);
  prezzoMin = new FormControl('0', [Validators.min(0),Validators.pattern(/^[0-9]+$/)]);
  brand = new FormControl('');
  size = new FormControl('M', [ Validators.min(0),Validators.pattern(/^(XXS|XS|S|M|L|XL|XXL)$/i)]);

  constructor(private router:Router){}

  getErrorMessagePrezzoMax() {
    if (this.prezzoMax.hasError('min')) { //required è il validatore
      return 'Devi inserire un prezzo positivo';
    }
    if (this.prezzoMax.hasError('pattern')) { //required è il validatore
      return 'Devi inserire un prezzo';
    }

    return "";
  }

  getErrorMessagePrezzoMin() {
    if (this.prezzoMin.hasError('min')) { //required è il validatore
      return 'Devi inserire un prezzo positivo';
    }
    if (this.prezzoMin.hasError('pattern')) { //required è il validatore
      return 'Devi inserire un prezzo';
    }


    return "";
  }

  getErrorMessagesize() {
    if (this.size.hasError('min')) { //required è il validatore
      return 'Devi inserire una size positivo';
    }
    if (this.size.hasError('pattern')) { //required è il validatore
      return 'Devi inserire una size';
    }

    return "";
  }

  ricercaAvanzata(){
    this.router.navigate(['/risultatiRicerca'], { queryParams: {  'nome': this.nome.value || '',
                                                      'prezzoMax': Number(this.prezzoMax.value) || 5000, 
                                                      'prezzoMin': Number(this.prezzoMin.value) || 0, 
                                                      'brand': this.brand.value || '', 
                                                      'size': Number(this.size.value) || '' } });
  }

}
