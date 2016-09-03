package domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by csperandio on 25/08/2016.
 */
class CompanyName {
    private static Logger logger = LoggerFactory.getLogger(CompanyName.class)

    final Boolean isService
    final List<String> wordsName

    CompanyName(boolean isService, List<String> words) {
        this.isService = isService
        this.wordsName = new ArrayList<>(words)
    }

    CompanyName(String field) {
        logger.debug("Input: field=$field")

        def words = buildWords(field.tokenize(" "))

        this.isService = isService(words)
        this.wordsName = words

        logger.debug("$this")
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
                    corrected << current.join('')
                    current.clear()
                }

                corrected << w
            } else {
                current << w
            }
        }

        if (current) {
            corrected << current.join('')
        }

        corrected
    }

    @Override
    String toString() {
        "Company [words: ${this.wordsName}, isService: ${this.isService}]"
    }
}
