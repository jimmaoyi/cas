package org.apereo.cas.support.spnego.authentication.principal;

import org.apereo.cas.authentication.principal.DefaultPrincipalFactory;

import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;


/**
 * @author Misagh Moayyed
 * @since 3.0.0
 */
public class SpnegoCredentialsTests {

    @Test
    public void verifyToStringWithNoPrincipal() {
        val credentials = new SpnegoCredential(new byte[]{});
        assertTrue(credentials.getId().contains("unknown"));
    }

    @Test
    public void verifyToStringWithPrincipal() {
        val credentials = new SpnegoCredential(new byte[]{});
        val principal = new DefaultPrincipalFactory().createPrincipal("test");
        credentials.setPrincipal(principal);
        assertEquals("test", credentials.getId());
    }

    /**
     * Make sure that when the Principal becomes populated / changes we return a new hash
     */
    @Test
    public void verifyPrincipalAffectsHash() {
        val credential = new SpnegoCredential(new byte[]{});
        val hash1 = credential.hashCode();
        val principal = new DefaultPrincipalFactory().createPrincipal("test");
        credential.setPrincipal(principal);
        val hash2 = credential.hashCode();
        assertNotEquals(hash1, hash2);
    }
}
