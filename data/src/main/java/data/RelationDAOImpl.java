package data;

import org.hibernate.Session;

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
}
