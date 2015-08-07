package com.github.dandelion.datatables.dataimport.mail;

import com.github.dandelion.datatables.model.Person;
import org.junit.Assert;
import org.junit.Test;

public class PersonExtractorTest {

    public static String message = "<br/>\n" +
            "<br/>\n" +
            "Получен новый лид с целевой страницы: <a href=\"http://testlpgenerator.ru/testapi/\">Тест API</a>, вариант: Первый вариант.\n" +
            "<br/>\n" +
            "Для обработки лидов, пожалуйста, используйте <a href=\"http://lpgenerator.ru/leads/\">Систему управления лидами LPgenerator</a>.\n" +
            "<br/>\n" +
            "<br/>\n" +
            "<b>Данные лида:</b><br/><br/>\n" +
            "ID: 16632732<br />Имя: Я тот самый  <br /><br />Email: imm@m.ru <br /><br />Телефон: (999) 999-99-99 <br /><br />Имя формы: Форма во второй секции<br /><br />\n" +
            "\n" +
            "\n" +
            "<br/>Дополнительные значения:<br/>\n" +
            "\n" +
            "\n" +
            "<b>HTTP_USER_AGENT: </b> Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:39.0) Gecko/20100101 Firefox/39.0\n" +
            "<br/>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<b>IP: </b> 89.22.4.3\n" +
            "<br/>\n" +
            "\n" +
            "\n" +
            "\n" +
            "<b>LANGUAGE: </b> en-US\n" +
            "<br/>\n";

    @Test
    public void testExtractMessage() throws Exception {
        Person person = PersonExtractor.extractMessage(message);
        Assert.assertEquals("Я тот самый", person.getName());
        Assert.assertEquals("imm@m.ru", person.getMail());
        Assert.assertEquals("(999) 999-99-99", person.getPhone());
    }
}