package com.github.dandelion.datatables.dataimport.mail;

import com.github.dandelion.datatables.model.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class PersonExtractor {

    public static String PATTERN_ID = ".*ID: ([0-9]+?)<br.*";
    public static String PATTERN_NAME = ".*Имя: (.*?)<br.*";
    public static String PATTERN_EMAIL = ".*Email: (.*?)<br.*";
    public static String PATTERN_PHONE = ".*Телефон: (.*?)<br.*";

    public static final Pattern ID = Pattern.compile(PATTERN_ID, Pattern.DOTALL);
    public static final Pattern NAME = Pattern.compile(PATTERN_NAME, Pattern.DOTALL);
    public static final Pattern EMAIL = Pattern.compile(PATTERN_EMAIL, Pattern.DOTALL);
    public static final Pattern PHONE = Pattern.compile(PATTERN_PHONE, Pattern.DOTALL);

    public static List<Person> extract(String[] messages) {
        ArrayList<Person> persons = new ArrayList<Person>();
        for (int i = 0; i < messages.length; i++) {
            String message = messages[i];
            persons.add(extractMessage(message));
        }
        return persons;
    }

    public static Person extractMessage(String message) {
        Person person = new Person("", "", "", "", new Date());
        Matcher matcher = NAME.matcher(message);
        if (matcher.matches()) {
            String name = matcher.group(1);
            person.setName(name.trim());
        }
        matcher = EMAIL.matcher(message);
        if (matcher.matches()) {
            String mail = matcher.group(1);
            person.setMail(mail.trim());
        }
        matcher = PHONE.matcher(message);
        if (matcher.matches()) {
            String phone = matcher.group(1);
            person.setPhone(phone.trim());
        }
        return person;
    }
}
