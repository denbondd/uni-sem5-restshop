package ua.nure.st.patterns.labs.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.nure.st.patterns.labs.dao.mysql.MySqlDao;

import javax.sql.DataSource;

@Configuration
public class DaoFactory {

    private final Dao dao;

    public DaoFactory(@Value("${db.type}") String type, DataSource dataSource) {
        DaoType daoType = DaoType.valueOf(type);
        if (daoType == DaoType.MY_SQL) {
            dao = new MySqlDao(dataSource);
        } else {
            throw new IllegalArgumentException("Unknown database type");
        }
    }

    @Bean
    public ProductDao getProductDao() {
        return dao.getProductDao();
    }

    @Bean
    public ShopDao getShopDao() {
        return dao.getShopDao();
    }

    @Bean
    public BrandDao getBrandDao() {
        return dao.getBrandDao();
    }

    @Bean
    public CategoryDao getCategoryDao() {
        return dao.getCategoryDao();
    }

    @Bean
    public ShopHasProductDao getShopHasProductDao() {
        return dao.getShopHasProductDao();
    }
}
