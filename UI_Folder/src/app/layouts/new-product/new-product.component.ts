import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HostListener } from '@angular/core';
import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ProductDTO } from 'src/app/models/ProductDTO';
import { AdminService } from 'src/app/services/auth/admin.service';
import { AutenticazioneService } from 'src/app/services/auth/autenticazione.service';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent {
  constructor(private admin : AdminService,  private router : Router, private auth:AutenticazioneService){}

  nome = new FormControl('', [Validators.required]);
  prezzo = new FormControl('200', [Validators.required,Validators.min(0),Validators.pattern(/^[0-9]+$/)]);
  qta = new FormControl('20', [Validators.required,Validators.min(0),Validators.pattern(/^[0-9]+$/)]);
  brand = new FormControl('', [Validators.required]);
  size = new FormControl('M', [Validators.required, Validators.min(0),Validators.pattern(/^(XXS|XS|S|M|L|XL|XXL)$/i)]);
  url = new FormControl('', [Validators.required]);


  ngOnInit(){
    if(! this.auth.getRole())
      this.router.navigate(['/home']);
  }

  getErrorMessageNome() {
    if(this.nome.hasError('required')) { //required è il validatore
      return 'Devi inserire un prezzo';
    }

    return "";
  }

  getErrorMessagePrezzo() {
    if (this.prezzo.hasError('min')) { //required è il validatore
      return 'Devi inserire un prezzo positivo';
    }
    if (this.prezzo.hasError('pattern')) { //required è il validatore
      return 'Devi inserire un prezzo';
    }
    if(this.prezzo.hasError('required')) { //required è il validatore
      return 'Devi inserire un prezzo';
    }

    return "";
  }

  getErrorMessagePrezzoQta() {
    if (this.qta.hasError('min')) { //required è il validatore
      return 'Devi inserire una quantità positiva';
    }
    if (this.qta.hasError('pattern')) { //required è il validatore
      return 'Devi inserire una quantità';
    }
    if(this.qta.hasError('required')) { //required è il validatore
      return 'Devi inserire una quantità';
    }

    return "";
  }

  getErrorMessageBrand() {
    if(this.brand.hasError('required')) { //required è il validatore
      return 'Devi inserire un brand';
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
    if(this.size.hasError('required')) { //required è il validatore
      return 'Devi inserire una size';
    }
    return "";
  }

  getErrorMessageUrl() {
    if(this.url.hasError('required')) { //required è il validatore
      return 'Devi inserire un url';
    }

    return "";
  }

  caricaProdotto()
  {
    var prodotto=new ProductDTO();
    prodotto.nome=this.nome.value || '';
    prodotto.prezzo=Number(this.prezzo.value) || 200;
    prodotto.qta=Number(this.qta.value) || 20;
    prodotto.nomeBrand=this.brand.value || '';
    prodotto.url=this.url.value || '';
    prodotto.size=String(this.size.value) || "M";
    console.log(prodotto)
    this.admin.addProdotto(prodotto).subscribe((response: HttpResponse<any>) => {
      const status: number = response.status;
      console.log('Stato della risposta:', status);
  
      // Puoi fare ulteriori operazioni in base allo stato della risposta
      if (response instanceof HttpErrorResponse && response.status==200) {
        console.log('La richiesta è stata elaborata correttamente.');
        alert('Il prodotto è ora online!')
      } else {
        console.log('Stato della risposta non gestito.');
      }
    },
    
  );
  }

  onPaste(event: ClipboardEvent): void {
    const clipboardData:any= event.clipboardData;
    const pastedText = clipboardData.getData('text');
    // Ora puoi gestire il testo incollato come desideri
    console.log('Testo incollato:', pastedText);
  }

  pasteFromClipboard(): void {
    navigator.clipboard.readText().then((pastedText) => {
      this.url.setValue(pastedText);
    }).catch((error) => {
      console.error('Errore durante l\'incolla dalla clipboard:', error);
    });
  }
  
}
