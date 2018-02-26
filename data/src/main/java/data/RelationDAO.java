package data;

public interface RelationDAO {
    public void addRelation(Relation relation);

    public void updateRelation(Relation relation);

    public void deleteRelation(Relation relation);

    public Relation getRelationByUserAndChat(User user, Chatroom chat);

    public boolean hasRelation(Integer user_id, Integer chat_id);
}
