package com.kailas.frienzoservice.util;

public class FrienzoURIConstants {

	public static final String LOCATION_SERVICE_URI_BASE = "/location";
	public static final String LOCATION_SERVICE_URI_UPDATE = "/location/{id}";
	public static final String LOCATION_SERVICE_URI_GET_ALL = "/location/all";
	public static final String LOCATION_SERVICE_URI_GET = "/location/{id}";
	
	//TODO: add friendship functionality... needs FB login first
	public static final String USER_SERVICE_URI_BASE = "/user";
	public static final String USER_SERVICE_URI_UPDATE = "/user/update";
	public static final String USER_SERVICE_URI_GET = "/user/{id}";
	public static final String USER_SERVICE_URI_FRIENDS = "/user/friends";
	public static final String USER_SERVICE_URI_UNSUBSCRIBE = "/user/friends/unsubscribe/{userId}/{friendId}";
	public static final String USER_SERVICE_URI_SUBSCRIBE = "/user/friends/subscribe/{userId}/{friendId}";
	
}
