package com.example.implementations;

import com.example.utils.TemplateParseUtils;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.*;
import java.nio.file.FileSystems;
/*
 Encontratas dos formas de crear el pdf uno cuando se modifica el namespace de la plantilla para que puedo coger y evitar
 el erro de html not well-formed y el otro para generar uno con el que se le modifica ese namespace
 */
public class GeneratePDFWithItextOpenHtmlToPDF {
    private static final String HTML_INPUT = "openpdfdemo/src/main/resources/templates/test.html";
    private static final String PDF_OUTPUT = "openpdfdemo/src/main/resources/output/plantillaopenhtml.pdf";
    private static final  String TEMPLATE_CORREO_LIBRANZA = "plantillacorreolibranza";
    private static final  String TEMPLATE_CARTA_BIENVENIDA_MODIFIED = "cartabienvenida_modified";
    private static final  String TEMPLATE_CARTA_BIENVENIDA = "cartabienvenida";
    private static final  String TEMPLATE_CORREO_LIBRANZA_SUC = "plantillalibranzasuc";
    private static final  String TEMPLATE_LIBRE_INVERSION = "libreinversion";
    private static final  String TEMPLATE_CORREO_LIBRANZA_COL_SUC = "plantillacorreolibranzacolsuc";
    private static final  String TEMPLATE_CORREO_CREDITO = "plantillacorreopdfcopy";

    public static void main(String[] args) {
        try {
            GeneratePDFWithItextOpenHtmlToPDF htmlToPdf = new GeneratePDFWithItextOpenHtmlToPDF();
            htmlToPdf.generateHtmlToPdfWithoutModifiedTemplate(TEMPLATE_CARTA_BIENVENIDA);
            htmlToPdf.generateHtmlToPdfWithTemplateModified(TEMPLATE_CARTA_BIENVENIDA_MODIFIED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateHtmlToPdfWithoutModifiedTemplate(String template) throws IOException {
        Document doc = createWellFormedHtml(template);
        xhtmlToPdfWithoutModifiedTemplate(doc, String.format("openpdfdemo/src/main/resources/output/%s.%s", template, "pdf"));
    }

    private void generateHtmlToPdfWithTemplateModified(String template) throws IOException {
        xhtmlToPdfWithTemplateModified(template, String.format("openpdfdemo/src/main/resources/output/%s.%s", template, "pdf"));
    }
    private Document createWellFormedHtml(String template) {
        org.jsoup.nodes.Document document = Jsoup.parse(TemplateParseUtils.generateHTMLDocument(template));
        //Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);
        return document;
    }

    private void xhtmlToPdfWithTemplateModified(String template, String output) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(TemplateParseUtils.generateHTMLDocument(template), "");
        builder.toStream(baos);
        builder.run();

        byte[] pdfContent = baos.toByteArray();
        PDDocument document = PDDocument.load(pdfContent);
        document.save(output);
    }


    private void xhtmlToPdfWithoutModifiedTemplate(Document doc, String outputPdf) throws IOException {
        try (OutputStream os = new FileOutputStream(outputPdf)) {
            String baseUri = FileSystems.getDefault()
                    .getPath("src/main/resources/")
                    .toUri()
                    .toString();

            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(outputPdf);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
            builder.run();
        }
    }
}
