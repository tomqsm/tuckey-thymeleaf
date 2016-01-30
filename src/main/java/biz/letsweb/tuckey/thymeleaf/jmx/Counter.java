package biz.letsweb.tuckey.thymeleaf.jmx;

import org.javasimon.SimonManager;
import org.javasimon.Split;
import org.javasimon.Stopwatch;

/**
 *
 * @author toks
 */
public class Counter implements CounterMBean {

    private String name;
    private final Stopwatch stopwatch;// = SimonManager.getStopwatch(name);
    Split split = Split.start();

    public Counter() {
        this.name = "InitialName";
        stopwatch = SimonManager.getStopwatch(name);
        stopwatch.start();
      
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getCurrentTimeMilis() {
        return split.toString();
    }
}
