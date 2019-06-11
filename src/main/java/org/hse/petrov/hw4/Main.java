package org.hse.petrov.hw4;


import org.hse.petrov.hw4.db.*;
import org.hse.petrov.hw4.objects.Event;
import org.hse.petrov.hw4.objects.Location;
import org.hse.petrov.hw4.objects.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        YandexFileManager downloader = new YandexFileManager(Config.YANDEX_USER, Config.YANDEX_TOKEN, Config.FILE_FOLDER);
        if(!downloader.isDownloaded()){
            System.out.println("No files downloaded, so doing the job...");
            downloader.download();
        } else {
            System.out.println("Files already exists, no job required!");
        }

        try {
            List<Event> events = Files.readAllLines(
                    Paths.get(downloader.getUserLogs().toURI())
            ).stream().map(Event::parseLine).collect(Collectors.toList());

            List<User> users = Files.readAllLines(
                    Paths.get(downloader.getUserData().toURI())
            ).stream().map(User::parseLine).collect(Collectors.toList());

            List<Location> locations = Files.readAllLines(
                    Paths.get(downloader.getIpData().toURI())
            ).stream().map(Location::parseLine).collect(Collectors.toList());

            ConnectionFactory connectionFactory = ConnectionFactory.getConnectionFactory();
            LocationDAO locationDAO = new LocationDAO(connectionFactory.getConnection());
            locationDAO.createTableIfNotExists();

            EventDAO eventDAO = new EventDAO(connectionFactory.getConnection());
            eventDAO.createTableIfNotExists();

            UserDAO userDAO = new UserDAO(connectionFactory.getConnection());
            userDAO.createTableIfNotExists();

            locationDAO.loadAll(locations);
            eventDAO.loadAll(events);
            userDAO.loadAll(users);

            System.out.println(locationDAO.readAll().get(0));
            System.out.println(userDAO.readAll().get(0));
            System.out.println(eventDAO.readAll().get(0));

            Analytics analytics = new Analytics(connectionFactory.getConnection());
            analytics.initAnalysticsTables();

            analytics.getRegions().forEach(s -> System.out.println(s));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
