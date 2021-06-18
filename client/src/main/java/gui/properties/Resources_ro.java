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
            {"successful", "de succes"}
    };

    @Override
    public Object[][] getContents () {
        return contents;
    }

}
