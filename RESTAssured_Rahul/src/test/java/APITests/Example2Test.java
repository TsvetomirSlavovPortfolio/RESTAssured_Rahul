package APITests;

import static org.testng.Assert.*;
import java.util.ArrayList;
import org.testng.annotations.*;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Example2Test 
{

    private Response res = null; //Response
    private JsonPath jp = null; //JsonPath

    /*
    We should do setup operations, get JSON response from the API and put it into JsonPath object
    Then we will do query and manipulations with JsonPath class’s methods.
    We can do all of the preparation operations after @Before Junit annotation.
    */
    @BeforeClass
    public void setup (){
        //Test Setup
        RestUtil.setBaseURI("http://api.5min.com"); //Setup Base URI
        RestUtil.setBasePath("video"); //Setup Base Path
        //In this example, I assigned full path manually in below code line.
        RestUtil.path = "list/info.json?video_ids=519218045&num_related_return=4";
        RestUtil.setContentType(ContentType.JSON); //Setup Content Type
        res = RestUtil.getResponse(); //Get response
        jp = RestUtil.getJsonPath(res); //Set JsonPath
    }

    @Test
    public void T01_StatusCodeTest() {
        //Verify the http response status returned. Check Status Code is 200?
        HelperMethods.checkStatusIs200(res);
    }

    @Test
    public void T02_SearchTermTest() {
        //Verify the response contained the relevant search term (519218045)
        assertEquals(HelperMethods.getVideoIdList(jp).get(0).toString(), "519218045", "ID does not match!");
    }

    @Test
    public void T03_verifyExtraFourVideosReturned() {
        //Verify that extra 4 video entries were returned as related videos
        assertEquals(HelperMethods.getRelatedVideoIdList(jp).size(), 4, "Related video Size is not equal to 4");
    }

    @SuppressWarnings("unchecked")
	@Test
    public void T04_duplicateVideoVerification() {
        //Check duplicate videos exist?
        assertTrue(HelperMethods.findDuplicateVideos(getMergedVideoLists()), "Duplicate videos exist!");
    }

    @Test
    public void T05_printAttributes() {
        //Print attributes
        printAttributes(jp);
    }

    @AfterClass
    public void afterTest (){
        //Reset Values
        RestUtil.resetBaseURI();
        RestUtil.resetBasePath();
    }

    //*******************
    //***Local Methods***
    //*******************
    //Returns Merged Video Lists (Video List + Related Video List)
    @SuppressWarnings("rawtypes")
	private ArrayList getMergedVideoLists (){
        return HelperMethods.mergeLists(HelperMethods.getVideoIdList(jp), HelperMethods.getRelatedVideoIdList(jp));
    }

    //Prints Attributes
    private void printAttributes(JsonPath jp) {
        for(int i=0; i <getMergedVideoLists().size(); i++ ) {
            //Prints Video List Attributes
            if(jp.get("items.title[" + i + "]") != null) {
                System.out.println("title: " + jp.get("items.title[" + i + "]"));
                System.out.println("Tablets: " + jp.get("items.permittedDeviceTypes.Tablets[" + i + "]"));
                System.out.println("Handsets: " + jp.get("items.permittedDeviceTypes.Handsets[" + i + "]"));
                System.out.println("ConnectedDevices: " + jp.get("items.permittedDeviceTypes.ConnectedDevices[" + i + "]"));
                System.out.println("Computers: " + jp.get("items.permittedDeviceTypes.Computers[" + i + "]"));
                System.out.println("Duration: " + jp.get("items.duration[" + i + "]"));
                System.out.print("\n");

                //Check that sent video has related videos? If yes print their attributes
                if (jp.get("items.related.title[" + i + "][" + i + "]") != null) {
                    for (int j = 0; j < HelperMethods.getRelatedVideoIdList(jp).size(); j++) {
                        System.out.println("title: " + jp.get("items.related.title[0][" + j + "]"));
                        System.out.println("Tablets: " + jp.get("items.related.permittedDeviceTypes.Tablets[0][" + j + "]"));
                        System.out.println("Handsets: " + jp.get("items.related.permittedDeviceTypes.Handsets[0][" + j + "]"));
                        System.out.println("ConnectedDevices: " + jp.get("items.related.permittedDeviceTypes.ConnectedDevices[0][" + j + "]"));
                        System.out.println("Computers: " + jp.get("items.related.permittedDeviceTypes.Computers[0][" + j + "]"));
                        System.out.println("Duration: " + jp.get("items.related.duration[0][" + j + "]"));
                        System.out.print("\n");
                    }
                }
            }
        }
    }
}
