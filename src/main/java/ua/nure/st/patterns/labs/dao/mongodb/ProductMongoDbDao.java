package ua.nure.st.patterns.labs.dao.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import ua.nure.st.patterns.labs.dao.ProductDao;
import ua.nure.st.patterns.labs.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMongoDbDao implements ProductDao<ObjectId> {

    private static final String COLLECTION_NAME = "products";

    private final String dbName;

    public ProductMongoDbDao(String dbName) {
        this.dbName = dbName;
    }

    public static Product<ObjectId> mapDocumentToProduct(Document document) {
        return new Product<>(
                document.getObjectId("_id"),
                document.getObjectId("brandId"),
                document.getObjectId("categoryId"),
                document.getString("name"),
                document.getLong("price")
        );
    }

    @Override
    public List<Product<ObjectId>> getAll() {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.find().into(new ArrayList<>());

            return documents.stream()
                    .map(ProductMongoDbDao::mapDocumentToProduct)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Product<ObjectId>> getAllByName(String name) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.find(new Document("name", name)).into(new ArrayList<>());

            return documents.stream()
                    .map(ProductMongoDbDao::mapDocumentToProduct)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Product<ObjectId>> getAllByBrandId(String brandId) {
        ObjectId brandObjectId = new ObjectId(brandId);
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.find(new Document("brandId", brandObjectId)).into(new ArrayList<>());

            return documents.stream()
                    .map(ProductMongoDbDao::mapDocumentToProduct)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Product<ObjectId>> getAllByCategoryId(String categoryId) {
        ObjectId categoryObjectId = new ObjectId(categoryId);
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            List<Document> documents = collection.find(new Document("categoryId", categoryObjectId)).into(new ArrayList<>());

            return documents.stream()
                    .map(ProductMongoDbDao::mapDocumentToProduct)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Product<ObjectId> getById(String id) {
        ObjectId objectId = new ObjectId(id);
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = collection.find(new Document("_id", objectId)).first();

            if (document == null) {
                return null;
            }
            return mapDocumentToProduct(document);
        }
    }

    @Override
    public boolean save(String name, String description, Long price, String brandId, String categoryId) {
        ObjectId brandObjectId = new ObjectId(brandId);
        ObjectId categoryObjectId = new ObjectId(categoryId);
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document()
                    .append("name", name)
                    .append("description", description)
                    .append("price", price)
                    .append("brandId", brandObjectId)
                    .append("categoryId", categoryObjectId);

            collection.insertOne(document);

            return true;
        }
    }

    @Override
    public boolean update(Product<ObjectId> mySqlProduct) {
        try (MongoClient client = MongoDbDao.getMongoClient()) {
            MongoCollection<Document> collection = client.getDatabase(dbName)
                    .getCollection(COLLECTION_NAME);

            Document document = new Document()
                    .append("name", mySqlProduct.getName())
                    .append("price", mySqlProduct.getPrice())
                    .append("brandId", mySqlProduct.getBrandId())
                    .append("categoryId", mySqlProduct.getCategoryId());

            collection.updateOne(new Document("_id", mySqlProduct.getId()), new Document("$set", document));

            return true;
        }
    }

    @Override
    public boolean undo(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(String id) {
        throw new UnsupportedOperationException();
    }
}
