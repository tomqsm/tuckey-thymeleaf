package biz.letsweb.tuckey.thymeleaf.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author toks
 */
public class TestJson {

    public void setJsonAttributes(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("currentTime", System.currentTimeMillis());
        req.setAttribute("request", req);
        req.setAttribute("items", new String[]{"item1", "item2"});
    }
}
