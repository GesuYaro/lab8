package client;

import collectionmanager.databasetools.AuthenticationException;
import console.FieldsReader;
import musicband.*;

import network.Request;


import java.util.Collection;

public class RequestFabric {

    private Collection<String> commandsWithExtendedRequest;
    private FieldsReader fieldsReader;
    private MusicBandFieldsChecker checker;
    private UserManager userManager;

    public RequestFabric(Collection<String> commandsWithExtendedRequest, FieldsReader fieldsReader, UserManager userManager, MusicBandFieldsChecker checker) {
        this.commandsWithExtendedRequest = commandsWithExtendedRequest;
        this.fieldsReader = fieldsReader;
        this.userManager = userManager;
        this.checker = checker;
    }

    public Request createRequest(String command, String argument) {
        Request request = null;
        if (userManager != null) {
            if (commandsWithExtendedRequest.contains(command)) {
                request = createExtendedRequest(command, argument);
            } else {
                request = new Request(command, argument, null, userManager.getUser());
            }
        } else {
            throw new AuthenticationException();
        }
        return request;
    }

    private Request createExtendedRequest(String command, String argument) {
        String name = fieldsReader.readName();
        Coordinates coordinates = fieldsReader.readCoordinates();
        int numberOfParticipants = fieldsReader.readNumberOfParticipants();
        Integer singlesCount = fieldsReader.readSinglesCount();
        MusicGenre musicGenre = fieldsReader.readMusicGenre();
        Label label = fieldsReader.readLabel();
        return new Request(command, argument, new MusicBand(0, name, coordinates, null, numberOfParticipants, singlesCount, musicGenre, label, ""), userManager.getUser());
    }

    public Request createExtendedRequest(String command, String argument, String name, String x, String y,
                                         String numberOfParticipants, String singlesCount, String genre, String label)
            throws InputValueException {
        String rName = checker.readName(name);
        Coordinates rCoordinates = new Coordinates(checker.readX(x), checker.readY(y));
        int rNumberOfParticipants = checker.readNumberOfParticipants(numberOfParticipants);
        Integer rSinglesCount = checker.readSinglesCount(singlesCount);
        MusicGenre rMusicGenre = checker.readMusicGenre(genre);
        Label rLabel = checker.readLabel(label);
        return new Request(command, argument, new MusicBand(0, rName, rCoordinates, null, rNumberOfParticipants, rSinglesCount, rMusicGenre, rLabel, ""), userManager.getUser());
    }

}
