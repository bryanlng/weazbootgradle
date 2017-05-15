import {inject, TestBed} from '@angular/core/testing';
import {AuthService} from './auth.service';
import {MockBackend} from '@angular/http/testing';
import {BaseRequestOptions, Http} from '@angular/http';
import {RouterTestingModule} from '@angular/router/testing';
import {Routes} from '@angular/router';
import {LandingComponent} from '../landing/landing.component';

export const fakeRoutes: Routes = [
  {path: 'landing', component: LandingComponent}
];

describe('AuthService', () => {
  beforeEach(() => {
    // noinspection JSUnusedGlobalSymbols
    TestBed.configureTestingModule({
      declarations: [
        LandingComponent
      ],
      providers: [
        AuthService,
        MockBackend,
        BaseRequestOptions,
        {
          provide: Http,
          useFactory: (backend: MockBackend, defaultOptions: BaseRequestOptions) => {
            return new Http(backend, defaultOptions);
          },
          deps: [MockBackend, BaseRequestOptions],
        }],
      imports: [
        RouterTestingModule.withRoutes(fakeRoutes)
      ]
    });
  });

  beforeEach(inject([AuthService, MockBackend], (authService: AuthService, mockBackend: MockBackend) => {
    this.subject = authService;
    this.backend = mockBackend;
    this.backend.connections.subscribe((connection: any) => this.lastConnection = connection);
  }));

  describe('#logout', () => {
    it('should make a request to logout', () => {
      this.subject.logout();
      expect(this.lastConnection.request.url).toEqual('logout');
    });
  });
});
