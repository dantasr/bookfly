package controller;

import java.util.HashMap;

public class Dispatcher {
	private FrontController frontController;
	
	public enum Request {
		
	}
	
	public Dispatcher(FrontController frontController) {
		this.frontController = frontController;
	}
	
	public void dispatch(Request request, HashMap<String, Object> params) {
		
	}
}
