import org.apache.commons.lang.time.StopWatch;
import org.json.JSONArray;
import org.json.JSONObject;
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

    public String username = System.getProperty("LT_USERNAME");
    public String accesskey = System.getProperty("LT_ACCESS_KEY");
    public RemoteWebDriver driver;
    public String gridURL = "hub.lambdatest.com";
    String status;
    String hub;
    SessionId sessionId;
    String buildName = null;
    DesiredCapabilities caps;
    String BrowserValue = null;
    String PlatformValue = null;
    String BrowserVersion = null;
    String Resolution = null;


    @BeforeTest
    public void setUp() throws Exception {


        //  Collection c= new ArrayList(System.getenv("LT_BROWSERS"));

      /*  for (int i = 0; i < browsers.length; i++) {//length is the property of the array
            System.out.println(browsers[i]);
            System.out.println("Value of I===================================================="+i);
        }*/
//        for (String s:browsers) {
//            System.out.println(s);
//        }

        try {
            String browsers = System.getenv("LT_BROWSERS");

            JSONArray array = new JSONArray(browsers);

            for (int i = 0; i < array.length(); i++) {

                JSONObject object = array.getJSONObject(i);
                //if (browsers.matches("Chrome") || browsers.matches("Firefox")) {
                buildName = "INternetStoreLambdaparallel";
                caps.setCapability("build", buildName);
                caps.setCapability("name", "Demo");
                caps.setCapability("platform", object.getString("operatingSystem"));
                caps.setCapability("browserName", object.getString("browserName"));
                caps.setCapability("version", object.getString("browserVersion"));
                caps.setCapability("resolution", object.getString("resolution"));
                // }

                caps.setCapability("network", true);
                caps.setCapability("visual", false);
                caps.setCapability("console", "error");
                caps.setCapability("headless", false);

                //  caps.setCapability("driver_version", "3.141.59");

                StopWatch driverStart = new StopWatch();
                driverStart.start();

                hub = "https://" + username + ":" + accesskey + "@" + gridURL + "/wd/hub";

                driver = new RemoteWebDriver(new URL(hub), caps);
                DesktopScript();
                tearDown();
                sessionId = driver.getSessionId();
                System.out.println(sessionId);
                driverStart.stop();
                float timeElapsed = driverStart.getTime() / 1000f;
                System.out.println("Driver initiate time" + "   " + timeElapsed);
                ArrayList<Float> TotalTimeDriverSetup = new ArrayList<Float>();
                TotalTimeDriverSetup.add(timeElapsed);
                System.out.println(TotalTimeDriverSetup);
            }

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

