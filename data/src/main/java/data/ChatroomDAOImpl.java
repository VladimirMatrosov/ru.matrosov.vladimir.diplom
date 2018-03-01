package data;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChatroomDAOImpl implements ChatroomDAO {
    public void addChatroom(Chatroom chatroom) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.save(chatroom);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && (session.isOpen()))
                session.close();
        }
    }

    public void updateChatroom(Chatroom chatroom) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.update(chatroom);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && (session.isOpen()))
                session.close();
        }
    }

    public void deleteChatroom(Chatroom chatroom) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.delete(chatroom);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && (session.isOpen()))
                session.close();
        }
    }

    public Collection getChatroomAll() {
        List<Chatroom> chatrooms = new ArrayList<>();
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Chatroom ");
            chatrooms = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && (session.isOpen()))
                session.close();
        }
        return chatrooms;
    }

    public Collection getChatroomsByUser(User user) {
        List<Chatroom> chatrooms = new ArrayList<>();
        Session session = null;
        int user_id = user.getUserID();
        List<Relation> relations = new ArrayList<>();
        int chat_id;
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Relation where userID = :paramName");
            query.setParameter("paramName", user_id);
            relations = query.list();
            for (int i = 0; i < relations.size(); i++) {
                chat_id = relations.get(i).getChatroomID();
                query = session.createQuery("from Chatroom where chatroomID = :paramName");
                query.setParameter("paramName", chat_id);
                chatrooms.addAll(query.list());
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && session.isOpen())
                session.close();
        }
        return chatrooms;
    }

    public Chatroom getChatroomByID(Integer id) {
        Session session = null;
        Chatroom chatroom = new Chatroom();
        try {
            session = Main.getSession();
            session.beginTransaction();
            chatroom = session.get(Chatroom.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && session.isOpen())
                session.close();
        }
        return chatroom;
    }

    public void deleteChatroomByID(Integer id) {
        Session session = null;
        Chatroom chatroom;
        try {
            session = Main.getSession();
            session.beginTransaction();
            chatroom = session.get(Chatroom.class, id);
            session.delete(chatroom);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && session.isOpen())
                session.close();
        }
    }

    public Chatroom getChatroomByName(String name) {
        Session session = null;
        Chatroom chatroom = new Chatroom();
        try {
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Chatroom where name = :paramName");
            query.setParameter("paramName", name);
            chatroom = (Chatroom) query.list().get(0);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if ((session != null) && session.isOpen())
                session.close();
        }
        return chatroom;
    }

    @Override
    public boolean isNull(Chatroom chatroom) {
        boolean bool = false;
        Session session = null;
        try{
            session = Main.getSession();
            session.beginTransaction();
            if (chatroom == null)
                bool = true;
            session.getTransaction().commit();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return bool;
    }
}
