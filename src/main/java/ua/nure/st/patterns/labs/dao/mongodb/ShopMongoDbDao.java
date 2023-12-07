package ua.nure.st.patterns.labs.dao.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.ShopDao;
import ua.nure.st.patterns.labs.entity.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShopMongoDbDao implements ShopDao<ObjectId> {

    private static final String COLLECTION_NAME = "shops";

    private final String databaseName;

    public ShopMongoDbDao(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public List<Shop<ObjectId>> getAll() {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(databaseName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.find().into(new ArrayList<>());

            return documents.stream()
                    .map(this::mapDocumentToShop)
                    .collect(Collectors.toList());
        }
    }

    private Shop<ObjectId> mapDocumentToShop(Document document) {
        return new Shop<>(
                document.getObjectId("_id"),
                document.getString("name"),
                document.getString("location")
        );
    }

    @Override
    public List<Shop<ObjectId>> getAllByName(String name) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(databaseName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.find(new Document("name", name)).into(new ArrayList<>());

            return documents.stream()
                    .map(this::mapDocumentToShop)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Shop<ObjectId> getById(ObjectId id) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(databaseName)
                    .getCollection(COLLECTION_NAME);

            Document document = collection.find(new Document("_id", id)).first();

            if (document == null) {
                return null;
            }
            return mapDocumentToShop(document);
        }
    }

    @Override
    public boolean save(String name, String location) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(databaseName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("name", name)
                    .append("location", location);

            InsertOneResult res = collection.insertOne(document);
            return res.wasAcknowledged();
        }
    }

    @Override
    public boolean update(Shop<ObjectId> mySqlShop) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(databaseName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("_id", mySqlShop.getId())
                    .append("name", mySqlShop.getName())
                    .append("location", mySqlShop.getLocation());

            InsertOneResult res = collection.insertOne(document);
            return res.wasAcknowledged();
        }
    }

    @Override
    public boolean delete(ObjectId id) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(databaseName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("_id", id);

            InsertOneResult res = collection.insertOne(document);
            return res.wasAcknowledged();
        }
    }
}
