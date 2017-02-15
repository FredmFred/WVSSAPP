package wvssappclub.de.wvssapp;

/**
 * Created by Fred on 15.02.2017.
 */

import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PdfParser {
    private PDFParser parser = null;

    // Extract text from PDF Document
    public PdfParser(File pdf) {
        if (!pdf.isFile()) {
            System.err.println("File " + pdf.toString() + " does not exist.");
        }
        //Set up instance of PDF parser
        try {
            parser = new PDFParser(new FileInputStream(pdf));

        } catch (IOException e) {
            System.err.println("Unable to open PDF Parser." + e.getMessage());
        }
    }

    //-------------------------------

    public String getParsedText() {
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        String parsedText = null;

        try {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            parser.parse();
            cosDoc = parser.getDocument();
            pdDoc = new PDDocument(cosDoc);

            //get list of all pages
            List list = pdDoc.getDocumentCatalog().getAllPages();

            //note that you can print out any pages you want
            //by choosing different values of the start and end page
            pdfStripper.setStartPage(1); //1-based
            int length = list.size(); //total number of pages
            pdfStripper.setEndPage(length); //last page

            //get the text for the pages selected
            parsedText = pdfStripper.getText(pdDoc);
        } catch (IOException e) {
            System.err.println("An exception occured in parsing the PDF Document " + e.getMessage());
        }
        return parsedText;
    }
}
