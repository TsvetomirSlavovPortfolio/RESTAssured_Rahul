package rk.RESTAssured;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;

public class REST_CreatePOSTRequest_2
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
	@Test(groups = { "CreateCustomer" },priority=2)
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

	@Test(groups = { "CreateCustomer" },priority=1)
	public void testCreatePOSTRequest_JsonAsMap ()
	{
		Map<String, Object>  map = new HashMap<>();
		map.put("FirstName", "Test121");
		map.put("LastName", "Test1271231");
		map.put("Email", "abc1@gmail.com");
		map.put("UserName", "testing_purpose1");
		map.put("Password", "testing_purpose1231");
		System.out.println("Map Values := \n"+map);
		Response response = given().body(map).post("/register");

		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved := " + statusCode);
		System.out.println("Response body:= \n" + response.body().asString());
		
		Assert.assertEquals(statusCode, 201);
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals("OPERATION_SUCCESS", successCode,  "Correct Success code was returned");
	}

	@Test(groups = { "CreateCustomer","CouchBase" },priority=1)
	public void testCreatePOSTRequest_JsonAsMapofMap ()
	{
		Map<String, Object>  map = new HashMap<>();

		Map<String, Object>  mapCompanyInfo = new HashMap<>();
		mapCompanyInfo.put("CompanyYear", "2015");
		mapCompanyInfo.put("CompanyMonth", "July");

		Map<String, Object>  mapCompany = new HashMap<>();
		mapCompany.put("CompanyName", "Amdocs");
		mapCompany.put("CompanyLocation", "Magarpatta");
		mapCompany.put("CompanyJoiningInfo", mapCompanyInfo);
		
		map.put("FirstName", "Test12");
		map.put("LastName", "Test127123");
		map.put("Email", "abc@gmail.com");
		map.put("UserName", "testing_purpose");
		map.put("Company", mapCompany);
		map.put("Password", "testing_purpose123");
		
		System.out.println("Map Values := \n"+map);
		RestAssured.baseURI  = "http://127.0.0.1:5984/restassured";	

		Response response =given().
						contentType("application/json").
						body(map).
					when().
						post("");

		System.out.println("Response body:= \n" + response.body().asString());		
		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved := " + statusCode);
		Assert.assertEquals(statusCode, 201);

	}

	@Test(groups = { "CreateCustomer" },priority=2)
	public void testCreatePOSTRequestFromJsonFile()
	{
	    File file = new File(System.getProperty("user.dir")+"/src/test/resources/RegisterCustomer.json");

		Response response = 
				given()
					.contentType("application/json").
					body(file).
				when().
					post("/register");
		
		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);
		System.out.println("Response body: " + response.body().asString());
		
		Assert.assertEquals(statusCode, 201);
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals( "OPERATION_SUCCESS", successCode, "Correct Success code was returned");
	}
	
	@Test(groups = { "CreateCustomer" },priority=1)
	public void testCreatePOSTRequest_Parameterised()
	{
		String firstName="Rahul10";
		String lastName="Kinge10";
		Response response = 
				given()
					.contentType("application/json").
					body("{\"firstName\":\""+firstName+"\",\"lastName\":\""+lastName+"\",\"mobNumber\":\"9975056263\"}").
				when().
					post("/register");

		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);
		System.out.println("Response body: " + response.body().asString());
		
		Assert.assertEquals(statusCode, 201);
		String successCode = response.jsonPath().get("SuccessCode");
		Assert.assertEquals( "Correct Success code was returned", successCode, "OPERATION_SUCCESS");
	}

}

