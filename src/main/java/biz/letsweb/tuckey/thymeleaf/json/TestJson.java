package biz.letsweb.tuckey.thymeleaf.json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author toks
 */
public class TestJson {

    public void getJson(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute("currentTime", System.currentTimeMillis());
    }
}
