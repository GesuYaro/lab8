package gui.properties;

import java.util.ListResourceBundle;

public class Resources_sq extends ListResourceBundle {
    private static final Object[][] contents = new Object[][] {
            {"sign out", "dilni"},
            {"sign in", "Hyni"},
            {"sign up", "regjistrohu"},
            {"login", "login"},
            {"password", "fjalëkalimin"},
            {"commands", "komandat"},
            {"logged as", "regjistruar si"},
            {"add", "shtoni"},
            {"clear", "qartë"},
            {"filter less than singles", "filtroni më pak se teke"},
            {"help", "ndihmë"},
            {"history", "historia"},
            {"info", "info"},
            {"insert at", "futur në"},
            {"print genre descending", "zhanër i shtypur zbritës"},
            {"remove by id", "hiq me id"},
            {"remove last", "hiqni të fundit"},
            {"show", "shfaqje"},
            {"update", "azhurnimi"},
            {"table", "tryezë"},
            {"visualization", "vizualizimi"},
            {"id", "id"},
            {"name", "emri"},
            {"x", "x"},
            {"y", "y"},
            {"date", "data"},
            {"participants", "pjesëmarrësit"},
            {"singles", "beqarë"},
            {"genre", "zhanër"},
            {"label", "emërtim"},
            {"enter argument", "hyj në argument"},
            {"enter fields", "hyj në fusha"},
            {"successful", "e suksesshme"},
            {"delete?", "fshihet"},
            {"Input Error\nName field can't be null, string can't be empty", "Gabim i hyrjes\nFusha e emrit nuk mund të jetë null, vargu nuk mund të jetë bosh"},
            {"X can't be less than -153", "X nuk mund të jetë më pak se -153"},
            {"Input Error\nX should be long", "Gabim i hyrjes\n" +
                    "X duhet të jetë e gjatë"},
            {"Y should be greater than -159", "Y duhet të jetë më i madh se -159"},
            {"Input Error\nY should be double", "Gabim i hyrjes\n" +
                    "Y duhet të jetë dyfish"},
            {"Input Error\nNumber of participants should be the natural number", "Gabim i hyrjes\n" +
                    "Numri i pjesëmarrësve duhet të jetë numri natyror"},
            {"Input Error\nSingles count should be the natural number", "Gabim i hyrjes\n" +
                    "Numri i teke duhet të jetë numri natyror"},
            {"execute script", "ekzekutoni skenarin"}
    };

    @Override
    public Object[][] getContents () {
        return contents;
    }

}
