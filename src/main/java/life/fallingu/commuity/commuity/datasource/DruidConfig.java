package life.fallingu.commuity.commuity.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean//组件
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

}
