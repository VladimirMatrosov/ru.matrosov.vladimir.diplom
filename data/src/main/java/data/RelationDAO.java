package data;

import java.util.Collection;

public interface RelationDAO {
    public void addRelation(Relation relation);

    public void updateRelation(Relation relation);

    public void deleteRelation(Relation relation);

    public Relation getRelationByUserAndChat(User user, Chatroom chat);

    public boolean hasRelation(int user_id, int chat_id);

    public Collection getRelationsByUser(User user);

}
