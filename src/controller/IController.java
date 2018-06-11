package controller;

import java.util.HashMap;

import controller.FrontController.Request;

public interface IController {
	public void dispatchRequest(Request request, HashMap<String, Object> hashMap);
}
