import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.commonMethods;
import files.payload;
import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@DataProvider (name = "books-data")
    public Object[][] getData()
    {
         return new Object[][] {{"tessfdfdasfde", "11111utr"},{"dsddsasfde", "222222gfdfgtr"},{"dfdfasfde", "33333diutr"}};
    }

	
	@Test (dataProvider = "books-data")
	public void addBook(String isbn, String aisle) {
		
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json")
		.body(payload.AddBook(isbn, aisle))
		.when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = commonMethods.rawToJson(response);
		String id = js.get("ID");
		System.out.println(id);
	}

}
