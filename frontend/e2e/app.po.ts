import {browser, by, element, protractor} from 'protractor';

export class WeazbootgradlePage {
  static navigateTo() {
    return browser.get('/');
  }

  static getHeader() {
    return element(by.css('h1'));
  }

  static getParagraphText() {
    return element(by.css('main p'));
  }

  static getLoginLink() {
    this.toggleCollapsedNavbarIfWindowIsSmall();

    let elm = element(by.cssContainingText('.nav-link', 'Login'));
    browser.wait(protractor.ExpectedConditions.elementToBeClickable(elm), 10000);
    return elm;
  }

  static getLoggedInUserFullNameDropdown() {
    this.toggleCollapsedNavbarIfWindowIsSmall();

    let elm = element(by.css('a#fullNameDropdown'));
    browser.wait(protractor.ExpectedConditions.elementToBeClickable(elm), 10000);
    return elm;
  }

  static getLogoutLink() {
    let loggedInUserFullName = this.getLoggedInUserFullNameDropdown();
    loggedInUserFullName.getAttribute('aria-expanded')
      .then(function (expanded) {
        if (expanded === 'false') {
          loggedInUserFullName.click();
        }
      });

    let elm = element(by.css('a#logout'));
    browser.wait(protractor.ExpectedConditions.elementToBeClickable(elm), 10000);
    return elm;
  }

  static getResourceLink() {
    this.toggleCollapsedNavbarIfWindowIsSmall();
    let elm = element(by.cssContainingText('a', 'Resources'));
    browser.wait(protractor.ExpectedConditions.elementToBeClickable(elm), 10000);
    return elm;
  }

  private static toggleCollapsedNavbarIfWindowIsSmall() {
    let elm = element(by.css('button.navbar-toggler'));
    if (elm.isPresent()) {
      browser.wait(protractor.ExpectedConditions.elementToBeClickable(elm), 10000);
      elm.getAttribute('aria-expanded')
        .then(function (expanded) {
          if (expanded === 'false') {
            elm.click();
          }
        });
    }
  }
}
