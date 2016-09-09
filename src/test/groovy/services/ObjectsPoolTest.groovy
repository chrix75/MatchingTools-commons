package services

import org.junit.Before
import org.junit.Test
/**
 * Created by csperandio on 09/09/2016.
 */
class ObjectsPoolTest {
    ObjectsPool<TestObject> pool

    @Before
    public void setUp() throws Exception {
        pool = new ObjectsPool<>( { new TestObject() })
    }

    @Test
    public void add() throws Exception {
        def o = pool.fetch { it.value = 'Youou' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o
    }

    @Test
    public void clear() throws Exception {
        def o = pool.fetch { it.value = 'Youou' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o

        pool.clear()

        o = pool.fetch { it.value = 'KIKO' }
        println o

        o = pool.fetch { it.value = 'JOJO' }
        println o

        o = pool.fetch { it.value = 'LIBN' }
        println o

        o = pool.fetch { it.value = 'Youou' }
        println o
    }

    @Test
    public void withObjectPool() {
        ObjectsPool.withObjectsPool( { new TestObject() }) { internalPool ->
            def o = internalPool.fetch { it.value = 'Youou' }
            println o

            o = internalPool.fetch { it.value = 'Youou' }
            println o

            o = internalPool.fetch { it.value = 'Youou' }
            println o
        }
    }

}