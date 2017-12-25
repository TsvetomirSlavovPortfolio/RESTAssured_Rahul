package rk.RESTAssured;

import java.io.InputStream;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ReadResponseInDifferentWays_1
{

	// This is Json where Response is in Json
	@Test (groups = { "Search_Text_Lands" },priority=1)
	public void testLogging()
	{
		given().get("http://services.groupkt.com/country/search?text=lands").
		then().statusCode(200).log().all();
	}
	
	// This is String where Response is in String
	@Test (groups = { "Search_Text_Lands" },priority=2)
	public void testGetResponseAsString()
	{
		String str = get("http://services.groupkt.com/country/search?text=lands").asString();
		System.out.println("Search?text=lands Response is :-\n"+str);
	}
	
	// This is asInputStream where Response is in asInputStream
	@Test (groups = { "Search_Text_Lands" },priority=1)
	public void testGetResponseAsInputStream()
	{
		InputStream stream = get("http://services.groupkt.com/country/search?text=lands").asInputStream();
		System.out.println("Search?text=lands Response Stream is :-\n"+stream.toString().length());
	}
	
	// This is asInputStream where Response is in asInputStream
	@Test (groups = { "Search_Text_Lands" },priority=1)
	public void testGetResponseAsByteArray()
	{
		byte[] byteArray = get("http://services.groupkt.com/country/search?text=lands").asByteArray();
		System.out.println("Search?text=lands Response Byte Array Length is :-\n"+byteArray.length);
	}
	
	//--------------------------------------------xoxoxoxoxoxoxoxoxox-----------------------------------------
	
	// This is String where Response is in String
	@Test (groups = {"JsonPlaceHolder_Photos" },priority=1)
	public void testExtraxtDetailsUsingPath()
	{
		String href = 
				when().
					get("http://jsonplaceholder.typicode.com/photos/1").
				then().
					contentType(ContentType.JSON).
					body("albumId",equalTo(1)).
					extract().
					path("url");
		System.out.println(href);
		
		when().
			get(href).
		then().			
			statusCode(200).log().all();		
	}	
	
	// This is String where Response is in String
	@Test (groups = {"JsonPlaceHolder_Photos" },priority=1)
	public void testExtraxtPathInOneLine()
	{
		String href_1 = get("http://jsonplaceholder.typicode.com/photos/1").path("url");
		System.out.println("href_1 := "+href_1);
		when().get(href_1).then().statusCode(200).log().all();		
		
		String href_2 = get("http://jsonplaceholder.typicode.com/photos/1").andReturn().jsonPath().getString("url");
		System.out.println("href_2 := "+href_2);
		when().get(href_2).then().statusCode(200).log().all();		
	}	
	
	// This is String where Response is in String
	@Test (groups = {"JsonPlaceHolder_Photos" },priority=2)
	public void testExtraxtDetailsUsingResponse()
	{
		Response resp = get("http://jsonplaceholder.typicode.com/photos/1");
		System.out.println("Content Type:= "+resp.contentType());
		System.out.println("Full Response:= "+resp.asString());
		System.out.println("Status Code:= "+resp.statusCode());
		System.out.println("href_3 :="+resp.path("url"));
	}	
	
}
