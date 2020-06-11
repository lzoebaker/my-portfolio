package com.google.sps.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.sps.data.UserDatabase;
import com.google.sps.data.CommentDatabase;


@WebListener
public class Application implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
      ServletContext context = servletContextEvent.getServletContext();
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      UserService userService = UserServiceFactory.getUserService(); 
      CommentDatabase commentDatabase = new CommentDatabase(datastore);
      UserDatabase userDatabase = new UserDatabase(datastore);
      context.addServlet("commentsServlet", new CommentsServlet(datastore, userService, commentDatabase, userDatabase))
          .addMapping("/comments");
      context.addServlet("deleteCommentServlet", new DeleteCommentServlet(datastore))
          .addMapping("/delete-comment");
      context.addServlet("displayNameServlet", new DisplayNameServlet(datastore, userService, userDatabase))
          .addMapping("/display-name");
      context.addServlet("authenticationServlet", new AuthenticationServlet(datastore, userService, userDatabase))
          .addMapping("/login");
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
};