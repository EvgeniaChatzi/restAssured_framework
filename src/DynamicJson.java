import org.testng.annotations.Test;

import files.commonMethods;
import files.payload;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test
	public void addBook() {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json")
		.body(payload.AddBook())
		.when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = commonMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}

}
