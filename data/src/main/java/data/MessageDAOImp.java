package data;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MessageDAOImp implements MessageDAO {
    public MessageDAOImp() {
    }

    public void addMessage(Message message) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.save(message);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateMessage(Message message) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.update(message);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void deleteMessage(Message message) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.delete(message);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Collection getMessagesByUser(User user) {
        Session session = null;
        List<Message> messages = new ArrayList<Message>();
        int user_id = user.getUserID();
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Message where userID = :paramName");
            query.setParameter("paramName", user_id);
            messages = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return messages;
    }

    public Collection getMessagesByChat(Chatroom chatroom) {
        Session session = null;
        List<Message> messages = new ArrayList<Message>();
        int chat_id = chatroom.getChatroomID();
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Message where chatID = :paramName");
            query.setParameter("paramName", chat_id);
            messages = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && session.isOpen())
                session.close();
        }
        return messages;
    }

    public void deleteMessagesByChat(Chatroom chatroom) {
        Session session = null;
        List<Message> messages;
        int chat_id = chatroom.getChatroomID();
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Message where chatID = :paramName");
            query.setParameter("paramName", chat_id);
            messages = query.list();
            for (int i = 0; i < messages.size(); i++) {
                session.delete(messages.get(i));
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public Message getMessageByID(int id) {
        Session session = null;
        Message message = new Message();
        try {
            session = Main.getSession();
            session.beginTransaction();
            message = session.get(Message.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return message;
    }

    public Collection getMessagesByDate(Date date) {
        Session session = null;
        List<Message> messages = new ArrayList<>();
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Message where date = :paramName");
            query.setParameter("paramName", date);
            messages = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return messages;
    }
}
