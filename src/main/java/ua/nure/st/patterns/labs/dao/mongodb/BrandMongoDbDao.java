package ua.nure.st.patterns.labs.dao.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.BrandDao;
import ua.nure.st.patterns.labs.entity.Brand;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BrandMongoDbDao implements BrandDao<ObjectId> {
    
    private static final String COLLECTION_NAME = "brands";

    private final String dbName;

    public BrandMongoDbDao(String dbName) {
        this.dbName = dbName;
    }

    private Brand<ObjectId> mapDocumentToBrand(Document document) {
        return new Brand<>(
                document.getObjectId("_id"),
                document.getString("name")
        );
    }

    @Override
    public List<Brand<ObjectId>> getAll() {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.find().into(new ArrayList<>());

            return documents.stream()
                    .map(this::mapDocumentToBrand)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Brand<ObjectId> getById(ObjectId id) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = collection.find(new Document("_id", id)).first();

            return mapDocumentToBrand(document);
        }
    }

    @Override
    public boolean save(String name) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("name", name);

            InsertOneResult result = collection.insertOne(document);

            return result.wasAcknowledged();
        }
    }

    @Override
    public boolean update(Brand<ObjectId> brand) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("name", brand.getName());

            InsertOneResult result = collection.insertOne(document);

            return result.wasAcknowledged();
        }
    }

    @Override
    public boolean delete(ObjectId id) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document("_id", id);

            InsertOneResult result = collection.insertOne(document);

            return result.wasAcknowledged();
        }
    }
}
