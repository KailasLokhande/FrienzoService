package com.kailas.frienzoservice.controllers;

import java.io.FileNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kailas.frienzoservice.model.Location;
import com.kailas.frienzoservice.persistance.DatabaseManager;

import static com.kailas.frienzoservice.util.FrienzoURIConstants.*;

@Controller
public class LocationController {

	private static final Logger logger = LoggerFactory
			.getLogger(LocationController.class);

	@RequestMapping(value = LOCATION_SERVICE_URI_GET, method = RequestMethod.GET)
	public @ResponseBody Location getLocation(@PathVariable("id") String id)
			throws FileNotFoundException {
		logger.info("Getting location for Person with Id: " + id);
		return DatabaseManager.getInstance().getLocation(id);
	}

	@RequestMapping(value = LOCATION_SERVICE_URI_UPDATE, method = RequestMethod.PUT)
	public @ResponseBody Location updateLocation(@PathVariable("id") String id,
			@RequestBody Location location) throws FileNotFoundException {
		logger.info("Updating location for Person with Id: " + id);
		DatabaseManager.getInstance().updateLocation(location, id);
		return location;
	}


}
