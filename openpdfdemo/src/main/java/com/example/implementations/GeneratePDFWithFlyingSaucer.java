package com.example.implementations;

import com.example.utils.CustomElementFactoryImpl;
import com.example.utils.TemplateParseUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class GeneratePDFWithFlyingSaucer {

    private static final String HTML_INPUT = "openpdfdemo/src/main/resources/templates/test.html";
    private static final String PDF_OUTPUT = "openpdfdemo/src/main/resources/output/plantilla.pdf";

    private static final  String TEMPLATE_NAME = "cartabienvenida";

    private static final  String TEMPLATE_CORREO_LIBRANZA = "plantillacorreolibranza";


    public static void main(String[] args) {
        try {
            GeneratePDFWithFlyingSaucer htmlToPdf = new GeneratePDFWithFlyingSaucer();
            htmlToPdf.generateHtmlToPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateHtmlToPdf() throws Exception {
        File inputHTML = new File(HTML_INPUT);
        Document inputHtml = createWellFormedHtml(inputHTML);
        File outputPdf = new File(PDF_OUTPUT);
        xhtmlToPdf(inputHtml, outputPdf);
    }

    private Document createWellFormedHtml(File inputHTML) {
        Document document = Jsoup.parse(TemplateParseUtils.generateHTMLDocument(TEMPLATE_NAME));
        /* code to generate a pdf from a File
         Document document = Jsoup.parse(inputHTML, "UTF-8");
         */
        document.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

    private void xhtmlToPdf(Document xhtml, File outputPdf) throws Exception {
        try (OutputStream outputStream = new FileOutputStream(outputPdf)) {
            ITextRenderer renderer = new ITextRenderer();
            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            sharedContext.setReplacedElementFactory(new CustomElementFactoryImpl());
            renderer.setDocumentFromString(xhtml.html());
            renderer.layout();
            renderer.createPDF(outputStream);
        }
    }

}
