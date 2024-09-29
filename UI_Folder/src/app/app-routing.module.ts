import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './layouts/home/home.component';
import { LoginComponent } from './layouts/login/login.component';
import { NewProductComponent } from './layouts/new-product/new-product.component';
import { ProfileComponent } from './layouts/profile/profile.component';
import { SearchComponent } from './layouts/search/search.component';
import { RegistrationComponent } from './layouts/registration/registrazione.component';
import { SearchResultComponent } from './layouts/search-result/search-result.component';
import { OrderComponent } from './layouts/order/order.component';
import { ProductComponent } from './layouts/product/product.component';
import { CartComponent } from './layouts/cart/cart.component';
import { ConfermedOrderComponent } from './layouts/confermed-order/confermed-order.component';

const routes: Routes = [

  {path: 'register', component:RegistrationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'newProduct', component: NewProductComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'filteredSearch', component:SearchComponent},
  {path: '', component:HomeComponent},
  {path: 'searchProducts', component:SearchResultComponent},
  {path: 'order',component:OrderComponent },
  {path: 'product', component: ProductComponent},
  {path: 'cart', component:CartComponent},
  {path: 'order-confirmation', component:ConfermedOrderComponent},
  

  {path: '**', component: HomeComponent, pathMatch: 'full', redirectTo: ''},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
