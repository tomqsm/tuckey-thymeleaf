package biz.letsweb.tuckey.thymeleaf.servlets;

import biz.letsweb.tuckey.thymeleaf.aspects.Timed;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

/**
 *
 * @author toks
 */
public class ThymeleafTemplateLoaderFilter implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;
    private static final String DEFAULT_VIEW = "error";

    /**
     * Init method for this filter
     * @param filterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;

    }

    /**
     Write code here to process the request and/or response before the rest of the filter chain is invoked.
     @param request
     @param response
     @throws IOException
     @throws ServletException
     */
    @Timed(message = "Before - doing nothing at the moment.")
    private void doBeforeProcessing(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
    }

    /**
     Write code here to process the request and/or response after the rest of the filter chain is invoked.
     @param request
     @param response
     @throws IOException
     @throws ServletException
     */
    @Timed(message = "After - Prepraring thymeleaf template.")
    private void doAfterProcessing(final HttpServletRequest request, final HttpServletResponse response)
            throws IOException, ServletException {
        final TemplateEngine configuredTemplateEngine = ThymeleafConfig.getConfiguredTemplateEngine(filterConfig);
        final ServletContext servletContext = filterConfig.getServletContext();
        final WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        if (!isViewSet(request)) {
            setDefaultView(request);
            addMessageViewMustBeSet(request);
        }
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            final String view = request.getAttribute("view").toString();
            configuredTemplateEngine.process(view, ctx, writer);
        }
    }

    private void setDefaultView(final HttpServletRequest request) {
        request.setAttribute("view", DEFAULT_VIEW);
    }

    private void addMessageViewMustBeSet(final HttpServletRequest request) {
        request.setAttribute("isViewMissing", true);
    }

    private boolean isViewSet(final HttpServletRequest request) {
        Object viewName = request.getAttribute("view");
        return viewName != null;
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        doBeforeProcessing(req, resp);

        Throwable problem = null;
        try {
            chain.doFilter(request, response);
        } catch (Throwable t) {
            // If an exception is thrown somewhere down the filter chain,
            // we still want to execute our after processing, and then
            // rethrow the problem after that.
            problem = t;
            t.printStackTrace();
        }

        doAfterProcessing(req, resp);

        // If there was a problem, we want to rethrow it if it is
        // a known type, otherwise log it.
        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
        }
    }

    /**
     * Return the filter configuration object for this filter.
     * @return
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter 
     */
    @Override
    public void destroy() {
    }

}
