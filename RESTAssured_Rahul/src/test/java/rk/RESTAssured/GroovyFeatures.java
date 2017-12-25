package rk.RESTAssured;
import rk.RESTAssured.ExtentReport;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.path.json.JsonPath.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class GroovyFeatures 
{	
	//  http://james-willett.com/2015/06/rest-assured-extract-json-response/
	//  http://james-willett.com/2017/05/rest-assured-gpath-json/
	public static ExtentReports extent=ExtentReport.startReport();
	public static ExtentTest test=null;
	
/*	public GroovyFeatures(ExtentHtmlReporter htmlReporter, ExtentReports extent, ExtentTest test)
	{
		super(htmlReporter, extent, test);
	}*/

	@BeforeClass
	public void setUPREstAssured()
	{
	    RestAssured.baseURI = "http://services.groupkt.com";
	    RestAssured.basePath = "/country/";
/*	    RestAssured.port = 80;
	    RestAssured.authentication = basic("username", "password");
	    RestAssured.rootPath = "store.book";*/
	}

	//Example 1 – Use path() on Response
	@Test(groups = { "Groovy", "Search_Text_Lands" },priority=1)
	public void testPathOnResponse()
	{
		test = extent.createTest("This test will create logs for the test case");
		test.log(Status.INFO,"I am in testPathOnResponse test case");
		Response resp = get("search?text=lands");
		System.out.println(resp.path("RestResponse.result.name"));
		System.out.println(resp.path("RestResponse.result.name[0]"));  //find the first “name”
		System.out.println(resp.path("RestResponse.result.name[-1]")); //find the last “name”
		
		List<String> jsonResponse = resp.jsonPath().getList("RestResponse.result.name");
		System.out.println(jsonResponse.get(0));					   //find the first “name”
		
		Map<String, String> company = resp.jsonPath().getMap("RestResponse.result[0]");
        System.out.println(company.get("name"));					   //find the first “name”
        
        List<Map<String, String>> companies = resp.jsonPath().getList("RestResponse.result");
        System.out.println(companies.get(0).get("name"));			   //find the first “name”
        ExtentReport.tearDown();
	}

	//Example 2 – Specify JSONPath
	@Test (groups = { "Groovy", "Search_Text_Lands" },priority=2)
	public void testJSONPathOnResponse()
	{
		Response resp = get("search?text=lands");
		JsonPath jsonPath = new JsonPath(resp.asString());
		System.out.println(jsonPath.get("RestResponse.result.name"));
		
		JsonPath jsonPath_2 = new JsonPath(resp.asString()).setRoot("RestResponse.result");
		System.out.println(jsonPath_2.get("name"));
		
/*        List<String> jsonPath_3 = resp.jsonPath().getList("$"); //$ notation which means the root element.
        System.out.println(jsonPath_3);*/
	}
	
	//Example 3 – Response as String 
	@Test(groups = { "Groovy", "Search_Text_Lands" },priority=3)
	public void testStringOnResponse()
	{		
	    String responseAsString = get("search?text=lands").asString();
	    System.out.println(JsonPath.from(responseAsString).get("RestResponse.result.name"));
	}
	
	//Example 4 – Single Line
	@Test(groups = { "Groovy", "Search_Text_Lands" },priority=3)
	public void testSingleLineResponse()
	{
	    System.out.println(get("search?text=lands").path("RestResponse.result.name"));
	}
	
	//loop over it, using find() and findAll() etc
	@Test(groups = { "Groovy", "Search_Text_Lands", "Football" },priority=3)
	public void testLengthOfResponse()
	{
		given().
		get("search?text=lands").
		then().
		body("RestResponse.result.alpha3_code*.length().sum()", greaterThan(40));
			
	    Response response = get("http://api.football-data.org/v1/teams/66/players");
	    int sumOfJerseys = response.path("players.collect { it.jerseyNumber }.sum() ");
	    System.out.println(sumOfJerseys);
	}
	
	// find the single player (because find() only returns one result) with a nationality of “Argentina”:
	@Test (groups = { "Groovy", "Search_Text_Lands" },priority=3)
	public void extractMapOfObjectWithFindAllAndFind() 
	{
	    Response response = get("http://api.football-data.org/v1/teams/66/players");
	    Map<String,?> playerOfCertainPosition = response.path("players.findAll { it.position == \"Centre-Back\" }.find { it.nationality == \"Argentina\" }");
	    System.out.println(playerOfCertainPosition);
	}
	
	// Use Parameters in your GPath JSON Expressions
	@Test (groups = { "Groovy", "Search_Text_Lands" },priority=3)
	public void extractMapOfObjectWithFindAllAndFindUsingParameters() {
	    Response response = get("http://services.groupkt.com/country/search?text=lands");
	    String code_2 = "NL";
	    String code_3 = "NLD";
	    Map<String,?> countryName= 
	    		response.path("RestResponse.result.findAll { it.alpha2_code == '%s' }.find { it.alpha3_code == '%s' }",code_2, code_3);
	    System.out.println(countryName);
	}
	
	
	@Test (groups = { "Groovy", "Search_Text_Lands" },priority=3)
	public void extractMapOfElementsWithFind()
	{	    
	    Response response = get("search?text=lands");
	    Map<String,?> allTeamDataForSingleTeam = response.path("RestResponse.result.find { it.name == 'Netherlands' }");
	    System.out.println(allTeamDataForSingleTeam);
	    
	    String  allTeamDataForSingleTeam_2 = response.path("RestResponse.result.find { it.alpha2_code == 'NL' }.name");
	    System.out.println(allTeamDataForSingleTeam_2);
	    
		String resp = get("search?text=lands").asString();
		List<String> ls = from(resp).getList("RestResponse.result.findAll{it.name.length()>20}.name");
		System.out.println("Country Name  := "+ls.toString());
	}
	
	//Extract Single Value with Max and Min
	@Test (groups = { "Groovy", "Football" },priority=1)
	public void extractSingleValueWithHighestValueOfOtherElement()
	{	    
	    Response response = get("http://api.football-data.org/v1/teams/66/players");
	    String highestNumberPlayer = response.path("players.max { it.jerseyNumber }.name");
	    System.out.println(highestNumberPlayer); //max
	    
	    String lowestNumberPlayer = response.path("players.min { it.jerseyNumber }.name");
	    System.out.println(lowestNumberPlayer);  //min 
	}
	
	@Test (groups = { "Groovy", "Search_Text_Lands" },priority=1)
	public void testGETResponseAsList()
	{
		String resp = get("search?text=lands").asString();
		List<String> ls = from(resp).getList("RestResponse.result.name");
		System.out.println("List Size := "+ls.size());
		for (String name : ls)
		{
			if (name.equals("Netherlands"))
			{
				System.out.println("Yup, Netherlands Country found :");
			} 
		}
	}
}
