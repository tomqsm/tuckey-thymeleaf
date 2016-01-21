package biz.letsweb.tuckey.thymeleaf.urlrewrite;

import static biz.letsweb.tuckey.thymeleaf.urlrewrite.ErrorServlet.VIEW_NAME;
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
public class ErrorServlet extends HttpServlet {

    public static final String VIEW_NAME = "error";

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
//        final String errorRequestURI = (String) req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        final Object errorCode = req.getAttribute("javax.servlet.error.status_code");
        final Object errorType = req.getAttribute("javax.servlet.error.exception_type");
        final Throwable throwable = (Throwable) req.getAttribute("javax.servlet.error.exception");
        if (throwable != null) {
            String errorMessage = throwable.getMessage();
            req.setAttribute("errorMessage", errorMessage.isEmpty() ? "no message" : errorMessage);
        }
        req.setAttribute("errorType", errorType);
        req.setAttribute("errorCode", errorCode);
    }
}
