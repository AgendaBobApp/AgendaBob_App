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
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
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
                case Constants.GetPollByKey:
                    handleGetPoll(req, out, pm);
                    break;
                case Constants.CreatePollKey:
                    handleCreatePoll(req, out, pm);
                    break;
                case Constants.GetPollByUserKey:
                    handleGetPolls(req, out, pm);
                    break;
                case Constants.addUserKey:
                    handleAddUserFromPoll(req, out, pm);
                    break;
                case Constants.removeUserKey:
                    handleRemoveUserFromPoll(req, out, pm);
                    break;
                case Constants.UpdatePollByKey:
                    UpdatePoll(req, out, pm);
                    break;
//                case Constants.PurgePollsKey:
//                    PurgeEmptyPolls(req, out, pm);
//                    break;
                case Constants.GetAllPollsKey:
                    DEBUGHandleGetAllPoll(req, out, pm);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid op parameter");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                if (pm.currentTransaction().isActive()){pm.currentTransaction().rollback();}
            } catch (Exception ignoreThis) {
            }
            out.write("{\"error\":" + new Gson().toJson(ex.getMessage()) + "}");
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

            Poll newPoll = new Poll();

            PollGroup group = PollGroup.create(Constants.AllPollsGroup, pm);
            newPoll.setEntityGroup(group.getKey());

            newPoll.updateDate();
            newPoll.addUser(user);
            newPoll.setSerialPoll(poll);

            pm.makePersistent(newPoll);
            out.write(newPoll.getID().toString());//formatAsJson()
            //out.write(newPoll.getID());
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
            pm.close();
        }
    }
    private void handleGetPoll(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        String pollID = req.getParameter(Constants.PollID);
        System.out.println(pollID);

        if (pollID == null || pollID.length() == 0){
            throw new IllegalArgumentException("Invalid Poll ID \""+ pollID+"\"");
        }
        Long id = new Long(pollID);
        System.out.println(id.toString());
        try {
            Key entityGoup = PollGroup.create(Constants.AllPollsGroup, pm).getKey();
            Key key = new KeyFactory.Builder(
                    entityGoup).addChild(Poll.class.getSimpleName(), id).getKey();
            Poll p = pm.getObjectById(Poll.class, key);
            if (p != null) {;
                out.write(p.getSerialPoll());
                System.out.println(p.getSerialPoll());
            } else {
                System.err.println("RESULTS WAS NULL");
                //out.write("NULL");
            }
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
        }
    }
    private void handleGetPolls(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        try {
            String user = req.getParameter(Constants.UserNameKey);
            System.out.println("USER: "+user);
            if (user == null || user.length() == 0){
                throw new IllegalArgumentException("Invalid user \""+ user+"\"");
            }
            Query q = pm.newQuery(Poll.class, "activeUsers.contains(:user)");
            List<Poll> results = (List<Poll>) q.execute(user);
            results.size();
            if (results != null)
                out.write(formatAsJson(results));
            else{
                results = new ArrayList<Poll>();
                System.out.println("RESULTS WAS NULL");
                out.write(formatAsJson(results));
            }
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
            pm.close();
        }
    }
    private void UpdatePoll(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        Transaction trans = pm.currentTransaction();
        try {
            trans.begin();
            String user = req.getParameter(Constants.UserNameKey);
            System.out.println("USER: "+user);
            if (user == null || user.length() == 0){
                throw new IllegalArgumentException("Invalid user \""+ user+"\"");
            }
            String poll = req.getParameter(Constants.Poll);
            System.out.println("USER: "+user);
            if (poll == null || poll.length() == 0){
                throw new IllegalArgumentException("Invalid poll \""+ poll+"\"");
            }
            String pollID = req.getParameter(Constants.PollID);
            System.out.println(pollID);

            if (pollID == null || pollID.length() == 0){
                throw new IllegalArgumentException("Invalid Poll ID \""+ pollID+"\"");
            }
            Long id = new Long(pollID);
            System.out.println(id.toString());

            Key entityGoup = PollGroup.create(Constants.AllPollsGroup, pm).getKey();
            Key key = new KeyFactory.Builder(
                    entityGoup).addChild(Poll.class.getSimpleName(), id).getKey();
            Poll p = pm.getObjectById(Poll.class, key);
            if (p != null) {
                p.updateDate();
                p.updateSerializedPoll(poll);
                pm.makePersistent(p);
//                out.write(p.getSerialPoll());
                System.out.println("UPDATE SUCCESS: " +user+ "" + p.getSerialPoll());
            } else {
                System.err.println("RESULTS WAS NULL");
                //out.write("NULL");
            }
            trans.commit();
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
            if (trans.isActive()){trans.rollback();}
        } catch (Exception e) {
            if (trans.isActive()){trans.rollback();}
        }finally {
            pm.close();
        }
    }
    private void handleAddUserFromPoll(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        Transaction trans = pm.currentTransaction();
        try {
            trans.begin();

            String user = req.getParameter(Constants.UserNameKey);
            System.out.println("USER: "+user);
            if (user == null || user.length() == 0){
                throw new IllegalArgumentException("Invalid user \""+ user+"\"");
            }
            String pollID = req.getParameter(Constants.PollID);
            System.out.println(pollID);

            if (pollID == null || pollID.length() == 0){
                throw new IllegalArgumentException("Invalid Poll ID \""+ pollID+"\"");
            }
            Long id = new Long(pollID);
            System.out.println(id.toString());

            Key entityGoup = PollGroup.create(Constants.AllPollsGroup, pm).getKey();
            Key key = new KeyFactory.Builder(
                    entityGoup).addChild(Poll.class.getSimpleName(), id).getKey();
            Poll p = pm.getObjectById(Poll.class, key);
            if (p != null) {
                p.addUser(user);
                pm.makePersistent(p);
//                out.write(p.getSerialPoll());
                System.out.println("ADD SUCCESS: " +user+ "" + p.getID().toString());
            } else {
                System.err.println("RESULTS WAS NULL");
                //out.write("NULL");
            }
            trans.commit();
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
            if (trans.isActive()){trans.rollback();}
        } catch (Exception e) {
            if (trans.isActive()){trans.rollback();}
        }finally {
            pm.close();
        }
    }
    private void handleRemoveUserFromPoll(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        Transaction trans = pm.currentTransaction();
        try {
            trans.begin();

            String user = req.getParameter(Constants.UserNameKey);
            System.out.println("USER: "+user);
            if (user == null || user.length() == 0){
                throw new IllegalArgumentException("Invalid user \""+ user+"\"");
            }
            String pollID = req.getParameter(Constants.PollID);
            System.out.println(pollID);

            if (pollID == null || pollID.length() == 0){
                throw new IllegalArgumentException("Invalid Poll ID \""+ pollID+"\"");
            }
            Long id = new Long(pollID);
            System.out.println(id.toString());

            Key entityGoup = PollGroup.create(Constants.AllPollsGroup, pm).getKey();
            Key key = new KeyFactory.Builder(
                    entityGoup).addChild(Poll.class.getSimpleName(), id).getKey();
            Poll p = pm.getObjectById(Poll.class, key);
            if (p != null) {
                p.removeUser(user);
                pm.makePersistent(p);
//                out.write(p.getSerialPoll());
                System.out.println("REMOVE SUCCESS: " +user+ "" + p.getID().toString());
            } else {
                System.err.println("RESULTS WAS NULL");
                //out.write("NULL");
            }
            trans.commit();
        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
            if (trans.isActive()){trans.rollback();}
        } catch (Exception e) {
            if (trans.isActive()){trans.rollback();}
        }finally {
            pm.close();
        }
    }

    private void DEBUGHandleGetAllPoll(HttpServletRequest req, PrintWriter out, PersistenceManager pm) {
        Query q = pm.newQuery(Poll.class);
        q.setOrdering("id");

        try {
            List<Poll> results = (List<Poll>) q.execute();
            System.out.println(results.toString());
            results.size();
            if (!results.isEmpty()) {;
                out.write(formatAsJson(results));
                System.out.println(formatAsJson(results));
            } else {
                System.err.println("RESULTS WAS NULL");
                out.write("NULL");
            }

        } catch (IllegalArgumentException iae) {
            out.write(formatAsJson(iae));
        } finally {
            q.closeAll();
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
    public static String formatAsJson(List<Poll> polls){
        List<String> serializedpolls = new ArrayList<String>();
        for(Poll poll : polls){
//            String jsonPoll = formatAsJson();
            System.out.println(poll.getSerialPoll());
            serializedpolls.add(poll.getSerialPoll());
        }
        Type listOfStrings = new TypeToken<ArrayList<String>>(){}.getType();
        Gson gson = new Gson();
        String rv = gson.toJson(serializedpolls, listOfStrings);
        System.out.println(rv);
        return rv;
    }
}
