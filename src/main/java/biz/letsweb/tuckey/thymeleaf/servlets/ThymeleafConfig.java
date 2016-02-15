package biz.letsweb.tuckey.thymeleaf.servlets;

import javax.servlet.FilterConfig;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 *
 * @author toks
 */
public class ThymeleafConfig {

    private static TemplateResolver TEMPLATE_RESOLVER;

    public static TemplateEngine getConfiguredTemplateEngine(FilterConfig filterConfig) {
        initializeTemplateResolverOnce(filterConfig);
        final TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(TEMPLATE_RESOLVER);
        return templateEngine;
    }

    private static void initializeTemplateResolverOnce(FilterConfig filterConfig) {
        if (TEMPLATE_RESOLVER == null) {
//            TEMPLATE_RESOLVER = new FileTemplateResolver();
//            TEMPLATE_RESOLVER.setPrefix("c:\\Users\\toks\\Documents\\NetBeansProjects\\spa\\front-end\\dist\\html\\");
            TEMPLATE_RESOLVER = new ServletContextTemplateResolver();
            TEMPLATE_RESOLVER.setCharacterEncoding("UTF-8");
            TEMPLATE_RESOLVER.setPrefix(filterConfig.getInitParameter("setPrefix"));
            TEMPLATE_RESOLVER.setSuffix(filterConfig.getInitParameter("setSuffix"));
            TEMPLATE_RESOLVER.setTemplateMode(filterConfig.getInitParameter("setTemplateMode"));
            TEMPLATE_RESOLVER.setCacheTTLMs(Long.parseLong(filterConfig.getInitParameter("setCacheTTLMs")));
        }
    }
}
