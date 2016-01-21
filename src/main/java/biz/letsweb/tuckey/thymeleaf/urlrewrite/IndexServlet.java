package biz.letsweb.tuckey.thymeleaf.urlrewrite;

import static biz.letsweb.tuckey.thymeleaf.urlrewrite.IndexServlet.VIEW_NAME;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

/**
 *
 * @author toks
 */
@WebServlet(urlPatterns = "/" + VIEW_NAME + "-servlet")
public class IndexServlet extends HttpServlet {

    public static final String VIEW_NAME = "index";

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final TemplateEngine templateEngine = ThymeleafConfig.getConfiguredTemplateEngine();
        final ServletContext servletContext = getServletConfig().getServletContext();
        final WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        templateEngine.process(VIEW_NAME, ctx, resp.getWriter());

    }

    /**
     This method is called from Tuckey filter. See urlrewrite.xml for specification.
     @param req
     @param resp
     @throws ServletException
     @throws IOException
     */
    public void tuckeyFilterBefore(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("someParamName", getServletConfig().getInitParameter("someParamName"));
        req.setAttribute("test", "works ok");
    }
}
