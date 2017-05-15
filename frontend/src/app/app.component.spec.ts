import {TestBed} from '@angular/core/testing';
import {RouterTestingModule} from '@angular/router/testing';
import {Routes} from '@angular/router';
import {Observable} from 'rxjs/Rx';

import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {ResourcesComponent} from './resources/resources.component';
import {User} from './models/user';
import {AuthService} from './services/auth.service';

export const fakeRoutes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'resources', component: ResourcesComponent}
];

class MockAuthService {
  // noinspection JSMethodCanBeStatic
  getLoggedInUser(): Observable<User> {
    return Observable.of({});
  }

  // noinspection JSMethodCanBeStatic
  logout(): Promise<void> {
    return new Promise<void>((resolve) => {
      resolve();
    });
  }
}

describe('AppComponent', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        {provide: AuthService, useClass: MockAuthService}
      ],
      declarations: [
        AppComponent,
        HomeComponent,
        ResourcesComponent
      ],
      imports: [
        RouterTestingModule.withRoutes(fakeRoutes)
      ]
    });
    TestBed.compileComponents();
    this.fixture = TestBed.createComponent(AppComponent);
  });

  it('should render title in a h1 tag', () => {
    this.fixture.detectChanges();
    this.compiled = this.fixture.debugElement.nativeElement;
    this.app = this.fixture.debugElement.componentInstance;
    expect(this.app.title).toEqual('Weazbootgradle');
    expect(this.compiled.querySelector('h1').textContent).toContain('Weazbootgradle');
  });

  it('should have a navbar brand set', () => {
    this.fixture.detectChanges();
    this.compiled = this.fixture.debugElement.nativeElement;
    expect(this.compiled.querySelector('.navbar-brand').textContent).toContain('wbg');
  });

  describe('when the user is not logged in', () => {
    beforeEach(() => {
      this.authService = this.fixture.debugElement.injector.get(AuthService);

      spyOn(this.authService, 'getLoggedInUser').and.returnValue(Observable.of(null));
      this.fixture.detectChanges();
      this.compiled = this.fixture.debugElement.nativeElement;
    });

    it('has a link to login', () => {
      expect(this.compiled.querySelectorAll('.nav-link').item(0).textContent).toContain('Login');
    });
  });

  describe('when the user is logged in', () => {
    beforeEach(() => {
      this.authService = this.fixture.debugElement.injector.get(AuthService);

      const user = new User();
      user.firstName = 'Test';
      user.lastName = 'User';
      spyOn(this.authService, 'getLoggedInUser').and.returnValue(Observable.of(user));
      this.fixture.detectChanges();
      this.compiled = this.fixture.debugElement.nativeElement;
    });

    it('should have a link to go to home', () => {
      expect(this.compiled.querySelectorAll('.nav-link').item(0).textContent).toContain('Home');
    });

    it('should have a link to resources', () => {
      expect(this.compiled.querySelectorAll('.nav-link').item(1).textContent).toEqual('Resources');
    });

    it('should display the user\'s first and last name', () => {
      expect(this.compiled.querySelector('#fullNameDropdown').textContent.trim()).toEqual('Test User');
    });

    describe('the logout button', () => {
      beforeEach(() => {
        spyOn(this.authService, 'logout').and.returnValue(new Promise<void>((resolve) => {
          resolve();
        }));

        this.fixture.detectChanges();
        this.compiled = this.fixture.debugElement.nativeElement;

        this.logoutElement = this.compiled.querySelector('.dropdown .dropdown-item');
      });

      it('should exist', () => {
        expect(this.logoutElement.textContent).toEqual('Logout');
      });

      it('should call authService logout when clicked', () => {
        this.logoutElement.click();

        expect(this.authService.logout).toHaveBeenCalled();
      });
    });
  });
});
