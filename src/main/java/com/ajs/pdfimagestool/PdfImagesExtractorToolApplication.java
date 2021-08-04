package com.ajs.pdfimagestool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PdfImagesExtractorToolApplication {

	public static void main(String[] args) {
		SpringApplication.run(PdfImagesExtractorToolApplication.class, args);
		//LOCATION OF THE PDF
		String pdfFilePath = "C:\\Aravind\\AJS_ AUSTRIA\\content.pdf";
		
		//FOLDER WHERE THE IMAGES NEED TO BE EXTRACTED
		String imagesOutputDirectory = "C:\\Aravind\\AJS_ AUSTRIA\\ExtractedImages";

		ExtractImagesTool extractImagesTool = new ExtractImagesTool(pdfFilePath, imagesOutputDirectory);
		extractImagesTool.execute();
	}

}
