package md.homeworks.lesson5;

import org.assertj.core.api.SoftAssertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AddWordsToTheDictionaryTest extends BaseTest {

//  Тестовые данные

    public static Stream<Word> newWords() {
        return Stream.of(new Word("box", "[ bɒks ]","коробка"),
                new Word("pen", "[ pen ]","ручка"),
                new Word("doll", "[ dɒl ]", "кукла"),
                new Word("bag", "[ bæɡ ]", "сумка"));
    }

    List<Word> newWords = Arrays.asList(new Word("box", "[ bɒks ]","коробка"),
            new Word("pen", "[ pen ]","ручка"),
            new Word("doll", "[ dɒl ]", "кукла"),
            new Word("bag", "[ bæɡ ]", "сумка"));

    @ParameterizedTest
    @MethodSource("newWords")
    @DisplayName("Добавление нового слова в словарь: дубли слов допускаются, приведение к нижнему регистру выключено")
    void addNewWordToTheDictionaryTest(Word word) throws InterruptedException {

        webDriver.get("https://myefe.ru/wp-login.php?redirect_to=https%3A%2F%2Fmyefe.ru");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

//      Авторизация
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();

//      Переходим на страницу с заранее созданным словарем
        webDriver.get("https://myefe.ru/pd/edit/117560");
        webDriver.findElement(By.xpath("//div[@class='mld-edit-top-panel']//button[text()='Добавить слова']")).click();
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui segment mld-add-words']")));

//      Добавляем слово в поле ввода
        webDriver.findElement(By.id("mld-add-words-textarea")).sendKeys(word.getWord());

//      Проверка, что добавляемое слово найдено системой
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
        assertThat(webDriver.findElement(By.xpath("//table//td[@class='five wide word value']"))
                .getText())
                .isEqualTo(word.getWord());

//      Проверка, что перевод к добавляемому слову подобран правильно
        assertThat(webDriver.findElement(By.xpath("//td[contains(text(),'" + word.getWord() + "')]/.."))
                            .findElement(By.xpath(".//td[@class='nine wide word transl']"))
                            .getText())
                    .contains(word.getTranslation());
        webDriver.findElement(By.xpath("//button[text()='Добавить в словарь']")).click();

//      Проверка, что в окне сообщения об успешном добавлении слова есть добавляемое слово
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()=' Слова добавлены']")));

        assertThat(webDriver.findElement(By.xpath("//div[@class='ui bulleted list']//div[@role='listitem']"))
                .getText()               // Получаем строку
                .split(" - ")[0])  //  Берем первое слово
                .isEqualTo(word.getWord());

        webDriver.findElement(By.xpath("//button[text()='Закрыть']")).click();

//      Проверка, что добавленное слова есть в списке слов словаря
        List<String> addedWordsFromDictionary = webDriver.findElements(By.xpath("//div[@class='four wide column mld-value-col mld-word']//span[@class='mld-text']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertThat(addedWordsFromDictionary)
                .contains(word.getWord());

//      Проверка, что транскрипция подобрана системой правильно
        assertThat(webDriver.findElement(By.xpath("//span[text()='" + word.getWord() + "']/ancestor::div[@class='row mld-word-row']//div[@class='middle aligned four wide column mld-value-col']/span"))
                            .getText())
                    .isEqualTo(word.getTranscription());
        //Thread.sleep(5000);
    }

    @Test
    @DisplayName("Добавление нескольких новых слов в словарь: дубли слов допускаются, приведение к нижнему регистру выключено")
    void addNewWordsToTheDictionaryTest() throws InterruptedException {

        webDriver.get("https://myefe.ru/wp-login.php?redirect_to=https%3A%2F%2Fmyefe.ru");
        webDriver.manage().window().setSize(new Dimension(1500, 1100));
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);  // Неявное ожидание (после каждого шага)

//      Авторизация
        webDriver.findElement(By.name("log")).sendKeys("mariapuzakova");
        webDriver.findElement(By.name("pwd")).sendKeys("K0G8MhIrUzHq");
        webDriver.findElement(By.name("wp-submit")).click();

//      Переходим на страницу с заранее созданным словарем
        webDriver.get("https://myefe.ru/pd/edit/117560");
        webDriver.findElement(By.xpath("//div[@class='mld-edit-top-panel']//button[text()='Добавить слова']")).click();
        new WebDriverWait(webDriver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='ui segment mld-add-words']")));

//      Добавление новых слов в поле ввода
        WebElement inputField = webDriver.findElement(By.id("mld-add-words-textarea"));
        for (Word word : newWords) {
            inputField.sendKeys(word.getWord() + Keys.ENTER);
        }

//      Проверка, что все добавляемые слова найдены системой
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.tagName("table")));
        List<String> addedWords = webDriver.findElements(By.xpath("//table//td[@class='five wide word value']"))
                        .stream()
                        .map(WebElement::getText)
                        .collect(Collectors.toList());
        List<String> newNameWords = newWords.stream()
                .map(Word::getWord)
                .collect(Collectors.toList());
        assertThat(addedWords).containsExactlyInAnyOrderElementsOf(newNameWords);

//      Проверка, что переводы к добавляемым словам подобраны правильно
        SoftAssertions softAssertionsBefore = new SoftAssertions();
        for (Word word : newWords) {
            softAssertionsBefore.assertThat(webDriver.findElement(By.xpath("//td[contains(text(),'" + word.getWord() + "')]/.."))
                            .findElement(By.xpath(".//td[@class='nine wide word transl']"))
                            .getText())
                    .contains(word.getTranslation());
        }
        softAssertionsBefore.assertAll();

        webDriver.findElement(By.xpath("//button[text()='Добавить в словарь']")).click();

//      Проверка, что в окне сообщения об успешном добавлении слов список слов верный
        new WebDriverWait(webDriver,5).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()=' Слова добавлены']")));

        List<String> addedWordsAfter = webDriver.findElements(By.xpath("//div[@class='ui bulleted list']//div[@role='listitem']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        String[] firstWords = new String[addedWordsAfter.size()];
            for (int i = 0; i < addedWordsAfter.size(); i++) {
                firstWords[i] = addedWordsAfter.get(i).split(" - ")[0];
            }
        assertThat(firstWords).containsExactlyInAnyOrderElementsOf(newNameWords);

        webDriver.findElement(By.xpath("//button[text()='Закрыть']")).click();

//      Проверка, что добавленные слова есть в списке слов словаря
        List<String> addedWordsFromDictionary = webDriver.findElements(By.xpath("//div[@class='four wide column mld-value-col mld-word']//span[@class='mld-text']"))
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        assertThat(addedWordsFromDictionary).containsAll(newNameWords);

//      Проверка, что транскрипция подобрана системой правильно
        SoftAssertions softAssertionsAfter = new SoftAssertions();
        for (Word word : newWords) {
            softAssertionsAfter.assertThat(webDriver.findElement(By.xpath("//span[text()='" + word.getWord() + "']/ancestor::div[@class='row mld-word-row']//div[@class='middle aligned four wide column mld-value-col']/span"))
                            .getText())
                    .isEqualTo(word.getTranscription());
        }
        softAssertionsAfter.assertAll();

        //Thread.sleep(5000);
    }
}