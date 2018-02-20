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
}
