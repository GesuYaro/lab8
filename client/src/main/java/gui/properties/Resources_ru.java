package gui.properties;

import java.util.ListResourceBundle;

public class Resources_ru extends ListResourceBundle {

    private static final Object[][] contents = new Object[][] {
            {"sign out", "выйти"},
            {"sign in", "войти"},
            {"sign up", "регистрация"},
            {"login", "логин"},
            {"password", "пароль"},
            {"commands", "команды"},
            {"logged as", "авторизирован как"},
            {"add", "добавить"},
            {"clear", "очистить"},
            {"filter less than singles", "отфильтровать меньших по количеству синглов"},
            {"help", "помощь"},
            {"history", "история"},
            {"info", "информация"},
            {"insert at", "вставить в"},
            {"print genre descending", "вывести жанры по убыванию"},
            {"remove by id", "удалить по id"},
            {"remove last", "удалить последний"},
            {"show", "показать всё"},
            {"update", "обновить"},
            {"table", "таблица"},
            {"visualization", "визуализация"},
            {"id", "идентификатор"},
            {"name", "название"},
            {"x", "x"},
            {"y", "y"},
            {"date", "дата"},
            {"participants", "участники"},
            {"singles", "синглы"},
            {"genre", "жанр"},
            {"label", "лейбл"},
            {"enter argument", "введите аргумент"},
            {"enter fields", "заполните поля"},
            {"successful", "успешно"},
            {"delete?", "удалить?"},
            {"Input Error\nName field can't be null, string can't be empty", "Ошибка ввода\nИмя не может быть пустым"},
            {"X can't be less than -153", "X не может быть меньше -153"},
            {"Input Error\nX should be long", "Ошибка ввода\nX должен быть целым числом"},
            {"Y should be greater than -159", "Y должен быть больше -159"},
            {"Input Error\nY should be double", "Ошибка ввода\nY должен быть числом"},
            {"Input Error\nNumber of participants should be the natural number", "Ошибка ввода\nКоличество участников должно быть натуральным числом"},
            {"Input Error\nSingles count should be the natural number", "Ошибка ввода\nКоличество синглов должно быть натуральным числом"},
            {"execute script", "исполнить скрипт"}
    };

    @Override
    public Object[][] getContents () {
        return contents;
    }
}
