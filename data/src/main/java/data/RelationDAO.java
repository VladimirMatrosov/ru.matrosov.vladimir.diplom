package data;

public interface RelationDAO {
    public void addRelation(Relation relation);

    public void updateRelation(Relation relation);

    public void deleteRelation(Relation relation);

    public Relation getRelationByUserAndChat(User user, Chatroom chat);
}
