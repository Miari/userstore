package com.boroday.userstore.web.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Map;

public class PageGenerator {

    private static final String HTML_DIR = Paths.get("WEB-INF", "templates").toString();//+ "src/main/webapp/WEB-INF/templates";

    private static PageGenerator pageGenerator;
    private final Configuration configuration;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {

        Writer stream = new StringWriter();
        try {
            Template template = configuration.getTemplate(Paths.get(HTML_DIR, filename).toString());
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    public String getPage(String filename) {
        return getPage(filename, null);
    }

    private PageGenerator() {
        configuration = new Configuration();
    }
}
