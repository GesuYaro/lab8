package gui.properties;

import java.util.ListResourceBundle;

public class Resources_ro extends ListResourceBundle {

    private static final Object[][] contents = new Object[][] {
            {"sign out", "deconectați-vă"},
            {"sign in", "conectare"},
            {"sign up", "Inscrie-te"},
            {"login", "nume de utilizator"},
            {"password", "parola"},
            {"commands", "comenzi"},
            {"logged as", "logat ca"},
            {"add", "adăuga"},
            {"clear", "clar"},
            {"filter less than singles", "filtru mai puțin decât single"},
            {"help", "ajutor"},
            {"history", "istorie"},
            {"info", "info"},
            {"insert at", "introduceți la"},
            {"print genre descending", "genul tipărit descrescător"},
            {"remove by id", "elimina prin id"},
            {"remove last", "elimina ultimul"},
            {"show", "spectacol"},
            {"update", "actualizați"},
            {"table", "masa"},
            {"visualization", "vizualizare"},
            {"id", "id"},
            {"name", "nume"},
            {"x", "x"},
            {"y", "y"},
            {"date", "data"},
            {"participants", "participanți"},
            {"singles", "singuri"},
            {"genre", "gen"},
            {"label", "eticheta"},
            {"enter argument", "introduceți argumentul"},
            {"enter fields", "introduceți câmpuri"},
            {"successful", "de succes"},
            {"delete?", "șterge?"},
            {"Input Error\nName field can't be null, string can't be empty", "Eroare de intrare\n" +
                    "Câmpul de nume nu poate fi nul, șirul nu poate fi gol"},
            {"X can't be less than -153", "X nu poate fi mai mic de -153"},
            {"Input Error\nX should be long", "Eroare de intrare\n" +
                    "X ar trebui să fie lung"},
            {"Y should be greater than -159", "Y ar trebui să fie mai mare de -159"},
            {"Input Error\nY should be double", "Eroare de intrare\n" +
                    "Y ar trebui să fie dublu"},
            {"Input Error\nNumber of participants should be the natural number", "Eroare de intrare\n" +
                    "Numărul de participanți ar trebui să fie numărul natural"},
            {"Input Error\nSingles count should be the natural number", "Eroare de intrare\n" +
                    "Numărul de singuri ar trebui să fie numărul natural"},
            {"execute script", "executați scriptul"}
    };

    @Override
    public Object[][] getContents () {
        return contents;
    }

}
