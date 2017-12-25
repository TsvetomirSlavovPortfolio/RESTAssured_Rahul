package rk.RESTAssured;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class CouchDB_REST
{	
	// http://127.0.0.1:5984/_utils/index.html
	@Test(groups = { "CouchBase" },priority=1)
	public void postCouchDB_RESTExample_1()
	{
		RestAssured.baseURI  = "http://127.0.0.1:5984/restassured";	

		Response r = 
				given()
					.contentType("application/json").
					body("{\"firstName\":\"Rahul\",\"lastName\":\"Kinge\",\"mobNumber\":\"9975056263\"}").
				when().
					post("");

		//String body = r.getBody().asString();
		System.out.println(r);  // io.restassured.internal.RestAssuredResponseImpl@50468873
		System.out.println(r.asString());  // {"ok":true,"id":"1cf43d542626d142dbd13f76f0001e43","rev":"1-b58eddb97c1b02e5fa695630493cece3"}
		System.out.println(r.getBody().asString());  // {"ok":true,"id":"1cf43d542626d142dbd13f76f0001e43","rev":"1-b58eddb97c1b02e5fa695630493cece3"}
	}

	// http://127.0.0.1:5984/_utils/index.html
	@Test(groups = { "CouchBase" },priority=2)
	public void postCouchDB_RESTExample_2()
	{
		RestAssured.baseURI  = "http://127.0.0.1:5984/restassured";	

		Response r = 
				given()
					.contentType("application/json").
					body("{\"name\":\"Rahul Kinge3\"}").
				when().
					post("");

		//String body = r.getBody().asString();
		System.out.println(r);  // io.restassured.internal.RestAssuredResponseImpl@50468873
		System.out.println(r.asString());  // {"ok":true,"id":"1cf43d542626d142dbd13f76f0001e43","rev":"1-b58eddb97c1b02e5fa695630493cece3"}
		System.out.println(r.getBody().asString());  // {"ok":true,"id":"1cf43d542626d142dbd13f76f0001e43","rev":"1-b58eddb97c1b02e5fa695630493cece3"}
	}


}
