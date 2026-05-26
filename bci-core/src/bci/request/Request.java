package bci.request;

import bci.work.Work;

public class Request {
    private Work _work;
    private int _dueDate;

    public Request(Work work, int date) {
        _work = work;
        _dueDate = date;
    }

    public Work getWork() { return _work; }
    public int getDueDate() { return _dueDate; }
}
