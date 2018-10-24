package org.apereo.cas.adaptors.radius.authentication.handler.support;

import org.apereo.cas.authentication.AuthenticationHandler;
import org.apereo.cas.authentication.CoreAuthenticationTestUtils;
import org.apereo.cas.category.RadiusCategory;
import org.apereo.cas.config.RadiusConfiguration;
import org.apereo.cas.util.junit.ConditionalIgnore;
import org.apereo.cas.util.junit.ConditionalIgnoreRule;
import org.apereo.cas.util.junit.RunningContinuousIntegrationCondition;

import lombok.val;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static org.junit.Assert.*;

/**
 * This is {@link RadiusAuthenticationHandlerTests}.
 *
 * @author Misagh Moayyed
 * @since 6.0.0
 */
@SpringBootTest(classes = {RadiusConfiguration.class, RefreshAutoConfiguration.class})
@TestPropertySource(properties = {
    "cas.authn.radius.server.protocol=PAP",
    "cas.authn.radius.client.sharedSecret=testing123",
    "cas.authn.radius.client.inetAddress=localhost"
})
@Category(RadiusCategory.class)
@ConditionalIgnore(condition = RunningContinuousIntegrationCondition.class)
public class RadiusAuthenticationHandlerTests {
    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final ConditionalIgnoreRule conditionalIgnoreRule = new ConditionalIgnoreRule();
    
    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    @Qualifier("radiusAuthenticationHandler")
    private AuthenticationHandler radiusAuthenticationHandler;

    @Test
    public void verifyOperation() throws Exception {
        val result = radiusAuthenticationHandler.authenticate(CoreAuthenticationTestUtils.getCredentialsWithDifferentUsernameAndPassword("casuser", "Mellon"));
        assertNotNull(result);
    }
}
