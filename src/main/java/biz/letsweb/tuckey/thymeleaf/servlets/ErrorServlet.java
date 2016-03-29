package biz.letsweb.tuckey.thymeleaf.servlets;

import biz.letsweb.tuckey.thymeleaf.aspects.Timed;
import static biz.letsweb.tuckey.thymeleaf.servlets.ErrorServlet.VIEW_NAME;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author toks
 */
@WebServlet(name = "error", urlPatterns = "/" + VIEW_NAME + "-servlet")
public class ErrorServlet extends HttpServlet {

    public static final String VIEW_NAME = "error";

    @Override
    @Timed(message = "handling of an error")
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp) {
        prepareErrorData(req);
    }

    private void prepareErrorData(final HttpServletRequest req) {
        final String errorRequestURI = (String) req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        final Object errorCode = req.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        final Object errorType = req.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        Throwable throwable = (Throwable) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (throwable != null) {
            final String errorMessage = throwable.getMessage();
            req.setAttribute("errorMessage", errorMessage.isEmpty() ? "no message" : errorMessage);
        } else {
            throwable = new Throwable("Not allowed, sorry.");
            req.setAttribute("errorMessage", throwable.getMessage());
        }
        req.setAttribute("errorRequestURI", errorRequestURI);
        req.setAttribute("errorType", errorType);
        req.setAttribute("errorCode", errorCode);
    }
}
