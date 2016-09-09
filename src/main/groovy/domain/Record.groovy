package domain

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class Record {
    List<CompanyName> names
    List<Address> addresses
    String siret
    String city
    long recorId

    Record() {}

    Record(long recordId, List<CompanyName> names, List<Address> addresses, String siret, String city) {
        this.names = new ArrayList<>(names)
        this.addresses = new ArrayList<>(addresses)
        this.siret = siret
        this.city = city
        this.recorId = recordId
    }

    @Override
    String toString() {
        "Record [recordID: $recorId, names: $names, addresses: $addresses, siret: $siret, city: $city]"
    }
}
