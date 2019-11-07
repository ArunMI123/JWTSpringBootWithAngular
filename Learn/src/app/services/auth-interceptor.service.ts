import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import 'rxjs/add/operator/do';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService {

  constructor(private auth: AuthService, private router: Router, private messageService: MessageService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.auth.getToken()}`
      }
    });
    return next.handle(request).do((event: HttpEvent<any>) => {
      if (event instanceof HttpResponse) {
      }
    }, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        if (err.status === 401) {
          this.router.navigate(['login']);
          this.messageService.add({ severity: 'error', summary: '401', detail: request.url + ' ' + err.statusText });
        }
        const error = err.error.message || err.statusText;
        return throwError(error);
      }
    });
  }

}
