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
            {"successful", "успешно"}
    };

    @Override
    public Object[][] getContents () {
        return contents;
    }
}
