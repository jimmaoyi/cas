package org.apereo.cas.support.saml.services.idp.metadata.cache.resolver;

import org.apereo.cas.category.FileSystemCategory;
import org.apereo.cas.configuration.model.support.saml.idp.SamlIdPProperties;
import org.apereo.cas.support.saml.services.BaseSamlIdPServicesTests;
import org.apereo.cas.support.saml.services.SamlRegisteredService;

import lombok.val;
import org.apache.commons.io.FileUtils;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.TestPropertySource;

import static org.junit.Assert.*;

/**
 * This is {@link UrlResourceMetadataResolverTests}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */

@Category(FileSystemCategory.class)
@TestPropertySource(properties = {"cas.authn.samlIdp.metadata.location=file:/tmp"})
public class UrlResourceMetadataResolverTests extends BaseSamlIdPServicesTests {

    @Test
    public void verifyResolverSupports() {
        val props = new SamlIdPProperties();
        props.getMetadata().setLocation(new FileSystemResource(FileUtils.getTempDirectory()));
        val resolver = new UrlResourceMetadataResolver(props, openSamlConfigBean);
        val service = new SamlRegisteredService();
        service.setMetadataLocation("http://www.testshib.org/metadata/testshib-providers.xml");
        assertTrue(resolver.supports(service));
        service.setMetadataLocation("classpath:sample-sp.xml");
        assertFalse(resolver.supports(service));
    }

    @Test
    public void verifyResolverResolves() {
        val props = new SamlIdPProperties();
        props.getMetadata().setLocation(new FileSystemResource(FileUtils.getTempDirectory()));
        val service = new SamlRegisteredService();
        val resolver = new UrlResourceMetadataResolver(props, openSamlConfigBean);
        service.setName("TestShib");
        service.setId(1000);
        service.setMetadataLocation("http://www.testshib.org/metadata/testshib-providers.xml");
        val results = resolver.resolve(service);
        assertFalse(results.isEmpty());
    }
}
