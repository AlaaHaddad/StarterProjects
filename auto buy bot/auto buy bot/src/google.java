import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class google {
    public void stop() {}
    public boolean sad() {return true;}
    public void beAwesome() {}
    public void s() {

       //life motto
        if (sad()) {
            stop();
            beAwesome();
        }

    }


    final int[] arr = new int[] {1,2};
        public static void main(String[] args) throws IOException {
            System.setProperty("webdriver.chrome.driver", "C:\\software\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
//            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--enable-javascript");
            options.addArguments("--enable-automation");
            options.addArguments("--disable-extensions");
            options.addArguments("--disable-infobars");
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
            options.addArguments("user-data-dir=C:\\Users\\Alaa Haddad\\AppData\\Local\\Google\\Chrome\\User Data\\Default");
            ChromeDriver chrome = new ChromeDriver(options);//////
            chrome.get("https://www.footlocker.co.il/release");
        }
    }
