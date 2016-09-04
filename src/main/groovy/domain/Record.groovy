package domain

/**
 * Created by Christian Sperandio on 27/08/2016.
 */
class Record {
    final List<CompanyName> names
    final List<Address> addresses
    final String siret
    final String city
    final long recorId

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
