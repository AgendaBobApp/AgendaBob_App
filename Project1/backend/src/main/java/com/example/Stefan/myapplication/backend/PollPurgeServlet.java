package com.example.Stefan.myapplication.backend;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Stefan on 6/5/2016.
 */
public class PollPurgeServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        doOp(false, req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doOp(true, req, resp);
    }
    private void doOp(boolean ispost, HttpServletRequest reqx,
                      HttpServletResponse resp) {

        PersistenceManager pm = PMF.getPMF().getPersistenceManager();
        try {
            PurgeEmptyPolls(pm);
        } finally {
            pm.close();
        }
    }
    private void PurgeEmptyPolls(PersistenceManager pm) {
        System.out.println("PURGING EMPTY POLLS !!!!!!!");
        Transaction trans = pm.currentTransaction();
//        Query q = pm.newQuery(Poll.class, "activeUsers.size() < :amount");
        Query q = pm.newQuery(Poll.class);
        q.setOrdering("id");
        try {
            trans.begin();
            System.out.println("HERE 1 !!!!!!!");
            List<Poll> results = (List<Poll>) q.execute();
//            List<Poll> results = (List<Poll>) q.execute(1);
            System.out.println("HERE 2 !!!!!!!");
            results.size();
            System.out.println("Polls: "+results.toString());
            if (!results.isEmpty()) {
                for(Poll p : results){
                    if (p.getActiveUsers().size() < 1) {
                        System.out.println("No Active Uses for Poll: "+p.getID().toString());
                        System.out.println("DELETING: " +p.getID().toString());
                        pm.deletePersistent(p);
                    }
                }
            } else {
                System.err.println("RESULTS WAS NULL");
            }
            trans.commit();
        } catch (IllegalArgumentException iae) {
//            out.write(formatAsJson(iae));
            System.err.println("IllegalArgumentException");
            if (trans.isActive()){trans.rollback();}
        } catch (Exception e) {
            if (trans.isActive()){trans.rollback();}
            e.printStackTrace();
        }finally {
            q.closeAll();
        }
    }
}
