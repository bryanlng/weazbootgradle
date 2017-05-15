[![Gradle](./docs/images/gradle-logo.png)](https://gradle.org/) [![Angular](./docs/images/angular-logo.png)](https://angular.io/)
[![OAuth2](./docs/images/oauth2-logo.png)](https://tools.ietf.org/html/rfc6749) [![Spring Boot](./docs/images/spring-logo.png)](http://projects.spring.io/spring-boot/)
# Weazbootgradle
Tool | Status
 --- | ---
| Travis  |  [![Build Status](https://travis-ci.org/themadweaz/weazbootgradle.svg?branch=master)](https://travis-ci.org/themadweaz/weazbootgradle) |
| Jenkins | [![Build Status](http://jenkins.weaz.net/buildStatus/icon?job=Weazboot/master)](http://jenkins.weaz.net/job/weazboot/) |
| Java | [![Coverage Status](https://coveralls.io/repos/github/themadweaz/weazbootgradle/badge.svg)](https://coveralls.io/github/themadweaz/weazbootgradle) |

## Introduction
**Weazbootgradle** is a starter **[Spring Boot](http://projects.spring.io/spring-boot/)** / **[Angular](https://angular.io/)** project with many features 
designed to be used with **[Intellij IDE](https://www.jetbrains.com/idea/)**.  
It uses **[Gradle](https://gradle.org/)** as the main build tool with **[Angular CLI](https://cli.angular.io/)** for the frontend.  It is cross platform, 
**[travis-ci](https://travis-ci.org/)** and **[Jenkins](https://jenkins.io/)** friendly, and loaded with multiple testing frameworks.  It comes pre-configured with 
**[Intellij IDE](https://www.jetbrains.com/idea/)** configuration settings so that you can get started quickly.

The entire build lifecycle for this project can be run in a single: ``./gradlew build`` command.

## Goal
A frontend application secured by [OAuth2](https://tools.ietf.org/html/rfc6749) authorization code flow, with an example OAuth2 authentication server.  The 
authentication server could be used in an organization, replaced by an existing service (Google, Facebook, Okta, etc), or extended to integrate with other OAuth2 providers.

It should have staples such as login, logout, backend requests, etc in addition to being fully tested and easy to scale.

## Setup
In your workspace directory:
```Shell
git clone https://github.com/themadweaz/weazbootgradle.git
```

Then open the project directly in **[IntelliJ IDE](https://www.jetbrains.com/idea/)**.  *Do not Import*, **Open**.
Import would work, but you would lose many of the **[IntelliJ IDE](https://www.jetbrains.com/idea/)** specific configuration features of this sample.
 
Once that is done, either run the `./gradlew build` from the command line or the `build` *Run Configuration* in  **[IntelliJ IDE](https://www.jetbrains.com/idea/)**.
This will pull down all dependencies, install **[Node.js](https://nodejs.org)** and **[npm](https://www.npmjs.com/)**, and run the test suite.

See each module's README.md for more information about commands available.

#### Optional
- Load pre-configured settings for **[IntelliJ IDE](https://www.jetbrains.com/idea/)** by importing the settings jar in `$PROJECT_ROOT/config/intellij/settings-*.jar`
using `File->Import Settings...`.  Use at your own risk, as this will overwrite your own **[IntelliJ IDE](https://www.jetbrains.com/idea/)** settings.

## Tooling
### Java
#### Spring Boot
The project comes with a pretty sane **[Spring Boot](http://projects.spring.io/spring-boot/)** multi-app setup.  It contains a main application that serves the Angular app,
and an authorization server example app.  It is designed to be extended, and has classes prefixed with the word *Custom* which are targets for enhancement.

#### Spring Security (OAuth2)
Instead of the basic authentication default [Spring Security](https://projects.spring.io/spring-security/) configuration, this project comes with an
**[OAuth2](http://oauth.net/2/)** authentication server implementation which secures the frontend.  It supports token revocation and session based logout.
It follows the example by Dave Syer from [this github](https://github.com/spring-guides/tut-spring-boot-oauth2) with lots of modifications.

The default username/password is: `username/password`

#### Testing
Everything is tested using the most modern **[Spring Boot](http://projects.spring.io/spring-boot/)** patterns.  It has [JaCoCo](http://www.eclemma.org/jacoco/) coverage tooling
to monitor how well the application is tested.  Also included is an example integration with [Coveralls](https://coveralls.io/) to record that coverage over time.

#### Devtools
Pre-configured with **[Spring Developer Tools](http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html)** for hot-swapping classes and resources.

#### Checkstyle
The build runs **[Checkstyle](http://checkstyle.sourceforge.net/)** on the code base as part of the build lifecycle.  It is pre-configured with a modified version
of [Google's checkstyle configuration](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml).
The **[IntelliJ IDE](https://www.jetbrains.com/idea/)** project is configured to be able to run this checkstyle variant
from the Checkstyle **[IntelliJ IDE](https://www.jetbrains.com/idea/)** plugin.  The project contains a Code Style XML configuration that matches the style rules in the 
checkstyle configurations.  Can be easily modified as needed.

### Javascript
#### Node / NPM Install
Using a few **[Gradle](https://gradle.org/)** plugins, the first ``./gradlew build`` run will install **[Node.js](https://nodejs.org)** and pull down
any **[npm](https://www.npmjs.com/)** dependencies defined in _Package.json_.  If you already have **[Node.js](https://nodejs.org)** installed, it will use your local version.

#### Grunt
We use **[Grunt](http://gruntjs.com/)** to help stand up the jars and then run  ``ng test`` from the **[Angular CLI](https://cli.angular.io/)**.  

#### Testing
**[Angular CLI](https://cli.angular.io/)** includes tools for running both unit and end2end types of testing.  More information can be found on in the [Frontend README](
./frontend/README.md).


## Contributing:
I will happily take pull requests.  Please open an issue first!

## TODO:
* Users: roles, actuator for both applications
* auth store / authorize page for clients requiring authorization
* scope abstraction
* possibly saml integration

### IntelliJ / Devtools
To get devtools working correctly on class changes, you need to do a few things.  First, you will need to go to `Settings -> Compiler` and enable the option `Make Project 
Automatically`.  Next, you will need to enable the `compiler.automake.allow.when.app.running` registry setting in **[IntelliJ IDE](https://www.jetbrains.com/idea/)**.  
You can access the registry in by using the shortcut `Shift + Command + A`, then searching for `registry`.  Set this value to `true`.
