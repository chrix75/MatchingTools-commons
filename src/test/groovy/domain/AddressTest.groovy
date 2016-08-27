package domain

import org.junit.Test
import static org.junit.Assert.*

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class AddressTest {

    @Test
    void testTranlation() {
        def addr = new Address("12", "ZONE INDUSTRIELLE", "FOO DE BAR", "123", "45")

        assertEquals(12, addr.number)
        assertEquals("ZI", addr.way)
        assertEquals("FOO BAR", addr.name)
        assertEquals(123, addr.postBox)
        assertEquals(45, addr.roadNumber)
        assertFalse(addr.mainAddress)
    }

    @Test
    void testMainFlag() {
        def addr = new Address("", "RUE", "DE FOO DE BAR", "", "")

        assertEquals(0, addr.number)
        assertEquals("RUE", addr.way)
        assertEquals("FOO BAR", addr.name)
        assertEquals(0, addr.postBox)
        assertEquals(0, addr.roadNumber)
        assertTrue(addr.mainAddress)
    }
}
