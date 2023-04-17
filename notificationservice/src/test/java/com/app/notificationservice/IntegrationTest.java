package com.app.notificationservice;

import com.app.notificationservice.NotificationserviceApp;
import com.app.notificationservice.config.AsyncSyncConfiguration;
import com.app.notificationservice.config.EmbeddedElasticsearch;
import com.app.notificationservice.config.EmbeddedKafka;
import com.app.notificationservice.config.EmbeddedSQL;
import com.app.notificationservice.config.TestSecurityConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { NotificationserviceApp.class, AsyncSyncConfiguration.class, TestSecurityConfiguration.class })
@EmbeddedElasticsearch
@EmbeddedKafka
@EmbeddedSQL
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public @interface IntegrationTest {
}
