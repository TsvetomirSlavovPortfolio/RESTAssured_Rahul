package rk.RESTAssured;

import static io.restassured.RestAssured.*;

import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;





import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class HeadersAndCookies
{	
	//  http://james-willett.com/2015/06/rest-assured-extract-json-response/
	//  http://james-willett.com/2017/05/rest-assured-gpath-json/
	
	@BeforeClass
	public void setUPREstAssured()
	{
	    RestAssured.baseURI = "http://services.groupkt.com";
	    RestAssured.basePath = "/country/";
/*	    RestAssured.port = 80;
	    RestAssured.authentication = basic("username", "password");
	    RestAssured.rootPath = "store.book";*/
	}

	@Test (groups = { "Headers", "Search_Text_Lands" },priority=1)
	public void testHeaders()
	{
		Response resp = get("search?text=lands");
		System.out.println(resp.getHeader("Content-Type"));
		
		Headers headers = resp.getHeaders();
		for (Header header : headers)
		{
			System.out.println(header);
		}
	}
	
	@Test (groups = { "Cookies", "JsonPlaceHolder_Posts" },priority=1)
	public void testCookies()
	{
		Response resp = get("http://jsonplaceholder.typicode.com/posts");
		Map<String, String> mapCookies=resp.getCookies();
		for(Map.Entry<String, String> entry : mapCookies.entrySet())
		{
			System.out.println("Key := "+entry.getKey()+"\nValue := "+entry.getValue());
		}
	}
	
	@Test (groups = { "Cookies", "JsonPlaceHolder_Posts" },priority=1)
	public void testDetailedCookies()
	{
		Response resp = get("http://jsonplaceholder.typicode.com/posts");
		Cookie cookie =resp.getDetailedCookie("__cfduid");
		System.out.println("Detailed := "+cookie.hasExpiryDate());
		System.out.println("Detailed := "+cookie.getExpiryDate());
		System.out.println("Detailed := "+cookie.hasValue());
	}
}
