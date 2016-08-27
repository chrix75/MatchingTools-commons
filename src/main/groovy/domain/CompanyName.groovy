package domain

/**
 * Created by csperandio on 25/08/2016.
 */
class CompanyName {
    final Boolean isService
    final List<String> wordsName

    CompanyName(String field) {
        def words = buildWords(field.tokenize(" "))

        this.isService = isService(words)
        this.wordsName = words
    }

    Boolean isService(List words) {
        words && words[0] in ["SERVICE", "SERVICES", "SVC", "SERV", "SVCES" ]
    }

    List buildWords(List words) {
        def corrected = []
        def current = []

        words.each { w ->
            if (w.size() > 1) {
                if (current) {
                    corrected << current.join()
                    current.clear()
                }

                corrected << w
            } else {
                current << w
            }
        }

        if (current) {
            corrected << current.join()
        }

        corrected
    }

}
