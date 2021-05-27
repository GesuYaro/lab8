package console.commands;

import network.CurrentUser;
import musicband.MusicBand;

/**
 * Интерфейс для команд
 */
public interface Command {
    CommandResponse execute(String firstArgument, MusicBand requestedMusicBand, CurrentUser currentUser);
    String getName();
    String getDescription();
}
