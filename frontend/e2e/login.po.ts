import {by, element} from 'protractor';

export class LoginPage {

  static getTitleText() {
    return element(by.css('h1')).getText();
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
