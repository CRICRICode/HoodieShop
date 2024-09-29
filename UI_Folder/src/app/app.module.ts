import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';


//Angular Material
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule, MatMiniFabButton} from '@angular/material/button';
import {MatMenuModule} from '@angular/material/menu';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatCardModule} from '@angular/material/card';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import {MAT_FORM_FIELD_DEFAULT_OPTIONS, MatFormFieldModule} from '@angular/material/form-field';
import {MatPaginatorModule} from '@angular/material/paginator';



//Forms
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';

//Routing
import { RouterModule, Routes } from '@angular/router';

//HTTP
import { HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HomeComponent } from './layouts/home/home.component';
import { AppComponent } from './app.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { UnderComponent } from './layouts/navbar/under/under.component';
import { LoginComponent } from './layouts/login/login.component';
import { NewProductComponent } from './layouts/new-product/new-product.component';
import { ProfileComponent } from './layouts/profile/profile.component';
import { SearchComponent } from './layouts/search/search.component';
import { RegistrationComponent } from './layouts/registration/registrazione.component';
import { SearchResultComponent } from './layouts/search-result/search-result.component';
import { ProductComponent } from './layouts/product/product.component';
import { OrderComponent } from './layouts/order/order.component';
import { CartComponent } from './layouts/cart/cart.component';
import { FlexLayoutModule } from '@angular/flex-layout';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import { FooterComponent } from './layouts/footer/footer.component';
import { ConfermedOrderComponent } from './layouts/confermed-order/confermed-order.component';
import { PaymentComponent } from './layouts/payment/payment.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    NavbarComponent,
    UnderComponent,
    LoginComponent,
    NewProductComponent,
    ProfileComponent,
    SearchComponent,
    RegistrationComponent,
    SearchResultComponent,
    ProductComponent,
    OrderComponent,
    CartComponent,
    FooterComponent,
    ConfermedOrderComponent,
    PaymentComponent,

    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    //Angular Material
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatMenuModule,
    BrowserAnimationsModule,
    MatTooltipModule,
    MatGridListModule,
    MatCardModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatPaginatorModule,
    MatProgressBarModule,
    //Routing
    RouterModule,
    //HTTP
    HttpClientModule,
    //FORMS
    ReactiveFormsModule,
    FormsModule,
    MatInputModule,
    MatButtonModule,
    FlexLayoutModule,



    
    

  ],
  providers: [{provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}}
],
  bootstrap: [AppComponent]
})
export class AppModule { }
