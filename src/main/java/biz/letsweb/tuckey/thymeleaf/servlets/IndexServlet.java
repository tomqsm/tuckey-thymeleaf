package biz.letsweb.tuckey.thymeleaf.servlets;

import biz.letsweb.tuckey.thymeleaf.aspects.Timed;
import static biz.letsweb.tuckey.thymeleaf.servlets.IndexServlet.VIEW_NAME;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author toks
 */
@WebServlet(name = VIEW_NAME, urlPatterns = "/" + VIEW_NAME + "-servlet")
public class IndexServlet extends HttpServlet {

    public static final String VIEW_NAME = "index";

    @Override
    @Timed(message = "does nothing at the moment")
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    }

    /**
     This method is called from Tuckey filter. See urlrewrite.xml for specification.
     @param req
     @param resp
     @throws ServletException
     @throws IOException
     */
    @Timed(message = "index sets some params tucky method")
    public void setTuckeyParams(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("initParameter", getServletConfig().getInitParameter("initParameter"));
        req.setAttribute("test", "works ok");
    }
}
