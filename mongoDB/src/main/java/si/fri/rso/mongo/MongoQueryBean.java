package si.fri.rso.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import si.fri.rso.lib.MessageObject;
import si.fri.rso.lib.MongoMessageObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class MongoQueryBean {
    @Inject
    MongoConnection mongoConnection;

    public List<MongoMessageObject> getAllMessages() {

        MongoDatabase database = mongoConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("messages");
        System.out.println("Collection <messages> selected successfully");

        FindIterable<Document> iterDoc = collection.find();
        List<MongoMessageObject> mongoMessageObjectList = new ArrayList<MongoMessageObject>();

        for (Document doc : iterDoc) {
            MongoMessageObject messageObject = new MongoMessageObject(doc.get("_id").toString(),
                    doc.get("message").toString(),
                    doc.get("userName").toString(),
                    Integer.valueOf(doc.get("channelId").toString()));
            mongoMessageObjectList.add(messageObject);
        }
        return mongoMessageObjectList;
    }

    public List<MongoMessageObject> getMessagesOnChannel(Integer channelId) {
        System.out.println("CHANNEL ID QUERY: " + channelId);

        List<MongoMessageObject> mongoMessageObjectList = new ArrayList<MongoMessageObject>();

        MongoDatabase database = mongoConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("messages");

        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("channelId", channelId);
        FindIterable<Document> iterDoc = collection.find(whereQuery);

        for (Document doc : iterDoc) {
            MongoMessageObject messageObject = new MongoMessageObject(doc.get("_id").toString(),
                    doc.get("message").toString(),
                    doc.get("userName").toString(),
                    Integer.valueOf(doc.get("channelId").toString()));
            mongoMessageObjectList.add(messageObject);
        }

        return mongoMessageObjectList;
    }

    public boolean insertNewMessage(MessageObject messageObject) {
        MongoDatabase database = mongoConnection.getDatabase();
        MongoCollection<Document> collection = database.getCollection("messages");
        try {
            Document doc = new Document();
            doc.put("message", messageObject.getMessage());
            doc.put("userName", messageObject.getUserName());
            doc.put("channelId", messageObject.getChannelId());
            collection.insertOne(doc);
            return true;
        } catch (MongoException | ClassCastException e) {
            e.printStackTrace();
            return false;
        }
    }

}
