package ru.netology.javaqa;

import java.util.Comparator;

public class TicketTimeComparator implements Comparator<Ticket> {

    @Override
    public int compare(Ticket t1, Ticket t2) {
        int time1 = t1.getTimeTo() - t1.getTimeFrom();
        int time2 = t2.getTimeTo() - t2.getTimeFrom();
        return Integer.compare(time1, time2);
    }
}