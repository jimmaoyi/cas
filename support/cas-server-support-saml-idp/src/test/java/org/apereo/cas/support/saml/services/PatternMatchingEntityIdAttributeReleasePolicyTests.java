package org.apereo.cas.support.saml.services;

import org.apereo.cas.authentication.CoreAuthenticationTestUtils;
import org.apereo.cas.category.FileSystemCategory;
import org.apereo.cas.support.saml.BaseSamlIdPConfigurationTests;
import org.apereo.cas.util.CollectionUtils;

import lombok.val;
import org.apache.commons.io.FileUtils;
import org.junit.experimental.categories.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.FileSystemResource;

import static org.junit.Assert.*;

/**
 * This is {@link PatternMatchingEntityIdAttributeReleasePolicyTests}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
@Category(FileSystemCategory.class)
public class PatternMatchingEntityIdAttributeReleasePolicyTests extends BaseSamlIdPConfigurationTests {

    @BeforeAll
    public static void beforeClass() {
        METADATA_DIRECTORY = new FileSystemResource(FileUtils.getTempDirectory());
    }

    @Test
    public void verifyPatternDoesNotMatch() {
        val filter = new PatternMatchingEntityIdAttributeReleasePolicy();
        val registeredService = getSamlRegisteredServiceForTestShib();
        registeredService.setAttributeReleasePolicy(filter);
        val attributes = filter.getAttributes(CoreAuthenticationTestUtils.getPrincipal(),
            CoreAuthenticationTestUtils.getService(), registeredService);
        assertTrue(attributes.isEmpty());
    }

    @Test
    public void verifyPatternDoesMatch() {
        val filter = new PatternMatchingEntityIdAttributeReleasePolicy();
        filter.setEntityIds("https://sp.+");
        filter.setAllowedAttributes(CollectionUtils.wrapList("uid", "givenName", "displayName"));
        val registeredService = getSamlRegisteredServiceForTestShib();
        registeredService.setAttributeReleasePolicy(filter);
        val attributes = filter.getAttributes(CoreAuthenticationTestUtils.getPrincipal(),
            CoreAuthenticationTestUtils.getService(), registeredService);
        assertFalse(attributes.isEmpty());
    }
}
