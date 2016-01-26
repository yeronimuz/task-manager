package com.lankheet.taskmanager;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = { "pretty", "html:build/cucumber/componentTestBE" },
        tags = { "~@ignore", "~@manual" }, 
        strict = false
)
public class TaskManagerTestRunner {
}
