import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import 'rxjs/add/operator/toPromise';
import {Observable} from 'rxjs/Observable';
import {ReplaySubject} from 'rxjs/ReplaySubject';
import {User} from '../models/user';
import {Router} from '@angular/router';

@Injectable()
export class AuthService {
  private dataObs$ = new ReplaySubject<User>(1);

  constructor(private http: Http, private router: Router) {
  }

  getLoggedInUser(): Observable<User> {
    if (!this.dataObs$.observers.length) {
      this.http.get('user').subscribe(
        data => {
          if (data.url.includes('auth/login')) {
            this.dataObs$.error('Auth server redirected.  This is normal for not being logged in.');
          } else {
            this.dataObs$.next(data.json());
          }
        },
        error => {
          this.dataObs$.error(error);
          this.dataObs$ = new ReplaySubject<User>(1);
        });
    }
    return this.dataObs$;
  }

  logout(): Promise<void> {
    return this.http.post('logout', null).map(
      () => {
        this.dataObs$ = new ReplaySubject<User>(1);
        this.router.navigate(['landing']);
      }
    ).toPromise();
  }
}
