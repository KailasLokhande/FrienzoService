package com.kailas.frienzoservice.controllers;

import static com.kailas.frienzoservice.util.FrienzoURIConstants.*;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kailas.frienzoservice.exceptions.ResourceNotFoundException;
import com.kailas.frienzoservice.model.Location;
import com.kailas.frienzoservice.model.User;
import com.kailas.frienzoservice.persistance.DatabaseManager;

@Controller
public class UsersController {

	private static final Logger logger = LoggerFactory
			.getLogger(UsersController.class);

	@RequestMapping(value = USER_SERVICE_URI_UPDATE, method = RequestMethod.POST)
	public @ResponseBody User updateUser(@RequestBody User user)
			 {
		logger.info("Adding User with Id: " + user.getId());

		try {
			DatabaseManager.getInstance().addUser(user);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;

		// throw new RuntimeException("Fail");
	}

	@RequestMapping(value = USER_SERVICE_URI_GET, method = RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable("id") String id)
			throws FileNotFoundException {
		logger.info("Getting User with Id   :- " + id);

		User user =  DatabaseManager.getInstance().getUser(id);
		System.out.println(user);
		if( user == null )
			throw new ResourceNotFoundException("User with id: "+ id + " doesn't exist");
		return user;
		// throw new RuntimeException("Fail");
	}

	@RequestMapping(value = USER_SERVICE_URI_FRIENDS, method = RequestMethod.GET)
	public @ResponseBody Map<String, User> getAllUsers()
			throws FileNotFoundException {

		return DatabaseManager.getInstance().getAllUsers();
		// throw new RuntimeException("Fail");
	}

}