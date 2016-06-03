/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Servlet Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloWorld
*/

package com.example.Stefan.myapplication.backend;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
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
            String user = req.getParameter(Constants.UserNameKey);
            if (user == null || user.length() == 0){
                throw new IllegalArgumentException("Invalid user \""+ user+"\"");
            }

            String poll = req.getParameter(Constants.Poll);
            if (poll == null || poll.length() == 0){
                throw new IllegalArgumentException("Invalid poll Name \""+ poll+"\"");
            }

            // we could have multiple groups, each group locked separately,
            // but let's just have one for this project; create if not exists
            PollGroup group = PollGroup.create(Constants.AllPollsGroup, pm);

            Poll newPoll = new Poll();
            newPoll.setEntityGroup(group.getKey());
            newPoll.updateDate();
            newPoll.addUser(user);
            newPoll.setSerialPoll(poll);

            pm.makePersistent(newPoll);
            out.write(formatAsJson(newPoll));
            //out.write(newPoll.getID());
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
            pm.close();
        }
    }

    private void handleGetPolls(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        try {
            String user = req.getParameter(Constants.UserNameKey);
            if (user == null || user.length() == 0){
                throw new IllegalArgumentException("Invalid user \""+ user+"\"");
            }

            Query q = pm.newQuery("SELECT FROM Poll WHERE activeUsers == activeUsersParm parameters String activeUsersParm");
            List<Poll> results = (List<Poll>) q.execute(user);
            results.size();

            out.write(formatAsJson(results));
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
            pm.close();
        }
    }

    private void handleGetPoll(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        try {
            String pollID = req.getParameter(Constants.PollID);
            if (pollID == null || pollID.length() == 0){
                throw new IllegalArgumentException("Invalid key \""+ pollID+"\"");
            }

            Poll poll = pm.getObjectById(Poll.class, pollID);
            out.write(formatAsJson(poll));
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
        obj.put(Constants.PollID, poll.getID().toString());
        obj.put(Constants.PollGroup, poll.getEntityGroup().toString());
        obj.put(Constants.PollModDate, poll.getModifiedDate().toString());
        obj.put(Constants.ActiveUsers, poll.getActiveUsers().toString());
        obj.put(Constants.Poll, poll.getSerialPoll());

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String rv = gson.toJson(obj);
        return rv;
    }
    public static String formatAsJson(List<Poll> results) {
        HashMap<String, HashMap<String, String>> obj = new HashMap<String, HashMap<String, String>>();
        for (Poll poll : results) {
            HashMap<String, String> innerObj = new HashMap<String, String>();
            innerObj.put(Constants.PollID, poll.getID().toString());
            innerObj.put(Constants.PollGroup, poll.getEntityGroup().toString());
            innerObj.put(Constants.PollModDate, poll.getModifiedDate().toString());
            innerObj.put(Constants.ActiveUsers, poll.getActiveUsers().toString());
            innerObj.put(Constants.Poll, poll.getSerialPoll());
            obj.put(poll.getID().toString(), innerObj);
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        String rv = gson.toJson(obj);
        return rv;
    }
}
