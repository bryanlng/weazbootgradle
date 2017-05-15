import {Injectable} from '@angular/core';
import {CanActivate, Router} from '@angular/router';
import {Observable} from 'rxjs/Rx';
import {AuthService} from '../services/auth.service';

@Injectable()
export class CanActivateWhenLoggedInGuard implements CanActivate {

  constructor(private authService: AuthService, private router: Router) {
  }

  canActivate() {
    return this.authService.getLoggedInUser()
      .map(() => {
        return true;
      }).catch(() => {
        this.router.navigate(['landing']);
        return Observable.of(false);
      }).take(1);
  }
}
