package ru.job.cinema.model;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {

    private int id;
    private Session session_id;
    private int pos_row;
    private int cell;

    public Ticket() {
    }

    public Ticket(int id, Session session_id, int pos_row, int cell) {
        this.id = id;
        this.session_id = session_id;
        this.pos_row = pos_row;
        this.cell = cell;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Session getSession_id() {
        return session_id;
    }

    public void setSession_id(Session session_id) {
        this.session_id = session_id;
    }

    public int getPos_row() {
        return pos_row;
    }

    public void setPos_row(int pos_row) {
        this.pos_row = pos_row;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
