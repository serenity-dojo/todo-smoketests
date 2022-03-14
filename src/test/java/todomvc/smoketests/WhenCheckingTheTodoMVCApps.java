package todomvc.smoketests;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.annotations.CastMember;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.SearchableTarget;
import net.serenitybdd.screenplay.ui.PageElement;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.SingleBrowser;
import net.thucydides.core.util.EnvironmentVariables;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.Locale;

@SingleBrowser
@ExtendWith(SerenityJUnit5Extension.class)
public class WhenCheckingTheTodoMVCApps {

    public static final SearchableTarget APP_TITLE = PageElement.located(By.tagName("h3"));

    @CastMember
    Actor todd;

    EnvironmentVariables environmentVariables;

    @ParameterizedTest
    @CsvSource( {
            "pages.vue,           Vue.js",
            "pages.react,         React",
            "pages.polymer,       Polymer",
    })
    void shouldOpenTheTodoMVCAppInAngular(String startPage, String expectedTitle) {

        String currentDomain = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty(startPage);

        todd.attemptsTo(
                Open.browserOn().thePageNamed(startPage),
                Ensure.that(PageElement.withCSSClass("new-todo")).isDisplayed(),
                Ensure.that(APP_TITLE).textContent().isEqualTo(expectedTitle)
        );
    }


    @Test
    void shouldOpenTheTodoMVCAppInReact() {
        todd.attemptsTo(
                Open.url("https://todomvc.com/examples/react/#/"),
                Ensure.that(PageElement.withCSSClass("new-todo")).isDisplayed()
        );
    }

    @Test
    void shouldOpenTheTodoMVCAppInPolymer() {
        todd.attemptsTo(
                Open.url("https://todomvc.com/examples/polymer/index.html"),
                Ensure.that(PageElement.withCSSClass("new-todo")).isDisplayed()
        );
    }
}
