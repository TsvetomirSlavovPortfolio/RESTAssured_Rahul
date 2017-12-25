package rk.RESTAssured;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

public class REST_CreatePOSTRequest_1
{	
	//  http://toolsqa.com/rest-assured/post-request-using-rest-assured/
	@BeforeClass
	public void setUPRESTAssured()
	{
		RestAssured.baseURI = "http://restapi.demoqa.com";
		RestAssured.basePath = "/customer";
		/*	    RestAssured.port = 80;
	    RestAssured.authentication = basic("username", "password");
	    RestAssured.rootPath = "store.book";*/
	}

	@SuppressWarnings("unchecked")
	@Test(groups = { "CreateCustomer" },priority=1)
	public void testCreatePOSTRequest()
	{
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "Rahul"); // Cast
		requestParams.put("LastName", "Kinge");
		requestParams.put("UserName", "rkinge21");
		requestParams.put("Password", "rkinge21");
		requestParams.put("Email",  "rkinge786@gmail.com");
		Response response = given().body(requestParams.toJSONString()).post("/register");

		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);
		System.out.println("Response body: " + response.body().asString());
		
		Assert.assertEquals(statusCode, 201);
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
	}

}

