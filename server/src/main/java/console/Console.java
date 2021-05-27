package console;

import console.commands.CommandResponse;
import console.exсeptions.NoArgumentFoundException;
import console.exсeptions.NoSuchCommandException;
import console.commands.CommandCode;

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
     * @param writer Выходной поток
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
                String precommand = reader.readLine();
                if (precommand != null) {
                    userCommand = precommand
                            .trim()
                            .split(" ", 2);
                    userCommand[0] = userCommand[0].trim();
                    if (userCommand.length > 1) userCommand[1] = userCommand[1].trim();
                    try {
//                        commandCode = commandHandler.execute(userCommand);
                    } catch (NoArgumentFoundException e) {
                        writer.write(e.getMessage());
                    }
                }
            } catch (NoSuchCommandException e) {
                writer.write(e.getMessage());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } while (!commandCode.equals(CommandCode.EXIT));
    }


    /**
     * Выводит текст в консоль
     * @param string Текст, который нужно вывести
     */
    public void print(String string) {
        writer.write(string);

    }
}
