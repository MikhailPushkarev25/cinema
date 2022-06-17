package ru.job.cinema.persistence;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job.cinema.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDBStore {

    private final BasicDataSource pool;
    private static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());

    public UserDBStore(BasicDataSource pool) {
        this.pool = pool;
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM users")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    users.add(
                            new User(
                                    it.getInt("id"),
                                    it.getString("username"),
                                    it.getString("email"),
                                    it.getString("phone"),
                                    it.getString("password")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in logger method ", e);
        }
        return users;
    }

    public Optional<User> add(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(username, email, phone, password) VALUES(?, ?, ?, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.execute();
            try (ResultSet it = ps.getGeneratedKeys()) {
                if (it.next()) {
                    user.setId(it.getInt(1));
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in logger method add", e);
        }
        return Optional.of(user);
    }

    public User findById(int id) {
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                            new User(
                                    it.getInt("id"),
                                    it.getString("username"),
                                    it.getString("email"),
                                    it.getString("phone"),
                                    it.getString("password")
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in logger method ", e);
        }
        return null;
    }

    public Optional<User> findByEmailAndPhone(String email, String phone) {
        Optional<User> optUser = Optional.empty();
        try (Connection cn = pool.getConnection();
        PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE email = ? AND phone = ?")) {
            ps.setString(1, email);
            ps.setString(2, phone);
            try (ResultSet it = ps.executeQuery()) {
                if (it.next()) {
                    optUser = Optional.of(
                            new User(
                                    it.getInt("id"),
                                    it.getString("username"),
                                    it.getString("email"),
                                    it.getString("phone"),
                                    it.getString("password")
                            )
                    );
                }
            }
        } catch (Exception e) {
            LOG.error("Exception in logger method findByEmailAndPhone ", e);
        }
        return optUser;
    }
}
