package data;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDAOImp implements UserDAO {
    public UserDAOImp() {
    }

    public void addUser(User user) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateUser(User user) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Collection<User>  getUsersAll() {
        Session session = null;
        List users = new ArrayList();
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from User");
            users = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
    }

    public User getUserByID(int id) {
        Session session = null;
        User user = new User();
        try {
            session = Main.getSession();
            session.beginTransaction();
            user = session.get(User.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    public void deleteUserByID(int id) {
        Session session = null;
        User user;
        try {
            session = Main.getSession();
            session.beginTransaction();
            user = session.get(User.class, id);
            if (!(user.equals(null)))
                session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public User getUserByEmail(String email) {
        Session session = null;
        List<User> users = new ArrayList<User>();
        User user = new User();
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from User where email = :paramName");
            query.setParameter("paramName", email);
            users = query.list();
            user = users.get(0);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    public void deleteUser(User user) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Collection<User> getUsersByChatroom(Chatroom chatroom) {
        List<User> users = new ArrayList<>();
        List<Relation> relations = new ArrayList<>();
        int chat_id = chatroom.getChatroomID();
        int user_id;
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Relation where chatID = :paramName");
            query.setParameter("paramName", chat_id);
            relations = query.list();
            for (int i = 0; i < relations.size(); i++) {
                user_id = relations.get(i).getUserID();
                query = session.createQuery("from User where userID = :paramName");
                query.setParameter("paramName", user_id);
                users.add((User) query.list().get(0));
            }
            session.getTransaction().commit();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
