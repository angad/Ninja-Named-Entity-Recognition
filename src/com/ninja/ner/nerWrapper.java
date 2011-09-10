package com.ninja.ner;

import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;

import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.*;


public class nerWrapper extends HttpServlet{
    
    
    String serializedClassifier = "classifiers/all.3class.distsim.crf.ser.gz";

    AbstractSequenceClassifier classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);

    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().print("Hello from Java!\n");
        String s1 = "Good afternoon Rajat Raina, how are you today?";
        String s2 = "I go to school at Stanford University, which is located in California.";
        resp.getWriter().print(classifier.classifyWithInlineXML(s1) + " " + classifier.classifyWithInlineXML(s2));        
    }

    
    public static void main(String[] args) throws IOException {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new HelloWorld()),"/*");
        server.start();
        server.join();   
        
    }
}
