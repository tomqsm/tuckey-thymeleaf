package biz.letsweb.tuckey.thymeleaf.servlets;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 *
 * @author toks
 */
public class ThymeleafConfig {

    private static TemplateResolver TEMPLATE_RESOLVER;

    public static TemplateEngine getConfiguredTemplateEngine() {
        initializeTemplateResolverOnce();
        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(TEMPLATE_RESOLVER);
        return templateEngine;
    }

    private static void initializeTemplateResolverOnce() {
        if (TEMPLATE_RESOLVER == null) {
//            TEMPLATE_RESOLVER = new FileTemplateResolver();
            TEMPLATE_RESOLVER = new ServletContextTemplateResolver();
            // XHTML is the default mode, but we will set it anyway for better understanding of code
            TEMPLATE_RESOLVER.setTemplateMode("XHTML");
//            TEMPLATE_RESOLVER.setPrefix("c:\\Users\\toks\\Documents\\NetBeansProjects\\spa\\front-end\\dist\\html\\");
            TEMPLATE_RESOLVER.setPrefix("/html/");
            TEMPLATE_RESOLVER.setSuffix(".html");
            TEMPLATE_RESOLVER.setTemplateMode("HTML5");
            TEMPLATE_RESOLVER.setCacheTTLMs(3600000L);
        }
    }
}
