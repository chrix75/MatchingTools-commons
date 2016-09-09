package services

/**
 * Created by csperandio on 09/09/2016.
 */
class TestObject {
    static  int counter = 0
    int id
    String value

    TestObject() { id = ++counter  }

    @Override
    String toString() {
        "ID=$id VALUE=$value"
    }
}
