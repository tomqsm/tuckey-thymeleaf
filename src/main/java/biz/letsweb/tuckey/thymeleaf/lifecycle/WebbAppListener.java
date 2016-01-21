package biz.letsweb.tuckey.thymeleaf.lifecycle;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * Web application lifecycle listener.
 *
 * @author toks
 */
public class WebbAppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("*** TUCKEY-THYMELEAF INITALISING ***");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
     
    }
}
