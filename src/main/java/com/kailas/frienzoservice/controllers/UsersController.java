package com.kailas.frienzoservice.controllers;

import static com.kailas.frienzoservice.util.FrienzoURIConstants.*;

import java.io.FileNotFoundException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kailas.frienzoservice.model.User;
import com.kailas.frienzoservice.persistance.DatabaseManager;

@Controller
public class UsersController {

	private static final Logger logger = LoggerFactory
			.getLogger(UsersController.class);

	@RequestMapping(value = USER_SERVICE_URI_UPDATE, method = RequestMethod.POST)
	public @ResponseBody User updateUser(@RequestBody User user)
			throws FileNotFoundException {
		logger.info("Adding User with Id: " + user.getId());

		DatabaseManager.getInstance().addUser(user);
		return user;
error
		// throw new RuntimeException("Fail");
	}

	@RequestMapping(value = USER_SERVICE_URI_GET, method = RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable("id") String id)
			throws FileNotFoundException {
		logger.info("Getting User with Id: " + id);

		return DatabaseManager.getInstance().getUser(id);
		// throw new RuntimeException("Fail");
	}

	@RequestMapping(value = USER_SERVICE_URI_FRIENDS, method = RequestMethod.GET)
	public @ResponseBody Map<String, User> getAllUsers()
			throws FileNotFoundException {

		return DatabaseManager.getInstance().getAllUsers();
		// throw new RuntimeException("Fail");
	}

}