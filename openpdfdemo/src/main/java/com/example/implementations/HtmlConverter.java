package com.example.implementations;

import com.example.utils.TemplateParseUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class HtmlConverter {

    private static final String TEMPLATE_NAME = "cartabienvenida";

    public static void main(String[] args) throws IOException {

        String html = TemplateParseUtils.generateHTMLDocument(TEMPLATE_NAME).toString();
        String xhtml = htmlToXhtml(html);
        xhtmlToPdf(xhtml, "openpdfdemo/src/main/resources/output/htmlconverter.pdf");
    }

    private static String htmlToXhtml(String html) {
        Document document = Jsoup.parse(html);
        document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        return document.html();
    }

    private static void xhtmlToPdf(String xhtml, String outFileName) throws IOException {
        File output = new File(outFileName);
        ITextRenderer iTextRenderer = new ITextRenderer();
        iTextRenderer.setDocumentFromString(xhtml);
        iTextRenderer.layout();
        OutputStream os = new FileOutputStream(output);
        iTextRenderer.createPDF(os);
        os.close();
    }
}
