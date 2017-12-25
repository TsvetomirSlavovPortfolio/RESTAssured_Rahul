package rk.RESTAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
//import static org.hamcrest.Matchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;

public class REST_SchemaValidation
{

	@Test(groups = { "Schema" })
	public void testSchema()
	{
		given().
			get("http://services.groupkt.com/country/get/iso2code/IN").
		then().assertThat().body(matchesJsonSchemaInClasspath("Response_Country_IN.json"));
	}
}
