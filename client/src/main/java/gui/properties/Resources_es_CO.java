package gui.properties;

import java.util.ListResourceBundle;

public class Resources_es_CO extends ListResourceBundle {

    private static final Object[][] contents = new Object[][] {
            {"sign out", "desconectar"},
            {"sign in", "iniciar sesión"},
            {"sign up", "inscribirse"},
            {"login", "acceso"},
            {"password", "contraseña"},
            {"commands", "comandos"},
            {"logged as", "registrado como"},
            {"add", "agregar"},
            {"clear", "claro"},
            {"filter less than singles", "filtrar menos que los solteros"},
            {"help", "ayuda"},
            {"history", "historia"},
            {"info", "info"},
            {"insert at", "insertar en"},
            {"print genre descending", "género de impresión descendente"},
            {"remove by id", "eliminar por id"},
            {"remove last", "quitar último"},
            {"show", "show"},
            {"update", "actualizar"},
            {"table", "mesa"},
            {"visualization", "visualización"},
            {"id", "identificación"},
            {"name", "nombre"},
            {"x", "x"},
            {"y", "y"},
            {"date", "fecha"},
            {"participants", "participantes"},
            {"singles", "individual"},
            {"genre", "género"},
            {"label", "etiqueta"},
            {"enter argument", "ingresar argumento"},
            {"enter fields", "ingrese campos"},
            {"successful", "exitoso"},
            {"delete?", "¿eliminar?"},
            {"Input Error\nName field can't be null, string can't be empty", "Error de entrada\n" +
                    "El campo de nombre no puede ser nulo, la cadena no puede estar vacía"},
            {"X can't be less than -153", "X no puede ser menor que -153"},
            {"Input Error\nX should be long", "Error de entrada\n" +
                    "X debería ser larga"},
            {"Y should be greater than -159", "Y debería ser mayor que -159"},
            {"Input Error\nY should be double", "Error de entrada\n" +
                    "Y debería ser el doble"},
            {"Input Error\nNumber of participants should be the natural number", "Error de entrada\n" +
                    "El número de participantes debe ser el número natural."},
            {"Input Error\nSingles count should be the natural number", "Error de entrada\n" +
                    "El recuento de solteros debe ser el número natural"},
            {"execute script", "ejecutar script"}
    };

    @Override
    public Object[][] getContents () {
        return contents;
    }
}
