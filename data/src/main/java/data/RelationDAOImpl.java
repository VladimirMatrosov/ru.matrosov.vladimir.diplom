package data;

import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class RelationDAOImpl implements RelationDAO {
    public void addRelation(Relation relation) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.save(relation);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void updateRelation(Relation relation) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.update(relation);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }


    public void deleteRelation(Relation relation) {
        Session session = null;
        try {
            session = Main.getSession();
            session.beginTransaction();
            session.delete(relation);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public Relation getRelationByUserAndChat(User user, Chatroom chat) {
        Session session = null;
        Relation relation = new Relation();
        List<Relation> relations = new ArrayList<>();
        try{
            session = Main.getSession();
            session.beginTransaction();
            Query query = session.createQuery("from Relation where userID = :paramUser, chatID = :paramChat");
            query.setParameter("paramUser", user.getUserID());
            query.setParameter("paramChat", chat.getChatroomID());
            relations = query.list();
            relation = relations.get(0);
            session.getTransaction().commit();

        }catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return relation;
    }

    @Override
    public boolean hasRelation(Integer user_id, Integer chat_id) {
        Session session = null;
        boolean bool = false;
        try{
            session = Main.getSession();
            session.beginTransaction();
            UserDAO userDAO = new UserDAOImp();
            User user = userDAO.getUserByID(user_id);
            ChatroomDAO chatroomDAO = new ChatroomDAOImpl();
            Chatroom chatroom = chatroomDAO.getChatroomByID(chat_id);
            RelationDAO relationDAO = new RelationDAOImpl();
            Relation relation = relationDAO.getRelationByUserAndChat(user, chatroom);
            if (relation != null)
                bool = true;
            session.getTransaction().commit();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return bool;
    }
}
