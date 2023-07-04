import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

public class JiraTests {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "http://localhost:8080";
		
		SessionFilter session = new SessionFilter();
		
		//Login
		String response = given().header("Content-Type", "application/json").body("{ \"username\": \"chatzievgenia7\", \"password\": \"Jira777\" }").log().all()
		.filter(session)
		.when().post("/rest/auth/1/session")
		.then().log().all().extract().response().asString();
		
		// Add Comment
		given().pathParam("id", "10000").log().all().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \"Coming from RestAssured!!This is my first comment!!!!!\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201);

	}

}
