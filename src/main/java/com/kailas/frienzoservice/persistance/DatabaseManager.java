package com.kailas.frienzoservice.persistance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kailas.frienzoservice.model.Location;
import com.kailas.frienzoservice.model.User;

public class DatabaseManager {

	private static final String USER_DB_STORE = "C:/Code/Users.csv";

	private static final String LOCATION_DB_STORE = "C:/Code/Location.csv";

	private static File USER_DB;
	private static File LOCATION_DB;

	private static final DatabaseManager ISNTANCE = new DatabaseManager();
	private static final Logger logger = LoggerFactory
			.getLogger(DatabaseManager.class);

	private static final ConcurrentHashMap<String, User> USERS = new ConcurrentHashMap<String, User>();
	private static final ConcurrentHashMap<String, Location> LAST_KNOWN_LOCATIONS = new ConcurrentHashMap<String, Location>();

	private DatabaseManager() {

		USER_DB = new File(USER_DB_STORE);
		if (!USER_DB.exists())
			try {
				USER_DB.createNewFile();
			} catch (IOException e) {
				logger.error("USER DB CREATION FAILED", e);
				throw new RuntimeException(e);
			}

		LOCATION_DB = new File(LOCATION_DB_STORE);
		if (!LOCATION_DB.exists()) {
			try {
				LOCATION_DB.createNewFile();
			} catch (IOException e) {
				logger.error("LOCATION DB CREATION FAILED", e);
				throw new RuntimeException(e);
			}
		}
	}

	public static DatabaseManager getInstance() {
		return ISNTANCE;
	}

	private static final String DELIMITER = ",";

	public void addUser(User user) throws FileNotFoundException {
		// name, id, photoUrlGoogle, photoUrlFacebook, phoneNumber, email,
		// gPlusProfileUrl, fbProfileUrl,

		StringBuilder builder = new StringBuilder();
		builder.append(user.getName()).append(DELIMITER).append(user.getId())
				.append(DELIMITER).append(user.getPhotoUrlGoogle())
				.append(DELIMITER).append(user.getPhotoUrlFacebook())
				.append(DELIMITER).append("").append(DELIMITER)
				.append(user.getEmail()).append(DELIMITER)
				.append(user.getgPlusProfileUrl()).append(DELIMITER)
				.append(user.getFbProfileUrl());
		String userdata = builder.toString();

		synchronized (USER_DB) {
			PrintWriter printWriter = new PrintWriter(USER_DB);
			printWriter.println(userdata);
			printWriter.flush();
			printWriter.close();
		}

		USERS.put(user.getId(), user);
		Location location = user.getLastKnownLocation();
		if (location == null)
			return;
		StringBuilder locationBuilder = new StringBuilder();
		locationBuilder.append(user.getId()).append(DELIMITER)
				.append(location.getLatitude()).append(DELIMITER)
				.append(location.getLongitude());
		String locationData = locationBuilder.toString();

		synchronized (LOCATION_DB) {
			PrintWriter printWriter = new PrintWriter(LOCATION_DB);
			printWriter.println(locationData);
			printWriter.flush();
			printWriter.close();
		}
		LAST_KNOWN_LOCATIONS.put(user.getId(), location);

		// TODO: Phone Numbers storage
	}

	private static boolean oneReadFromFSDone = false;

	public Map<String, User> getAllUsers() throws FileNotFoundException {
		// name, id, photoUrlGoogle, photoUrlFacebook, phoneNumber, email,
		// gPlusProfileUrl, fbProfileUrl,
		if (oneReadFromFSDone)
			return USERS;

		Scanner scanner = new Scanner(USER_DB);
		while (scanner.hasNextLine()) {
			String userData = scanner.nextLine();
			String[] userFields = userData.split(DELIMITER);
			User user = new User();
			user.setName(userFields[0]);
			user.setId(userFields[1]);
			user.setPhotoUrlGoogle(userFields[2]);
			user.setPhotoUrlFacebook(userFields[3]);
			List<String> phoneNumbers = new ArrayList<String>();
			phoneNumbers.add(userFields[4]);
			user.setPhoneNumbers(phoneNumbers);
			user.setEmail(userFields[5]);
			user.setgPlusProfileUrl(userFields[6]);
			user.setFbProfileUrl(userFields[7]);
			USERS.put(userFields[1], user);
		}
		scanner.close();

		Scanner locationScanner = new Scanner(LOCATION_DB);
		while (locationScanner.hasNextLine()) {
			String locationData = locationScanner.nextLine();
			String[] locationFields = locationData.split(DELIMITER);
			Location location = new Location();
			location.setLatitude(Double.parseDouble(locationFields[1]));
			location.setLongitude(Double.parseDouble(locationFields[2]));
			if (USERS.containsKey(locationFields[0])) {
				USERS.get(locationFields[0]).setLastKnownLocation(location);
				LAST_KNOWN_LOCATIONS.put(locationFields[0], location);
			}
		}
		locationScanner.close();

		oneReadFromFSDone = true;
		return USERS;

	}

	public Location getLocation(String userId) throws FileNotFoundException {
		if (!oneReadFromFSDone) {
			synchronized (USERS) {
				if (!oneReadFromFSDone)
					getAllUsers();
			}
		}
		return LAST_KNOWN_LOCATIONS.get(userId);
	}

	public void updateLocation(Location location, String userId)
			throws FileNotFoundException {
		if (!oneReadFromFSDone) {
			synchronized (USERS) {
				if (!oneReadFromFSDone)
					getAllUsers();
			}
		}

		LAST_KNOWN_LOCATIONS.put(userId, location);
	}

	public User getUser(String id) throws FileNotFoundException {
		if (!oneReadFromFSDone) {
			synchronized (USERS) {
				if (!oneReadFromFSDone)
					getAllUsers();
			}
		}

		return USERS.get(id);
	}

}
