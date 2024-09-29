import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { tap, map } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';
import { UserDTO } from 'src/app/models/UserDTO';
import { TokenRefreshService } from '../token/token-refresh.service';
import { TokenService } from '../token/token.service';
import { ADDRESS_AUTHENTICATION_SERVER, ADDRESS_BACK_END, CLIENT_ID, REALM } from 'src/app/const';


@Injectable({
  providedIn: 'root'
})
export class AutenticazioneService {

  jwtDecoder = new JwtHelperService();

  constructor(private http : HttpClient, private router : Router, private tokenRefreshService: TokenRefreshService,
     private tokenService : TokenService) { }

  public register(utente : UserDTO)
  {
    this.http.post(ADDRESS_BACK_END+"/utenti/registration",utente, { observe: 'response' }).pipe(
      map(response => {
        return {
          status: response.status,
          body: response.body as string
        };
      }),
      tap(response => {
        if (response.status === 200) {
          alert(response.body);
        } else {
          alert(response.body); //messaggio di errore di spring
          return
        }
      })
    ).subscribe();//subscribe
    /*
      console.log(utente.email)
      console.log(utente.password)

    this.login(utente.email,utente.password);
    */
  }//register

  public login(email : string, password : string)
  {
    const headers = new HttpHeaders({

      'Content-Type': 'application/x-www-form-urlencoded',

    });

    const body = new HttpParams()

      .set("client_id", "client_rest_api")

      .set("grant_type", "password")

      .set("username", email)

      .set("password", password)

      //.set("scope","openid");
    
      this.http.post(ADDRESS_AUTHENTICATION_SERVER+"/realms/"+REALM+"/protocol/openid-connect/token", body.toString(), { headers, observe: 'response' })
  .subscribe((response: HttpResponse<any>) => {
    console.log('Stringa della risposta:', response.body);

    if (response.status === 200) {
      alert("Login avvenuto con successo");
    } else {
      alert("Login fallito");
    }

    window.localStorage.setItem('TOKEN', response.body.access_token);
    window.localStorage.setItem('RTOKEN', response.body.refresh_token);
    window.location.reload();
    this.tokenService.setToken(response.body.access_token);
    this.tokenRefreshService.startTokenRefresh(response.body.refresh_token);
  });
      
  }//login

  public logout(){
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
      'Authorization' : "Bearer "+window.localStorage.getItem('TOKEN')
    });

    const body = new HttpParams()
      .set("client_id", CLIENT_ID)
      .set("refresh_token",window.localStorage.getItem('RTOKEN') || '');
    

      this.http.post(ADDRESS_AUTHENTICATION_SERVER+"/realms/"+REALM+"/protocol/openid-connect/logout",body.toString(),{headers, observe: 'response'}).subscribe(

      (response: HttpResponse<any>) => {
        console.log('Stringa della risposta:', response.body);

          if(response.status === 204){
            window.localStorage.clear();
            alert("Logout effettuato con successo");
            this.router.navigate(['/home'])
            this.tokenService.setToken(null);
          }
          else{
            this.router.navigate(['/utente'])
          }
        });
  }//logout

  public isLogged():boolean{

    const accessToken = window.localStorage.getItem('TOKEN');

    const refreshToken = window.localStorage.getItem('RTOKEN');

    const isLoggedIn = !(accessToken === null || refreshToken === null);

    return isLoggedIn;

  }//isLogged


  public getUsername():string{
    var parsedResponse = JSON.stringify(window.localStorage.getItem('TOKEN'));
    let decodedToken = this.jwtDecoder.decodeToken(parsedResponse);
    return decodedToken.preferred_username;
  }

  public getName() {
    var parsedResponse = JSON.stringify(window.localStorage.getItem('TOKEN'));
    let decodedToken = this.jwtDecoder.decodeToken(parsedResponse);
    return decodedToken.name;
  }


  public getFirstName(): string {
    var parsedResponse = JSON.stringify(window.localStorage.getItem('TOKEN'));
    let decodedToken = this.jwtDecoder.decodeToken(parsedResponse);
    return decodedToken.given_name;
  }

  public getSurname(): string {
    var parsedResponse = JSON.stringify(window.localStorage.getItem('TOKEN'));
    let decodedToken = this.jwtDecoder.decodeToken(parsedResponse);
    return decodedToken.family_name
  }


  public getRole(){
    var parsedResponse = JSON.stringify(window.localStorage.getItem('TOKEN'));
    let decodedToken = this.jwtDecoder.decodeToken(parsedResponse);
    let a : string[] = decodedToken.resource_access.client_rest_api.roles;
    return a.includes('admin');
  }


}//AutenticazioneService
