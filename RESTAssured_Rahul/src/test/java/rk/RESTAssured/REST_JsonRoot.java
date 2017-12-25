package rk.RESTAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class REST_JsonRoot
{
	@Test(groups = { "country" },priority=1)
	public void testWithoutRoot()
	{
		given().
			get("http://services.groupkt.com/country/get/iso2code/IT").
		then().
			body("RestResponse.result.name",is("Italy")).
			body("RestResponse.result.alpha2_code",is("IT")).
			body("RestResponse.result.alpha3_code",is("ITA"));
	}
	
	@Test(groups = { "country" },priority=1)
	public void testWithRoot()
	{
		given().
			get("http://services.groupkt.com/country/get/iso2code/IT").
		then().root("RestResponse.result").
			body("name",is("Italy")).
			body("alpha2_code",is("IT")).
			body("alpha3_code",is("ITA"));
	}
	
	@Test(groups = { "country" },priority=1)
	public void testDetachRoot()
	{
		given().
			get("http://services.groupkt.com/country/get/iso2code/IT").
		then().root("RestResponse.result").
			body("name",is("Italy")).
			body("alpha2_code",is("IT")).
			detachRoot("result").
			body("result.alpha3_code",is("ITA"));
	}
}
