package br.sp.edimilsonestevam.cucumberTest;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/java/br/sp/edimilsonestevam/feature"}
                , glue = { "br/sp/edimilsonestevam/stepDefinition" }
				, plugin = {"html:target/cucumber-reports"}
				, dryRun = false
				, monochrome = true)

public class TestSuite {

}



