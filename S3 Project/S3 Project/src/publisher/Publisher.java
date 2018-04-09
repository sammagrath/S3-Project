package publisher;

import javax.xml.ws.Endpoint;

import webservice.WebServiceImpl;

public class Publisher {

	public static void main(String[] args) {

		Endpoint.publish("http://localhost:8080/MyWebService", new WebServiceImpl());
	}
}
