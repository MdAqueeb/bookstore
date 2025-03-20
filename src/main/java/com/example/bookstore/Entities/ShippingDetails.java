package com.example.bookstore.Entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class ShippingDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long shippingId;

    // The order this shipping detail belongs to
    @OneToOne
    @JoinColumn(name = "orderid")
    private Order orders;

    // Shipping Address
    @Column(nullable = false)
    private String shippingAddress;

    // Shipping Method (Standard, Express, etc.)
    // @Column(nullable = false)
    // private String shippingMethod;

    // Shipping Carrier (FedEx, UPS, etc.)
    // @Column(nullable = true)
    // private String shippingCarrier;

    // // Tracking Number
    // @Column(nullable = true)
    // private String trackingNumber;

    // Shipping Status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private ShippingStatus shippingStatus = ShippingStatus.PENDING;

    // Enum for shipping statuses
    public enum ShippingStatus {
        PENDING, SHIPPED, DELIVERED, CANCELED, RETURNED
    }

    // Shipping Cost
    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal shippingCost ;

    // Optional: Estimated delivery date
    @Column(nullable = true)
    
    private LocalDateTime estimatedDeliveryDate;


}





// references books (bookid)" via JDBC [You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order 
// add constraint FKhf1w36f4ngv6wihyk287wi40k 
// foreign key (bo' at line 1]

// org.hibernate.tool.schema.spi.CommandAcceptanceException: Error executing DDL "
// alter table order 
// add constraint FKhf1w36f4ngv6wihyk287wi40k 
// foreign key (bookid) 
// references books (bookid)" via JDBC [You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order 
// add constraint FKhf1w36f4ngv6wihyk287wi40k 
// foreign key (bo' at line 1]
//  at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:94) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applySqlString(AbstractSchemaMigrator.java:576) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applySqlStrings(AbstractSchemaMigrator.java:516) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applyForeignKeys(AbstractSchemaMigrator.java:448) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.performMigration(AbstractSchemaMigrator.java:269) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.doMigration(AbstractSchemaMigrator.java:112) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:280) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.lambda$process$5(SchemaManagementToolCoordinator.java:144) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at java.base/java.util.HashMap.forEach(HashMap.java:1430) ~[na:na]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:141) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.boot.internal.SessionFactoryObserverForSchemaExport.sessionFactoryCreated(SessionFactoryObserverForSchemaExport.java:37) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.internal.SessionFactoryObserverChain.sessionFactoryCreated(SessionFactoryObserverChain.java:35) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:324) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:463) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1517) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:66) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:390) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:419) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:400) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:366) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1859) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:601) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:523) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:339) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:346) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:337) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:207) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:970) ~[spring-context-6.2.3.jar:6.2.3]
//  at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:627) ~[spring-context-6.2.3.jar:6.2.3]
//  at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:752) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:439) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:318) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:1361) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:1350) ~[spring-boot-3.4.3.jar:3.4.3]
//  at com.example.bookstore.BookstoreApplication.main(BookstoreApplication.java:10) ~[classes/:na]
// Caused by: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order 
// add constraint FKhf1w36f4ngv6wihyk287wi40k 
// foreign key (bo' at line 1
//  at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:112) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.StatementImpl.executeInternal(StatementImpl.java:837) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.StatementImpl.execute(StatementImpl.java:685) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94) ~[HikariCP-5.1.0.jar:na]
//  at com.zaxxer.hikari.pool.HikariProxyStatement.execute(HikariProxyStatement.java) ~[HikariCP-5.1.0.jar:na]
//  at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:80) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  ... 36 common frames omitted

// Hibernate: 
// alter table order 
// add constraint FK7byn4e1754dr0ivrwhsfb2icf 
// foreign key (userid) 
// references user (userid)
// 2025-03-20T14:36:54.477+05:30  WARN 94860 --- [bookstore] [           main] o.h.t.s.i.ExceptionHandlerLoggedImpl     : GenerationTarget encountered exception accepting command : Error executing DDL "
// alter table order 
// add constraint FK7byn4e1754dr0ivrwhsfb2icf 
// foreign key (userid) 
// references user (userid)" via JDBC [You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order 
// add constraint FK7byn4e1754dr0ivrwhsfb2icf 
// foreign key (us' at line 1]

// org.hibernate.tool.schema.spi.CommandAcceptanceException: Error executing DDL "
// alter table order 
// add constraint FK7byn4e1754dr0ivrwhsfb2icf 
// foreign key (userid) 
// references user (userid)" via JDBC [You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order 
// add constraint FK7byn4e1754dr0ivrwhsfb2icf 
// foreign key (us' at line 1]
//  at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:94) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applySqlString(AbstractSchemaMigrator.java:576) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applySqlStrings(AbstractSchemaMigrator.java:516) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applyForeignKeys(AbstractSchemaMigrator.java:448) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.performMigration(AbstractSchemaMigrator.java:269) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.doMigration(AbstractSchemaMigrator.java:112) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:280) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.lambda$process$5(SchemaManagementToolCoordinator.java:144) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at java.base/java.util.HashMap.forEach(HashMap.java:1430) ~[na:na]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:141) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.boot.internal.SessionFactoryObserverForSchemaExport.sessionFactoryCreated(SessionFactoryObserverForSchemaExport.java:37) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.internal.SessionFactoryObserverChain.sessionFactoryCreated(SessionFactoryObserverChain.java:35) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:324) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:463) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1517) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:66) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:390) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:419) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:400) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:366) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1859) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:601) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:523) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:339) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:346) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:337) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:207) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:970) ~[spring-context-6.2.3.jar:6.2.3]
//  at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:627) ~[spring-context-6.2.3.jar:6.2.3]
//  at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:752) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:439) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:318) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:1361) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:1350) ~[spring-boot-3.4.3.jar:3.4.3]
//  at com.example.bookstore.BookstoreApplication.main(BookstoreApplication.java:10) ~[classes/:na]
// Caused by: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order 
// add constraint FK7byn4e1754dr0ivrwhsfb2icf 
// foreign key (us' at line 1
//  at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:112) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.StatementImpl.executeInternal(StatementImpl.java:837) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.StatementImpl.execute(StatementImpl.java:685) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94) ~[HikariCP-5.1.0.jar:na]
//  at com.zaxxer.hikari.pool.HikariProxyStatement.execute(HikariProxyStatement.java) ~[HikariCP-5.1.0.jar:na]
//  at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:80) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  ... 36 common frames omitted

// Hibernate: 
// alter table payment 
// add constraint FKn3jlt05ifdsmk7t8atkl7g43 
// foreign key (orderid) 
// references order (orderid)
// 2025-03-20T14:36:54.480+05:30  WARN 94860 --- [bookstore] [           main] o.h.t.s.i.ExceptionHandlerLoggedImpl     : GenerationTarget encountered exception accepting command : Error executing DDL "
// alter table payment 
// add constraint FKn3jlt05ifdsmk7t8atkl7g43 
// foreign key (orderid) 
// references order (orderid)" via JDBC [You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order (orderid)' at line 4]

// org.hibernate.tool.schema.spi.CommandAcceptanceException: Error executing DDL "
// alter table payment 
// add constraint FKn3jlt05ifdsmk7t8atkl7g43 
// foreign key (orderid) 
// references order (orderid)" via JDBC [You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order (orderid)' at line 4]
//  at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:94) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applySqlString(AbstractSchemaMigrator.java:576) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applySqlStrings(AbstractSchemaMigrator.java:516) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applyForeignKeys(AbstractSchemaMigrator.java:448) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.performMigration(AbstractSchemaMigrator.java:269) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.doMigration(AbstractSchemaMigrator.java:112) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:280) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.lambda$process$5(SchemaManagementToolCoordinator.java:144) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at java.base/java.util.HashMap.forEach(HashMap.java:1430) ~[na:na]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:141) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.boot.internal.SessionFactoryObserverForSchemaExport.sessionFactoryCreated(SessionFactoryObserverForSchemaExport.java:37) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.internal.SessionFactoryObserverChain.sessionFactoryCreated(SessionFactoryObserverChain.java:35) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:324) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:463) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1517) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:66) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:390) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:419) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:400) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:366) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1859) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:601) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:523) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:339) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:346) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:337) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:207) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:970) ~[spring-context-6.2.3.jar:6.2.3]
//  at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:627) ~[spring-context-6.2.3.jar:6.2.3]
//  at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:752) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:439) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:318) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:1361) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:1350) ~[spring-boot-3.4.3.jar:3.4.3]
//  at com.example.bookstore.BookstoreApplication.main(BookstoreApplication.java:10) ~[classes/:na]
// Caused by: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order (orderid)' at line 4
//  at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:112) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.StatementImpl.executeInternal(StatementImpl.java:837) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.StatementImpl.execute(StatementImpl.java:685) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94) ~[HikariCP-5.1.0.jar:na]
//  at com.zaxxer.hikari.pool.HikariProxyStatement.execute(HikariProxyStatement.java) ~[HikariCP-5.1.0.jar:na]
//  at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:80) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  ... 36 common frames omitted

// Hibernate: 
// alter table shipping_details 
// add constraint FK6b6wy9a2f35wp4pgdtl7ctux6 
// foreign key (orderid) 
// references order (orderid)
// 2025-03-20T14:36:54.484+05:30  WARN 94860 --- [bookstore] [           main] o.h.t.s.i.ExceptionHandlerLoggedImpl     : GenerationTarget encountered exception accepting command : Error executing DDL "
// alter table shipping_details 
// add constraint FK6b6wy9a2f35wp4pgdtl7ctux6 
// foreign key (orderid) 
// references order (orderid)" via JDBC [You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order (orderid)' at line 4]

// org.hibernate.tool.schema.spi.CommandAcceptanceException: Error executing DDL "
// alter table shipping_details 
// add constraint FK6b6wy9a2f35wp4pgdtl7ctux6 
// foreign key (orderid) 
// references order (orderid)" via JDBC [You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order (orderid)' at line 4]
//  at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:94) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applySqlString(AbstractSchemaMigrator.java:576) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applySqlStrings(AbstractSchemaMigrator.java:516) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.applyForeignKeys(AbstractSchemaMigrator.java:448) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.performMigration(AbstractSchemaMigrator.java:269) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.internal.AbstractSchemaMigrator.doMigration(AbstractSchemaMigrator.java:112) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.performDatabaseAction(SchemaManagementToolCoordinator.java:280) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.lambda$process$5(SchemaManagementToolCoordinator.java:144) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at java.base/java.util.HashMap.forEach(HashMap.java:1430) ~[na:na]
//  at org.hibernate.tool.schema.spi.SchemaManagementToolCoordinator.process(SchemaManagementToolCoordinator.java:141) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.boot.internal.SessionFactoryObserverForSchemaExport.sessionFactoryCreated(SessionFactoryObserverForSchemaExport.java:37) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.internal.SessionFactoryObserverChain.sessionFactoryCreated(SessionFactoryObserverChain.java:35) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.internal.SessionFactoryImpl.<init>(SessionFactoryImpl.java:324) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.boot.internal.SessionFactoryBuilderImpl.build(SessionFactoryBuilderImpl.java:463) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl.build(EntityManagerFactoryBuilderImpl.java:1517) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  at org.springframework.orm.jpa.vendor.SpringHibernateJpaPersistenceProvider.createContainerEntityManagerFactory(SpringHibernateJpaPersistenceProvider.java:66) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.createNativeEntityManagerFactory(LocalContainerEntityManagerFactoryBean.java:390) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.buildNativeEntityManagerFactory(AbstractEntityManagerFactoryBean.java:419) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.AbstractEntityManagerFactoryBean.afterPropertiesSet(AbstractEntityManagerFactoryBean.java:400) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean.afterPropertiesSet(LocalContainerEntityManagerFactoryBean.java:366) ~[spring-orm-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.invokeInitMethods(AbstractAutowireCapableBeanFactory.java:1859) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:601) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:523) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:339) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:346) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:337) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:207) ~[spring-beans-6.2.3.jar:6.2.3]
//  at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:970) ~[spring-context-6.2.3.jar:6.2.3]
//  at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:627) ~[spring-context-6.2.3.jar:6.2.3]
//  at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:146) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:752) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:439) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:318) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:1361) ~[spring-boot-3.4.3.jar:3.4.3]
//  at org.springframework.boot.SpringApplication.run(SpringApplication.java:1350) ~[spring-boot-3.4.3.jar:3.4.3]
//  at com.example.bookstore.BookstoreApplication.main(BookstoreApplication.java:10) ~[classes/:na]
// Caused by: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'order (orderid)' at line 4
//  at com.mysql.cj.jdbc.exceptions.SQLError.createSQLException(SQLError.java:112) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping.translateException(SQLExceptionsMapping.java:114) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.StatementImpl.executeInternal(StatementImpl.java:837) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.mysql.cj.jdbc.StatementImpl.execute(StatementImpl.java:685) ~[mysql-connector-j-9.1.0.jar:9.1.0]
//  at com.zaxxer.hikari.pool.ProxyStatement.execute(ProxyStatement.java:94) ~[HikariCP-5.1.0.jar:na]
//  at com.zaxxer.hikari.pool.HikariProxyStatement.execute(HikariProxyStatement.java) ~[HikariCP-5.1.0.jar:na]
//  at org.hibernate.tool.schema.internal.exec.GenerationTargetToDatabase.accept(GenerationTargetToDatabase.java:80) ~[hibernate-core-6.6.8.Final.jar:6.6.8.Final]
//  ... 36 common frames omitted

// Hibernate: 
// alter table wishlist 
// add constraint FKke4bi75bkr21ck354o9pr5dq5 
// foreign key (bookid) 
// references books (bookid)
// Hibernate: 
// alter table wishlist 
// add constraint FK24ulgcljrjs4ydet30v3gkub 
// foreign key (userid) 
// references user (userid)
// 2025-03-20T14:36:54.681+05:30  INFO 94860 --- [bookstore] [           main] j.LocalContainerEntityManagerFactoryBean : Initialized JPA EntityManagerFactory for persistence unit 'default'
// 2025-03-20T14:36:54.743+05:30  WARN 94860 --- [bookstore] [           main] JpaBaseConfiguration$JpaWebConfiguration : spring.jpa.open-in-view is enabled by default. Therefore, database queries may be performed during view rendering. Explicitly configure spring.jpa.open-in-view to disable this warning
// 2025-03-20T14:36:55.147+05:30  WARN 94860 --- [bookstore] [           main] .s.s.UserDetailsServiceAutoConfiguration : 

// Using generated security password: 10ce5825-1227-4b35-a7d1-4fe74271978d

// This generated password is for development use only. Your security configuration must be updated before running your application in production.

// 2025-03-20T14:36:55.161+05:30  INFO 94860 --- [bookstore] [           main] r$InitializeUserDetailsManagerConfigurer : Global AuthenticationManager configured with UserDetailsService bean with name inMemoryUserDetailsManager
// 2025-03-20T14:36:55.310+05:30  INFO 94860 --- [bookstore] [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path '/'
// 2025-03-20T14:36:55.319+05:30  INFO 94860 --- [bookstore] [           main] c.e.bookstore.BookstoreApplication       : Started BookstoreApplication in 6.051 seconds (process running for 6.746)

