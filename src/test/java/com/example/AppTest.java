package com.example;


import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;


@Testcontainers
public class AppTest {

    @Container
    public BrowserWebDriverContainer browser = 
        new BrowserWebDriverContainer<>()
            .withRecordingMode(
                RECORD_ALL, 
                Paths.get(".", "target").toFile())
            .withCapabilities(new FirefoxOptions());

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

}
