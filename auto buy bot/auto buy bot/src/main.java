import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;



public class main {
    public static void main(String[] args) throws InterruptedException {
        footlocker("https://www.footlocker.co.il/NIKE/Nike_Dunk_Low_%D7%A1%D7%A0%D7%99%D7%A7%D7%A8%D7%A1_%D7%A0%D7%A9%D7%99%D7%9D/DD1503/prd/6086d89e75e75b05a5d8a88f?style=DD1503-101","Alaa", "Haddad", "boy_2000.ah@hotmail.com", "0508802478", "goerge sameh waze", "A602", "תל אביב - יפו", "broshim dorms", "Alaa Haddad", "1234234534564567", "211706460", "123", 27, 11);
    }
    public static void footlocker(String LinkForShoe, String name, String lastname, String email, String phone, String adress, String house, String city, String note, String fullname, String card, String ID, String cvv, int expYear, int expMonth) throws InterruptedException {
        for(int ws = 0;ws<5;ws++) {
            try {
                System.setProperty("webdriver.gecko.driver", "C:\\software\\geckodriver.exe");
                WebDriver chrome = new FirefoxDriver();
                JavascriptExecutor js = (JavascriptExecutor) chrome;
                WebDriverWait waiter = new WebDriverWait(chrome, 4);
                Actions builder = new Actions(chrome);
                WebElement search;
//                js.executeScript("window.location.href = '"+LinkForShoe+"'"); //footlocker-->upcoming-->shoe
                js.executeScript("window.location.href = 'https://www.footlocker.co.il/ADIDAS/adidas_Graphics_Y2K_%D7%97%D7%95%D7%9C%D7%A6%D7%AA_%D7%98%D7%99_%D7%A9%D7%99%D7%A8%D7%98_%D7%9C%D7%92%D7%91%D7%A8%D7%99%D7%9D/HC7184/prd/61d66870a5a13f9d1803467c?style=HC7184&cid=60d992cb1b27440009fcddb7'");
                Thread.sleep(7000);
                int i = 0;
                int t = 0;
                while (i == 0) {
                    try {
                        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.col-6:nth-child(1) > app-product-main:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(4) > div:nth-child(1) > div:nth-child(3)"))).click();//size
                        js.executeScript("window.scrollBy(0,250)");
                        waiter.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div.col-6:nth-child(1) > app-product-main:nth-child(1) > div:nth-child(1) > div:nth-child(3) > div:nth-child(3) > div:nth-child(2) > div:nth-child(1)"))).click();//buy
                        while (true) {
                            Thread.sleep(2000);
                            t++;
                            if (chrome.getCurrentUrl().equals("https://www.footlocker.co.il/buynow")) {
                                i = 1;
                                break;
                            } else if (t == 5) {
                                throw new Exception();
                            }
                        }
                    } catch (Exception s) {
                        chrome.navigate().refresh();
                        i = 0;
                    }
                }
                while (true) {
                    try {
                        Thread.sleep(300);
                        search = chrome.findElement(By.id("email-input"));//email
                        break;
                    } catch (Exception e) {}
                }
                search.click();
                search.sendKeys(email);
                chrome.findElement(By.cssSelector(".dc-text")).click();//eshur
                builder.moveToElement(chrome.findElement(By.cssSelector("#policy-checkbox > label:nth-child(2)")), 65, 10).click().build().perform();//privacy
                search = chrome.findElement(By.cssSelector("#collapseOne > form > div.row > div:nth-child(1) > input"));//name
                search.click();
                search.sendKeys(name);
                search = chrome.findElement(By.cssSelector("#collapseOne > form > div.row > div:nth-child(2) > input"));//last name
                search.click();
                search.sendKeys(lastname);
                search = chrome.findElement(By.cssSelector("#collapseOne > form > div.row > div:nth-child(3) > input"));//street
                search.click();
                search.sendKeys(adress);
                search = chrome.findElement(By.cssSelector("#collapseOne > form > div.row > div:nth-child(5) > input"));//house
                search.click();
                search.sendKeys(house);
                search = chrome.findElement(By.cssSelector("#collapseOne > form > div.row > div:nth-child(9) > input"));//city
                search.click();
                search.sendKeys(city);
                search = chrome.findElement(By.cssSelector("#collapseOne > form > div.row > div:nth-child(10) > input"));//phone number
                search.click();
                search.sendKeys(phone);
                search = chrome.findElement(By.cssSelector("#collapseOne > form > div.row > div.form-group.col-12.col-sm-12.col-md-12 > textarea"));//side note
                search.click();
                search.sendKeys(note);
                chrome.findElement(By.cssSelector("div.col-md-4:nth-child(13) > button:nth-child(1)")).click();//eshur
                js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                waiter.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector(".iframe")));
                while (true) {
                    try {
                        Thread.sleep(500);
                        search = chrome.findElement(By.id("userData1"));//cardholder name
                        break;
                    }catch (Exception e) {System.out.println(e.getMessage());}
                }
                search.click();
                search.sendKeys(fullname);
                search = chrome.findElement(By.id("personalId"));//ID
                search.click();
                search.sendKeys(ID);
                search = chrome.findElement(By.id("Track2CardNo")); // visa number
                search.click();
                search.sendKeys(card);
                search = chrome.findElement(By.id("cvv")); //cvv
                search.click();
                search.sendKeys(cvv);
                chrome.findElement(By.cssSelector("#expYear")).click(); //year
                switch (expYear) { // choose year
                    case 22:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(3)")).click();// choose 22
                        break;
                    case 23:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(4)")).click();// choose 23
                        break;
                    case 24:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(5)")).click();// choose 24
                        break;
                    case 25:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(6)")).click();// choose 25
                        break;
                    case 26:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(7)")).click();// choose 26
                        break;
                    case 27:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(8)")).click();// choose 27
                        break;
                    case 28:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(9)")).click();// choose 28
                        break;
                    case 29:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(10)")).click();// choose 29
                        break;
                    case 30:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(11)")).click();// choose 30
                        break;
                    case 31:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(12)")).click();// choose 31
                        break;
                    case 32:
                        chrome.findElement(By.cssSelector("#expYear > option:nth-child(13)")).click();// choose 32
                        break;
                }
                chrome.findElement(By.cssSelector("#expMonth")).click();//month
                switch (expMonth) {
                    case 1:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(3)")).click();//choose 1
                        break;
                    case 2:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(4)")).click();//choose 2
                        break;
                    case 3:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(5)")).click();//choose 3
                        break;
                    case 4:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(6)")).click();//choose 4
                        break;
                    case 5:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(7)")).click();//choose 5
                        break;
                    case 6:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(8)")).click();//choose 6
                        break;
                    case 7:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(9)")).click();//choose 7
                        break;
                    case 8:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(10)")).click();//choose 8
                        break;
                    case 9:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(11)")).click();//choose 9
                        break;
                    case 10:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(12)")).click();//choose 10
                        break;
                    case 11:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(13)")).click();//choose 11
                        break;
                    case 12:
                        chrome.findElement(By.cssSelector("#expMonth > option:nth-child(14)")).click();//choose 12
                        break;
                }
                chrome.findElement(By.cssSelector("#submitBtn")).click(); // eshur
                break;
            } catch (Exception e) {}
        }
    }
}
