package biz.letsweb.tuckey.thymeleaf.lifecycle;

import biz.letsweb.tuckey.thymeleaf.jmx.Counter;
import biz.letsweb.tuckey.thymeleaf.jmx.CounterMBean;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author toks
 */
public class WebbAppListener implements ServletContextListener {

    private ObjectName objectName;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("*** TUCKEY-THYMELEAF INITALISING ***");
        final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            objectName = new ObjectName("tuckey-thymeleaf:type=Counter");
            if (!server.isRegistered(objectName)) {
                CounterMBean mbean = new Counter();
                server.registerMBean(mbean, objectName);
            }
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(WebbAppListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceAlreadyExistsException ex) {
            Logger.getLogger(WebbAppListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MBeanRegistrationException ex) {
            Logger.getLogger(WebbAppListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotCompliantMBeanException ex) {
            Logger.getLogger(WebbAppListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        final MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        try {
            objectName = new ObjectName("tuckey-thymeleaf:type=Counter");
            if (server.isRegistered(objectName)) {
                server.unregisterMBean(objectName);
            }
        } catch (MalformedObjectNameException ex) {
            Logger.getLogger(WebbAppListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstanceNotFoundException ex) {
            Logger.getLogger(WebbAppListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MBeanRegistrationException ex) {
            Logger.getLogger(WebbAppListener.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
