import org.junit.Test;

import static org.junit.Assert.*;


public class testUnionFind {
    @Test
    public void testBasic() {
        UnionFind u = new UnionFind(8);
        assertEquals(5,u.find(5));
        assertFalse(u.connected(1, 3));
        u.union(1, 3);
        assertTrue(u.connected(1, 3));
        assertEquals(2, u.sizeOf(1));
        assertEquals(2, u.sizeOf(3));
        u.union(2, 4);
        u.union(1, 2);
        assertEquals(3, u.sizeOf(1));
        assertEquals(4, u.find(1));
    }
}
