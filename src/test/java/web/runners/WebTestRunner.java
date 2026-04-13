package web.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/web",

        glue = "web.steps",

        tags = "@web",

        plugin = {
                "html:build/reports/cucumber/web/web-report.html",
                "json:build/reports/cucumber/web/web-report.json",
                "pretty"
        },

        publish = false
)
public class WebTestRunner {
}