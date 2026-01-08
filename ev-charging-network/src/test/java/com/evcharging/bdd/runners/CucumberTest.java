package com.evcharging.bdd.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue = "com.evcharging.bdd.steps",
        plugin = {"pretty"}
)
public class CucumberTest {
}