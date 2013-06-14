package ph.devcon.javacamp;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import spark.Request;
import spark.Response;
import spark.Route;

public class HelloSpark {

	public static void main(String[] args) {

		/*
		 * Demo for handling basic GET request
		 */
		get(new Route("/hello") {

			@Override
			public Object handle(Request request, Response response) {
				return "Hello World!";
			}
		});

		/*
		 * Demo for handling URL parameters
		 */
		get(new Route("/hello/withparams/:name") {
			@Override
			public Object handle(Request request, Response response) {
				return "Hello " + request.params(":name");
			}
		});

		/*
		 * Demo for handling basic POST request
		 */
		post(new Route("/hello") {
			@Override
			public Object handle(Request request, Response response) {
				return "Hello World: " + request.body();
			}
		});

		/*
		 * Demo for returning in XML format
		 */
		get(new Route("/hello/xml") {

			@Override
			public Object handle(Request request, Response response) {
				response.type("text/xml");
				return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><winner>miami</winner>";
			}
		});

		/*
		 * Demo for returning in JSON format
		 */
		get(new Route("/hello/json") {

			@Override
			public Object handle(Request request, Response response) {
				response.type("text/json");
				return "{\"king\":\"Lebron James\"}";
			}
		});

		/*
		 * Demo for returning HTML
		 */
		get(new Route("/hello/html") {

			@Override
			public Object handle(Request request, Response response) {

				String name = request.queryParams("name");
				
				response.type("text/html");

				return "" + "<DOCTYPE html>" + "<html>" + " <head>"
						+ " </head>" + " <body>" + " <h1>Hello " + name
						+ "</h1>" + " </body>" + "</html>";
			}
		});

		/*
		 * Demo for returning image
		 */
		get(new Route("/hello/imagefile") {

			@Override
			public Object handle(Request request, Response response) {

				String filename = "public/img/heat.jpg";

				byte[] out = null;

				try {

					/*
					 * Will only handle writes of 64 kiB at a time, which means
					 * larger files will fail
					 */
					out = IOUtils.toByteArray(new FileInputStream(filename));
					response.raw().setContentType("image/jpeg;charset=utf-8");
					response.raw().getOutputStream().write(out, 0, out.length);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return out;

			}
		});

		/*
		 * Demo for responding with status codes
		 */
		get(new Route("/hello/lovelife") {
			@Override
			public Object handle(Request request, Response response) {
				response.status(404); // 404 Not found
				return "Not found";
			}
		});
	}
}
