package com.ajs.pdfimagestool;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ExtractImagesTool extends PDFStreamEngine {
	private final String filePath;
	private final String outputDir;

	public ExtractImagesTool(String pdfFilePath, String imagesOutputDir) {
		this.filePath = pdfFilePath;
		this.outputDir = imagesOutputDir;
	}

	public void execute() {
		try {
			File file = new File(filePath);
			PDDocument pdfDocument = PDDocument.load(file);

			for (PDPage page : pdfDocument.getPages()) {
				processPage(page);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
		String operation = operator.getName();

		if ("Do".equals(operation)) {
			COSName objectName = (COSName) operands.get(0);
			PDXObject pdxObject = getResources().getXObject(objectName);

			if (pdxObject instanceof PDImageXObject) {
				// Image
				PDImageXObject image = (PDImageXObject) pdxObject;
				BufferedImage bImage = image.getImage();

				// File
				String randomName = UUID.randomUUID().toString();
				File outputFile = new File(outputDir, "IMG_" + randomName + ".png");

				// Write image to file
				ImageIO.write(bImage, "PNG", outputFile);

			} else if (pdxObject instanceof PDFormXObject) {
				PDFormXObject form = (PDFormXObject) pdxObject;
				showForm(form);
			}
		}

		else
			super.processOperator(operator, operands);
	}
}
