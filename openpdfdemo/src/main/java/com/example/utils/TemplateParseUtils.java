package com.example.utils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeConstants;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class TemplateParseUtils {
    public static String generateHTMLDocument(String templateName) throws ParseErrorException, MethodInvocationException, ResourceNotFoundException {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "class");
        ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        ve.setProperty("file.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
        ve.setProperty("input.encoding", StandardCharsets.UTF_8.name());
        ve.setProperty("file.resource.loader.cache", "false");
        //ve.setProperty("file.resource.loader.pat", "C:\testing/");
        ve.setProperty("file.resource.loader.modificationCheckInterval", "2");
        ve.init();
        Template template = ve.getTemplate(String.format("templates/%s.vm", templateName));

        VelocityContext context = new VelocityContext();
        //escoger los parametros a setear en el html deacuerdo al template enviado
        if(templateName.equals("cartabienvenida")) {
            loadCartaBienvenidaContextParameters(context);
        } else if(templateName.equals("cartabienvenida_modified")) {
            loadCartaBienvenidaContextParameters(context);
        } else if (templateName.equals("libreinversion")) {
            loadCorreoContexLibreInversionParameters(context);
        } else {
            loadCorreoContextParameters(context);
        }

        StringWriter writer = new StringWriter();
        template.merge(context, writer);
        return writer.toString();
    }
    private static void loadCorreoContexLibreInversionParameters( VelocityContext context) {
        context.put("NOMBRE_CLIENTE", "Test");
        context.put("FECHA_DESEMBOLSO", "2023-05-29");
        context.put("FECHA_PRIMER_PAGO", "2023-05-29");
        context.put("VALOR_DESEMBOLSO", "12.000.000");
        context.put("PERIODICIDAD_PAGO", "Mensual");
        context.put("DIA_PAGO_SIGUIENTE", "Jueves");
        context.put("TASA_FIJA_NOMINAL", "17.5");
        context.put("TASA_INTERES", "10.5");
        context.put("PLAZO_CREDITO", "54");
        context.put("VALOR_CUOTA", "1.457.154");
        context.put("TIPO_TASA", "test");
        context.put("PUNTOS_DTF", "147");
        context.put("TIPO_CUENTA", "Ahorros");
        context.put("FWK_ENVIAR_CORREO_NOMBRE_CLIENTE", "TEST");
        //context.put("RUTA_IMG", "src/main/resources");
        context.put("RUTA_IMG", "C:\\testing");
    }
    private static void loadCorreoContextParameters( VelocityContext context) {
        context.put("NOMBRE_CLIENTE", "Test");
        context.put("FECHA_DESEMBOLSO", "2023-05-29");
        context.put("VALOR_DESEMBOLSO", "12.000.000");
        context.put("PERIODICIDAD_PAGO", "Mensual");
        context.put("DIA_PAGO_SIGUIENTE", "Jueves");
        context.put("TASA_FIJA_NOMINAL", "17.5");
        context.put("TASA_INTERES", "10.5");
        context.put("PLAZO_CREDITO", "54");
        context.put("VALOR_CUOTA", "1.457.154");
        context.put("TIPO_TASA", "test");
        context.put("PUNTOS_DTF", "147");
        context.put("TIPO_CUENTA", "Ahorros");
        context.put("RUTA_IMG", "src/main/resources");
    }
    private static void loadCartaBienvenidaContextParameters(VelocityContext context) {
        context.put("NOMBRE_CLIENTE", "Test");
        context.put("FECHA", "2023-05-29");
        context.put("CIUDAD", "Cali");
        context.put("RUTA_IMG", "file:/C:/testing");
        context.put("CREDITO_NUMERO", "14574");
    }
}
