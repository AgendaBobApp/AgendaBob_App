/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.Stefan.myapplication.backend;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

public class MyServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().println("Please use the form to POST to this url");
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String op = req.getParameter("op");
        if (op == null)
            op = "list";
        PersistenceManager pm = PMF.getPMF().getPersistenceManager();
        try {
            switch (op) {
                case Constants.CreateUserKey:
                    handleCreateUser(req, out, pm);
                    break;
                case Constants.CreatePollKey:
                    handleCreatePoll(req, out, pm);
                    break;
                case Constants.PurgeUsersKey:
                    break;
                case Constants.PurgePollsKey:
                    break;
                default:
                    throw new IllegalArgumentException("Invalid op parameter");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                pm.currentTransaction().rollback();
            } catch (Exception ignoreThis) {
            }
            out.write("{\"error\":" + new Gson().toJson(ex.getMessage()) + "}");
        } finally {
            pm.close();
        }
    }

    private void handleCreateUser(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        try {
            String name = req.getParameter(Constants.UserNameKey);
            if (name == null || name.length() == 0){
                throw new IllegalArgumentException("Invalid user Name \""+ name+"\"");
            }
            if (name == null)
                name = "UNNAMED";
            User user = new User();

            // we could have multiple groups, each group locked separately,
            // but let's just have one for this demo; create if not exists
            UserGroup group = UserGroup.create(Constants.AllUsersGroup, pm);
            user.setEntityGroup(group.getKey());
            user.setName(name);
            pm.makePersistent(user);
            out.write(formatAsJson(user));
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
            pm.close();
        }
    }
    private void handleCreatePoll(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        try {
            String poll = req.getParameter(Constants.PollNameKey);
            String desc = req.getParameter(Constants.PollDescKey);
            String creator = req.getParameter(Constants.PollCreatorKey);
            //String timeset = req.getParameter(Constants.PollNameKey);
            if (poll == null || poll.length() == 0){
                throw new IllegalArgumentException("Invalid poll Name \""+ poll+"\"");
            }
            if (desc == null)
                desc = "No Description.";
            if (creator == null)
                creator = "God.";
            Poll newPoll = new Poll();

            // we could have multiple groups, each group locked separately,
            // but let's just have one for this demo; create if not exists
            PollGroup group = PollGroup.create(Constants.AllPollsGroup, pm);
            newPoll.setEntityGroup(group.getKey());
            newPoll.setName(poll);
            newPoll.setDescription(desc);
            newPoll.setCreator(creator);
            pm.makePersistent(newPoll);
            out.write(formatAsJson(newPoll));
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
            pm.close();
        }
    }

    public static String formatAsJson(Exception e) {
        HashMap<String, String> obj = new HashMap<String, String>();
        obj.put("errormsg", e.getMessage());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String rv = gson.toJson(obj);
        return rv;
    }
    public static String formatAsJson(User user) {
        HashMap<String, String> obj = new HashMap<String, String>();
//        obj.put("id", Long.toString(user.getID()));
        obj.put("name", user.getName());
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String rv = gson.toJson(obj);
        return rv;
    }
    public static String formatAsJson(Poll poll) {
        HashMap<String, String> obj = new HashMap<String, String>();
        obj.put("name", poll.getName());
        obj.put("creator", poll.getCreator());
        obj.put("description", poll.getDescription());
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String rv = gson.toJson(obj);
        return rv;
    }

//    public static String formatAsJson(Course course) {
//        HashMap<String, String> obj = new HashMap<String, String>();
//        obj.put("id", Long.toString(course.getId()));
//        obj.put("title", course.getTitle());
//        obj.put("description", course.getDescription());
//        obj.put("modified", Long.toString(course.getLastModified() != null ? course.getLastModified().getTime() : 0L));
//
//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        String rv = gson.toJson(obj);
//        return rv;
//    }
}
