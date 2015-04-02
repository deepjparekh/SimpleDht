package edu.buffalo.cse.cse486586.simpledht;

import java.util.HashMap;

/**
 * Created by deep on 4/1/15.
 */
public class Message {
    private boolean flagIsInsert;
    private boolean flagIsDelete;
    private boolean flagIsJoin;
    private boolean flagIsSuccessorPredecessor;
    private String[] SuccessorPredecessor;
    private Query query;
    private String portNumber;

    public boolean isJoined() {
        return isJoined;
    }

    public void setJoined(boolean isJoined) {
        this.isJoined = isJoined;
    }

    private boolean isJoined;
    public boolean isFlagIsSuccessorPredecessor() {
        return flagIsSuccessorPredecessor;
    }

    public void setFlagIsSuccessorPredecessor(boolean flagIsSuccessorPredecessor) {
        this.flagIsSuccessorPredecessor = flagIsSuccessorPredecessor;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String[] getSuccessorPredecessor() {

        return SuccessorPredecessor;
    }

    public void setSuccessorPredecessor(String[] successorPredecessor) {
        SuccessorPredecessor = successorPredecessor;
    }

    public boolean isFlagIsInsert() {
        return flagIsInsert;
    }

    public void setFlagIsInsert(boolean flagIsInsert) {
        this.flagIsInsert = flagIsInsert;
    }

    public boolean isFlagIsDelete() {
        return flagIsDelete;
    }

    public void setFlagIsDelete(boolean flagIsDelete) {
        this.flagIsDelete = flagIsDelete;
    }

    public boolean isFlagIsJoin() {
        return flagIsJoin;
    }

    public void setFlagIsJoin(boolean flagIsJoin) {
        this.flagIsJoin = flagIsJoin;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}
