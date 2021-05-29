package console.commands;

import collectionmanager.databasetools.UserChecker;

/**
 * Класс-предок для всех команд
 */

public abstract class AbstractCommand implements Command {
    private final String name;
    private final String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @return Имя для команды, используемой командой help
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * @return Описание действий команды
     */
    @Override
    public String getDescription() {
        return description;
    }
}
