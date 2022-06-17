package ru.job.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job.cinema.model.Session;
import ru.job.cinema.model.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(TicketDBStore.class.getName());

    public TicketDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM ticket")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    tickets.add(new Ticket(
                            it.getInt("id"),
                            new Session(it.getInt("session_id")),
                            it.getInt("pos_row"),
                            it.getInt("cell")
                    ));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in logger method findAll ", e);
        }
        return tickets;
    }

    public Ticket findById(int id) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM ticket WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    return new Ticket(
                            it.getInt("id"),
                            new Session(it.getInt("session_id")),
                            it.getInt("pos_row"),
                            it.getInt("cell")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }

    public Ticket add(Ticket ticket) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO ticket(session_id, pos_row, cell) VALUES (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ticket.getSession_id().getId());
            ps.setInt(2, ticket.getPos_row());
            ps.setInt(3, ticket.getCell());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    ticket.setId(it.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in logger method add", e);
        }
        return ticket;
    }

    public void update(Ticket ticket) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement(
                "UPDATE ticket SET session_id = ?, pos_row = ?, cell = ? WHERE id = ?")) {
            ps.setInt(1, ticket.getSession_id().getId());
            ps.setInt(2, ticket.getPos_row());
            ps.setInt(3, ticket.getCell());
            ps.setInt(4, ticket.getId());
            ps.executeUpdate();
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    ticket.setId(it.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }

    public Ticket delete(Ticket ticket) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("DELETE FROM ticket WHERE id = ?")) {
            ps.setInt(1, ticket.getId());
            ps.execute();
        } catch (Exception e) {
            LOG.error("Exception logger in method delete ", e);
        }
        return ticket;
    }
}
