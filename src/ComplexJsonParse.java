import org.testng.annotations.Test;

import files.mocks;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	@Test
	public void testClass() {
		// TODO Auto-generated method stub
		
		JsonPath js = new JsonPath(mocks.CoursePrice());

	}

}
 