# TESTCONTAINERS - SELENIDE
This project illustrates Testcontainers support for Selenide. [_Testcontainers_](https://testcontainers.com) is a framework that uses Docker containers as support to run tests against dependencies, such as databases. Therefore, you no longer rely on _Mocks_.

[_Selenide_](https://selenide.org) is a framework built on top of Selenium. [_Selenium_](https://www.selenium.dev) is a tool for browser automation. Therefore, Selenium supports running E2E tests.

In my opinion, Selenide simplifies a lot the browser automation.

> Check out [`testes-end-to-end`](https://github.com/manejo-project/testes-end-to-end.git) repo to see a practical example of Selenide used in our project in partnership with [IDR/Senar](https://www.noticiasagricolas.com.br/videos/soja/260090-novo-software-auxilia-produtores-do-parana-no-manejo-integrado-de-pragas-e-doencas.html?fbclid=IwAR1MoJ1x4MicU5K4kGY30cbOWT9wU5E1hRHqU_A8mCcA0fXFCPgjZKoVLTo#.Xs1HEi-ZNTY)

> Check out our [_hello-world project_](https://github.com/gabrielcostasilva/testcontainers-hello-world.git) to get started with Testcontainers.

## Overview
This project consist of a single test class ([AppTest](./src/test/java/com/example/AppTest.java)). The test uses Testcontainers/Selenide to run a Google search. The Figure below shows the automated process as recorded by Selenide.

<img src="./selenide-action.gif">

## The Test Class
The test class has three main parts. First, `org.testcontainers.junit.jupiter.Testcontainers` annotation identifies it as a Testcontainers test class, loading and configuring everything you need to run the test.

Second, the test class set the container used to run the test, as the code snippet below shows.

```java
(...)

@Container
    public BrowserWebDriverContainer browser = 
        new BrowserWebDriverContainer<>()
            .withRecordingMode(RECORD_ALL, Paths.get(".", "target").toFile())
            .withCapabilities(new FirefoxOptions());
(...)
```

Finally, the code snippet below shows the test that is executed.

```java
@Test
public void selenideShouldExist() {

    RemoteWebDriver remote = 
        new RemoteWebDriver(
                browser.getSeleniumAddress(), 
                new FirefoxOptions());

    WebDriverRunner.setWebDriver(remote);

    open("https://google.com?q=selenide");

    $$("form")
        .filterBy(exist)
        .first()
        .shouldBe(visible)
        .submit();

    $(byValue("selenide"))
        .shouldBe(exist)
        .shouldBe(visible)
        .submit();

}
```
In a nutshell, the test runs a Google search for _selenide_ and checks for results in the form.

> Notice that `exist`, `visible`, `$` and `$$` are [static imported properties](./src/test/java/com/example/AppTest.java).

## RemoteWebDriver
The `org.openqa.selenium.remote.RemoteWebDriver` class uses [Selenium Grid](https://www.selenium.dev/documentation/grid/) to run the tests remotely. Without Selenium Grid, the test would open a browser window ON YOUR DESKTOP, and you would feel like a ghost is operating your computer!

Luckily, the hard work is already implemented by Testcontainers and Selenide teams. Therefore, you only have to run the tests!


## Dependencies
In addition to [Testcontainers dependencies](https://github.com/gabrielcostasilva/testcontainers-hello-world#dependencies), you need Selenium (`org.testcontainers.selenium`) and Selenide dependencies (`com.codeborne.selenide`).

## Running the Project
> Be aware that running the project takes quite a while. So be patient ;)

1. **Ensures Docker Desktop is running**
2. Open your terminal, and from the project folder run `mvn test`
3. Check out the test result and the video saved at `target/` folder


