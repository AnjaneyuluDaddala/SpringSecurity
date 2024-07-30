package com.devdolphins.playwrightTesting;

import java.nio.file.Paths;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Tracing;
import com.microsoft.playwright.options.AriaRole;

public class FirstLogin {

    public static void main(String[] args) {
        try(Playwright playwright=Playwright.create()){
            Browser browser=playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false) );
             BrowserContext admiContext =browser.newContext();
 
            // Start tracing before creating / navigating a page.
                admiContext.tracing().start(new Tracing.StartOptions()
                .setScreenshots(false)
                .setSnapshots(false)
                .setSources(false));


            Page page=admiContext.newPage();
            page.navigate("http://localhost:3002/welcome");
            page.getByText("Welcome to DevDolphins Please").click();
            page.getByLabel("Employee ID:").click();
            page.getByLabel("Employee ID:").fill("DEDOL-0001");
            page.getByLabel("Password:").fill("admin@123");
            page.pause();
            page.waitForTimeout(3000);
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
            page.waitForTimeout(3000);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Add Employee")).click();
            page.waitForTimeout(3000);
            page.navigate("http://localhost:3002/admin/home");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("All Employees List")).click();
            page.waitForTimeout(3000);
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Employee List")).click();
            page.navigate("http://localhost:3002/admin/home");
            page.waitForTimeout(4000);
 

           BrowserContext userContext= browser.newContext();

           Page page2=userContext.newPage();
           page2.navigate("http://localhost:3002/welcome");
           page2.fill("#username", "DEDOL-0002");
           System.out.println(page2.title());
           page2.fill("#password", "user@123");
           System.out.println(page2.title());
           page2.waitForTimeout(3000);
           page2.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
           page2.navigate("http://localhost:3002/user/home");
           page2.pause();


         // Stop tracing
         admiContext.tracing().stop(new Tracing.StopOptions()
          .setPath(Paths.get("trace.zip")));

            //userContext.close();
            //admiContext.close();
            //
            // browser.close();
            // playwright.close();

        }
    }

}

//Debugging (through inspector) line by line
//Run configuration and then add enviornment 
//PWDEBUG(variable)
//value(1)
//while recording also when can stop and get all the types of DOM generates .
