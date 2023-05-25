package com.example.implementations;

import com.example.utils.TemplateParseUtils;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.html.HtmlParser;
import com.lowagie.text.pdf.PdfWriter;
import org.jsoup.Jsoup;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.jsoup.nodes.Entities.EscapeMode.xhtml;

public class GeneratePDFWithOpenPDF {

    private static final String TEMPLATE_NAME = "cartabienvenida";

    private static final  String TEMPLATE_CORREO_LIBRANZA = "plantillacorreolibranza";
    private static final  String HTML_DOCUMENT_TEST = "HTMLdocument";

    private static final  String TEMPLATE_CARTA_BIENVENIDA = "cartabienvenida";

    public static void main(String[] args) throws IOException {
        //generateSimpleExample();
        //testReadingFromInputStream();
        generatePDFUsingOpenPDF();

    }

    private static void generateSimpleExample() {
        // 1) Create Instance a object from the class com.lowagie.text.Document
        Document myPDFDoc = new Document();

        try {
            //2) Create a FileOutputStream object with the path and name of the file
            FileOutputStream pdfOutputFile = new FileOutputStream("openpdfdemo/src/main/resources/output/sample1.pdf");

            //3) Now we get a file writer instance from the class com.lowagie.text.pdf.PdfWriter
            final PdfWriter pdfWriter = PdfWriter.getInstance(myPDFDoc, pdfOutputFile);

            //4) Once the Filewritter is set up we can open
            //   the document and start adding content
            myPDFDoc.open();  // Open the Document

            // Add a text within a Paragraph
            // (we can add objects from classes implementing the interface com.lowagie.text.Element )

            myPDFDoc.add(new Paragraph("This is a simple PDF created with openPDF"));

            HtmlParser.parse(myPDFDoc, ("openpdfdemo/src/main/resources/templates/test.html"));
            myPDFDoc.close(); //5) Close the Document

            pdfWriter.close();//6) close the File writer

        } catch (IOException de) {
            System.err.println(de.getMessage());
        }
    }

    private static void testReadingFromInputStream() {
        try (Document document = new Document()) {
            //
            PdfWriter.getInstance(document, new FileOutputStream("openpdfdemo/src/main/resources/output/inputstream.pdf"));
            document.open();
            HtmlParser.parse(document, getFileFromResourceAsStream("templates/test.html"));
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }
    }

    private void generatePDFFromHtmlCss() throws FileNotFoundException {
        Document document = new Document();
        document.setPageSize(PageSize.LETTER);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream("contact.pdf"));
        document.open();

        //XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, new StringReader("test"));

        //HtmlParser.parse(document, getFileFromResourceAsStream("templates/test.html"));
    }


    private static InputStream getFileFromResourceAsStream(String fileName) {
        // The class loader that loaded the class
        ClassLoader classLoader = GeneratePDFWithOpenPDF.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
    private static void generatePDFString() {
        try {
            final String htmlText = "<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "<style>\n" +
                    "body {\n" +
                    "  background-color: linen;\n" +
                    "}\n" +
                    "\n" +
                    "h1 {\n" +
                    "  color: maroon;\n" +
                    "  margin-left: 40px;\n" +
                    "}\n" +
                    "</style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "\n" +
                    "<h1>This is a heading</h1>\n" +
                    "<p>This is a paragraph.</p>\n" +
                    "\n" +
                    "</body>\n" +
                    "</html>";

            final StringReader reader = new StringReader(htmlText);

            try (Document document = new Document()) {
                PdfWriter.getInstance(document, new FileOutputStream("openpdfdemo/src/main/resources/output/pdfitextstring.pdf"));
                document.open();
                HtmlParser.parse(document, reader);
            } catch (DocumentException | IOException de) {
                System.err.println(de.getMessage());
            }
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        }
    }

    private static void generatePDFUsingOpenPDF() {
        try {
            try (Document document = new Document()) {
                document.setPageSize(PageSize.LETTER);
                PdfWriter.getInstance(document, new FileOutputStream("openpdfdemo/src/main/resources/output/openpdf.pdf"));
                document.open();
                final StringReader reader = new StringReader(TemplateParseUtils.generateHTMLDocument(TEMPLATE_CARTA_BIENVENIDA));
                HtmlParser.parse(document, reader);
            } catch (DocumentException | IOException de) {
                System.err.println(de.getMessage());
            }
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        }
    }

    private static void generateHtmlFile(Writer writer) throws IOException {
        String html = writer.toString();

        Path path = Paths.get("openpdfdemo/src/main/resources/templates/generarated.html");
        byte[] strToBytes = html.getBytes();

        Files.write(path, strToBytes);
    }

    private static void htmlToPdtFlyingSaucer() throws IOException {
        File inputHTML = new File(TemplateParseUtils.generateHTMLDocument(TEMPLATE_NAME).toString());
        org.jsoup.nodes.Document parse = Jsoup.parse(inputHTML, "UTF-8");
        parse.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.html);

        try (OutputStream outputStream = new FileOutputStream("contact.pdf")) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(xhtml.toString());
            renderer.layout();
            renderer.createPDF(outputStream);
        }
    }
}
