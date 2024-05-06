package tests;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

public class XMLSchemaXSD {

	
	@Test
	public void XMLValidation() throws IOException {
		File file = new File("./SoapRequest/add.xml");

		if ((file).exists())
			System.out.println("File Exists");

		FileInputStream fileInputStream = new FileInputStream(file);
		String requestBody = IOUtils.toString(fileInputStream, "UTF-8");
		baseURI = "http://dneonline.com";
		given().contentType("text/xml").
				accept("text/xml").
				body(requestBody).
		when().
				post("/calculator.asmx").
		then().
				statusCode(200).
				log().
				all().and().body("//*:AddResult.text()",equalTo("5"))
				.and().
				assertThat().body(matchesXsdInClasspath("calculator.xsd"));
	}
}
