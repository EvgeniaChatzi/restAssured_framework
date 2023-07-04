import static io.restassured.RestAssured.*;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

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
		
		String expectedMessage = "Hi how are you?";
		
		// Add Comment
		String addCommentResponse = given().pathParam("id", "10000").log().all().header("Content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMessage+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(session)
		.when().post("rest/api/2/issue/{id}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath commentJs = new JsonPath(addCommentResponse);
		String commentId = commentJs.get("id");

		//Get issue
		String issueDetails = given().filter(session).pathParam("id", "10000").queryParam("fields", "comment").log().all()
		.when().get("rest/api/2/issue/{id}")
		.then().log().all().extract().response().asString();
		
		System.out.println(issueDetails);
		
		JsonPath issueDetailsJs = new JsonPath(issueDetails);
		int commentsCount = issueDetailsJs.get("fields.comment.comments.size()");
		
		//Find comment from a loop by its id
		for (int i = 0; i<commentsCount; i++) 
		{
			
			String commentIdIssue = issueDetailsJs.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equals(commentId)) {
				String message = issueDetailsJs.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
			}	
		}
		
		
		
		
	}
	

}
