import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class demoDesktop {

    public String username = System.getProperty("");
    public String accesskey = System.getProperty("");
    public RemoteWebDriver driver;
    public String gridURL = "hub.lambdatest.com";
    String status;
    String hub;
    SessionId sessionId;
    String buildName = null;


    @BeforeTest
    public void setUp() throws Exception {
        try {
            buildName = "Demo Failed Test";
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability("build", buildName);
            caps.setCapability("name", "Demo");
            caps.setCapability("platform", System.getenv("LT_OPERATING_SYSTEM"));
            caps.setCapability("browserName", System.getenv("LT_BROWSER_NAME"));
            caps.setCapability("version", System.getenv("LT_BROWSER_VERSION"));
            caps.setCapability("resolution", System.getenv("LT_RESOLUTION"));
            caps.setCapability("network", true);
            caps.setCapability("visual", false);
            caps.setCapability("console", "error");
            caps.setCapability("headless", false);

            //  caps.setCapability("driver_version", "3.141.59");

            StopWatch driverStart = new StopWatch();
            driverStart.start();

            hub = "https://" + username + ":" + accesskey + "@" + gridURL + "/wd/hub";

            driver = new RemoteWebDriver(new URL(hub), caps);

            sessionId = driver.getSessionId();
            System.out.println(sessionId);
            driverStart.stop();
            float timeElapsed = driverStart.getTime() / 1000f;
            System.out.println("Driver initiate time" + "   " + timeElapsed);
            ArrayList<Float> TotalTimeDriverSetup = new ArrayList<Float>();
            TotalTimeDriverSetup.add(timeElapsed);
            System.out.println(TotalTimeDriverSetup);


        } catch (
                MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception f) {
            System.out.println(f);

        }

    }

    @Test
    public void DesktopScript() {
        try {
            ToDo test = new ToDo();
            test.ToDo(driver, status);
        } catch (Exception e) {

            System.out.println(e);
        }
    }


    @AfterTest
    public void tearDown() throws Exception {
        if (driver != null) {

            driver.quit();
        }
    }
}

