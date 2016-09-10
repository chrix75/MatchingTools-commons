package services

/**
 * Created by csperandio on 09/09/2016.
 */
class ObjectsPool<T> {
    private List<T> instances;
    private int current
    private Closure<T> builder

    ObjectsPool(Closure<T> builder) {
        instances = new ArrayList<>(50)
        current = 0
        this.builder = builder
    }

    T fetch(Closure init) {
        def instance
        if (current == instances.size()) {
            instance = builder()
            instances << instance
            current++
            init(instance)
        } else {
            instance = instances[current++]
        }

        init(instance)

        return instance
    }

    def clearPool() {
        current = 0
    }

    static def withObjectsPool(Closure<T> builder, Closure f) {
        def pool = new ObjectsPool<T>(builder)

        f.delegate = pool

        f()

        pool.clearPool()
    }




}
