package ru.yandex.praktikum.test;

import ru.yandex.praktikum.page.MainPage;

import org.hamcrest.MatcherAssert;
import static org.hamcrest.CoreMatchers.containsString;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;


@RunWith(Parameterized.class)
public class FaqAccordionTest {

    private WebDriver driver;
    private final String faqAccordionItemId;
    private final boolean faqAccordionItemPanelHiddenExpected; //true - element is hidden, false - element isn't hidden
    private final String faqAccordionItemPanelTextExpected;

    // Локаторы
    public static final String ACCORDION_HEADING_0 = "accordion__heading-0";
    public static final String ACCORDION_HEADING_1 = "accordion__heading-1";
    public static final String ACCORDION_HEADING_2 = "accordion__heading-2";
    public static final String ACCORDION_HEADING_3 = "accordion__heading-3";
    public static final String ACCORDION_HEADING_4 = "accordion__heading-4";
    public static final String ACCORDION_HEADING_5 = "accordion__heading-5";
    public static final String ACCORDION_HEADING_6 = "accordion__heading-6";
    public static final String ACCORDION_HEADING_7 = "accordion__heading-7";

    public FaqAccordionTest(String accordionItemId, boolean faqAccordionItemPanelHiddenExpected, String faqAccordionItemPanelText){
        this.faqAccordionItemId = accordionItemId;
        this.faqAccordionItemPanelHiddenExpected = faqAccordionItemPanelHiddenExpected;
        this.faqAccordionItemPanelTextExpected = faqAccordionItemPanelText;
    }

    @Parameterized.Parameters
    public static Object[][] getInputData() {
        return new Object[][] {
                {ACCORDION_HEADING_0, false, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {ACCORDION_HEADING_1, false, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {ACCORDION_HEADING_2, false, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {ACCORDION_HEADING_3, false, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {ACCORDION_HEADING_4, false, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {ACCORDION_HEADING_5, false, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {ACCORDION_HEADING_6, false, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {ACCORDION_HEADING_7, false, "Да, обязательно. Всем самокатов! И Москве, и Московской области."}

                //случайно слил ветки. этот комментарий для отобрадения изменений
        };
    }

    @Before
    public void startUp() {
        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void checkAccordionExpand() {
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");

        MainPage objMainPage = new MainPage(driver, faqAccordionItemId);
        //Cookie
        objMainPage.clickCookieAcceptButton();
        //Go to FAQ accordeon's item and click
        objMainPage.clickFaqAccordionItemHeading();
        //Check panel is visible
        WebElement element = objMainPage.getFaqAccordionItemPanel();
        boolean visibleActual = Boolean.parseBoolean(element.getAttribute("hidden"));
        assertEquals(visibleActual, faqAccordionItemPanelHiddenExpected);
//        //Check panel text
        String textActual = objMainPage.getFaqAccordionItemPanelText().getText();
        MatcherAssert.assertThat(textActual, containsString(faqAccordionItemPanelTextExpected));
    }

    @After
    public void teardown() {
        driver.quit();
    }
}