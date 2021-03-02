package Console;

import Console.Exсeptions.NoArgumentFoundException;
import Console.Exсeptions.NoSuchCommandException;
import Console.commands.CommandCode;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Класс консоли
 * Принимает команды от пользователя и вызывает инвокер
 */
public class Console implements Runnable {

    private CommandHandler commandHandler;
    private BufferedReader reader;
    private ConsoleWriter writer;


    /**
     * @param commandHandler Обработчик команд, он же Invoker
     * @param reader Входной поток
     * @param writer выходной поток
     */
    public Console(CommandHandler commandHandler, BufferedReader reader, ConsoleWriter writer) {
        this.commandHandler = commandHandler;
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * Запускает консоль
     * Останавливается, если результат выполнения команды - CommandCode.EXIT
     */
    @Override
    public void run() {
        CommandCode commandCode;
        String[] userCommand = {"", ""};
        commandCode = CommandCode.DEFAULT;
        do {
            try {
            userCommand = reader.readLine()
                    .trim()
                    .split(" ", 2);
            userCommand[0] = userCommand[0].trim();
            if (userCommand.length > 1) userCommand[1] = userCommand[1].trim();
            try {
                commandCode = commandHandler.execute(userCommand);
            } catch (NoArgumentFoundException e) {
                writer.write(e.getMessage());
            }
            } catch (NoSuchCommandException e) {
                writer.write(e.getMessage());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
//            if (commandCode.equals(CommandCode.NO_SUCH_COMMAND)) {
//                print("Команда " + userCommand[0] + " не найдена");
//            }
        } while (!commandCode.equals(CommandCode.EXIT));
//        try {
//            reader.close();
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

//    public void setCommandHandler(CommandHandler commandHandler) {
//        this.commandHandler = commandHandler;
//    }

//    public void setReader(BufferedReader reader) {
//        this.reader = reader;
//    }

    /**
     * Выводит текст в консоль
     * @param string Текст, который нужно вывести
     */
    public void print(String string) {
        writer.write(string);

    }
}
