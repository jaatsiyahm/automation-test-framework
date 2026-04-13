package api.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/api",

        glue = "api.steps",

        tags = "@api",

        plugin = {
                "html:build/reports/cucumber/api/api-report.html",

                "json:build/reports/cucumber/api/api-report.json",

                "pretty"
        },

        publish = false
)
public class ApiTestRunner {
}