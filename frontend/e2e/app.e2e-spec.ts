import {WeazbootgradlePage} from './app.po';
import {LoginPage} from './login.po';
import {browser} from 'protractor';

describe('Weazbootgradle App', () => {
  let page: WeazbootgradlePage;

  beforeEach(() => {
    page = new WeazbootgradlePage();
  });

  it('should take you to the landing page', () => {
    WeazbootgradlePage.navigateTo();

    expect<any>(WeazbootgradlePage.getHeader().getText()).toEqual('Weazbootgradle');
    expect(WeazbootgradlePage.getLoginLink().isPresent()).toBeTruthy();
  });

  describe('clicking the login link', () => {
    beforeAll(() => {
      WeazbootgradlePage.getLoginLink().click();
      browser.waitForAngularEnabled(false);
    });

    afterAll(() => {
      browser.waitForAngularEnabled(true);
    });

    it('takes you to a login form', () => {
      expect<any>(LoginPage.getTitleText().getText()).toEqual('Weazbootgradle Authentication Server');
      expect(LoginPage.getUsernameField().isPresent()).toBeTruthy();
      expect(LoginPage.getPasswordField().isPresent()).toBeTruthy();
    });

    describe('filling out and submitting the form', () => {
      beforeAll(() => {
        LoginPage.getUsernameField().sendKeys('username');
        LoginPage.getPasswordField().sendKeys('password');
        LoginPage.getSubmitButton().click();
        browser.waitForAngularEnabled(true);
      });

      it('takes you to back to the landing page', () => {
        expect<any>(browser.getCurrentUrl()).toBe('http://localhost:9000/#/home');
        expect<any>(WeazbootgradlePage.getHeader().getText()).toEqual('Weazbootgradle');

        expect<any>(WeazbootgradlePage.getParagraphText().getText()).toEqual('home really works!');

      });

      it('shows the user name with a logout link', () => {
        expect(WeazbootgradlePage.getLoggedInUserFullNameDropdown().getText()).toContain('MyFirstName MyLastName');
        expect(WeazbootgradlePage.getLogoutLink().isPresent()).toBeTruthy();
      });

      it('has a button to click for resources', () => {
        expect(WeazbootgradlePage.getResourceLink().isPresent()).toBeTruthy();
      });

      describe('clicking the resource button', () => {
        beforeAll(() => {
          WeazbootgradlePage.getResourceLink().click();
        });

        it('displays some information from the backend', () => {
          expect<any>(browser.getCurrentUrl()).toBe('http://localhost:9000/#/resources');
          expect<any>(WeazbootgradlePage.getHeader().getText()).toEqual('Weazbootgradle');

          expect<any>(WeazbootgradlePage.getParagraphText().getText()).toEqual('resources works!');
        });
      });

      describe('clicking logout', () => {
        beforeAll(() => {
          WeazbootgradlePage.getLogoutLink().click();
        });

        it('takes you back to the landing page', () => {
          expect<any>(browser.getCurrentUrl()).toBe('http://localhost:9000/#/landing');
          expect<any>(WeazbootgradlePage.getHeader().getText()).toEqual('Weazbootgradle');

          expect<any>(WeazbootgradlePage.getParagraphText().getText()).toEqual('landing works!');
        });

        describe('refreshing multiple times', () => {
          beforeAll(() => {
            browser.refresh();
            browser.refresh();
            browser.refresh();
          });

          it('does not log the user back in', () => {
            expect<any>(browser.getCurrentUrl()).toBe('http://localhost:9000/#/landing');
            expect<any>(WeazbootgradlePage.getHeader().getText()).toEqual('Weazbootgradle');

            expect<any>(WeazbootgradlePage.getParagraphText().getText()).toEqual('landing works!');
          });
        });
      });
    });
  });
});
