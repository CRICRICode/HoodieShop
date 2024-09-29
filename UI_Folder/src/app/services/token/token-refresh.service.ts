import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { Observable } from 'rxjs/internal/Observable';
import { interval } from 'rxjs/internal/observable/interval';
import { map } from 'rxjs/internal/operators/map';
import { ADDRESS_AUTHENTICATION_SERVER, REALM } from 'src/app/const';

@Injectable({
  providedIn: 'root'
})
export class TokenRefreshService {
  private readonly refreshIntervalInMinutes = 3; // Intervallo di aggiornamento del token in minuti

  constructor(private http : HttpClient) {}

  startTokenRefresh(refreshToken: string) {
    // Esegue il refresh del token ogni tot minuti
    interval(this.refreshIntervalInMinutes * 60 * 1000).subscribe(() => {
      this.refreshToken(refreshToken);
    });
  }

  refreshToken(refreshToken: string){
    const refreshTokenUrl = ADDRESS_AUTHENTICATION_SERVER+"/realms/"+REALM+"/protocol/openid-connect/token";
  
    const headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded',
    });
  
    const body = new HttpParams()
      .set('grant_type', 'refresh_token')
      .set('refresh_token', refreshToken)
      .set('client_id', 'your_client_id');
  /*
    return this.http.post(refreshTokenUrl, body.toString(), { headers })
      .pipe(
        map((response: Object) => new HttpResponse<any>({ body: response }))
      ).subscribe();

    return this.http.post(refreshTokenUrl, body.toString(), { headers, observe: 'response' })
    .pipe(
      map((response: HttpResponse<any>) => response.body), // Estrarre solo il corpo della risposta
      tap(body => {
        console.log('Stringa della risposta:', body);
      }),
      tap(response => {
        if (response.status === 200) {
          alert("Login avvenuto con successo")
        } else {
          alert("Login fallito")
        }
      })
    )
    .subscribe((response)=>{
        window.localStorage.setItem('TOKEN',response.access_token);

        window.localStorage.setItem('RTOKEN',response.refresh_token);       
    });

    */
   return this.http.post("refreshTokenUrl", body.toString(), { headers, observe: 'response' })
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
   });
  }//refreshToken
}