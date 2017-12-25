package rk.RESTAssured;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
//import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class REST_Xml
{
	@Test (groups = { "ThomasCustomer" },priority=1)
	public void testSingleContent()
	{
		given().
			get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").
		then().
			body("CUSTOMER.ID", equalTo("10")).
			log().all();			
	}
	
	@Test(groups = { "ThomasCustomer" },priority=1)
	public void testMultipleContent()
	{
		given().
			get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").
		then().
			body("CUSTOMER.ID", equalTo("10")).
			body("CUSTOMER.FIRSTNAME", equalTo("Sue")).
			body("CUSTOMER.LASTNAME", equalTo("Fuller")).
			body("CUSTOMER.STREET", equalTo("135 Upland Pl.")).
			body("CUSTOMER.CITY", equalTo("Dallas"));
	}
	
	@Test(groups = { "ThomasCustomer" },priority=1)
	public void testCompleteTextInOneGo()
	{
		given().
			get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").
		then().
			body("CUSTOMER.text()", equalTo("10SueFuller135 Upland Pl.Dallas")).
			log().all();
	}
	
	@Test(groups = { "ThomasCustomer" },priority=1)
	public void testUsingXPath_1()
	{
		given().
			get("http://www.thomas-bayer.com/sqlrest/CUSTOMER/10").
		then().
			body(hasXPath("/CUSTOMER/CITY"),containsString("Dallas"));
	}
	
	@Test(groups = { "ThomasCustomerInvoice" },priority=1)
	public void testUsingXPath_2()
	{
		given().
			get("http://www.thomas-bayer.com/sqlrest/INVOICE").
		then().
			body(hasXPath("/INVOICEList/INVOICE[text()='40']")).
			log().all();
	}
}
