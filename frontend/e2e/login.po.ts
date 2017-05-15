import {browser, by, element, protractor} from 'protractor';

export class LoginPage {

  static getTitleText() {
    let elm = element(by.css('h1'));
    browser.wait(protractor.ExpectedConditions.presenceOf(elm), 10000);
    return elm;
  }

  static getUsernameField() {
    return element(by.css('input#username'));
  }

  static getPasswordField() {
    return element(by.css('input#password'));
  }

  static getSubmitButton() {
    return element(by.buttonText('Submit'));
  }
}
