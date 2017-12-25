package rk.RESTAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class REST_Json
{
	@Test (groups = { "JsonPlaceHolder_Posts" },priority=1)
	public void testStatusCode()
	{
		given().
			get("http://jsonplaceholder.typicode.com/posts/3").
		then().
			statusCode(200);			
	}
	
	@Test(groups = { "country" },priority=1)
	public void testLogging()
	{
		given().
			get("http://services.groupkt.com/country/get/iso2code/IN").
		then().
			statusCode(200).
			log().all();
	}

	@Test(groups = { "country" },priority=1)
	public void testEqualToFunction()
	{
		given().
			get("http://services.groupkt.com/country/get/iso2code/IN").
		then().
			body("RestResponse.result.name",equalTo("India"));
	}

	@Test(groups = { "country" },priority=1)
	public void testHasItemsFunction()
	{
		given().
			get("http://services.groupkt.com/country/get/all").
		then().
			body("RestResponse.result.name",hasItems("India","Albania","Antarctica"));
	}
	
	@Test(groups = { "country" },priority=1)
	public void testParametersAndHeaders()
	{
		given().
			param("key1","Value1").
			header("headA","valueA").
		when().
			get("http://services.groupkt.com/country/get/iso2code/IN").
		then().
			statusCode(200).
			log().all();
	}
	
	@Test(groups = { "country" })
	public void testParametersAndHeaders_Readability()
	{
		given().param("key1","Value1").and().header("headA","valueA").
		when().get("http://services.groupkt.com/country/get/iso2code/IN").
		then().statusCode(200).and().log().all();
	}
	
}
