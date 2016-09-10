package domain

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.regex.Pattern

/**
 *  Defines an address object.
 *
 * Created by csperandio on 24/08/2016.
 */
class Address {
    private static Logger logger = LoggerFactory.getLogger(Address.class)

    int number
    String way
    String name
    int postBox
    int roadNumber
    boolean mainAddress

    private Map wayTranslator = ["ZONE INDUSTRIELLE": "ZI",
                                 "Z I"              : "ZI",
                                 "ZI"               : "ZI",
                                 "ZONE IND"         : "ZI",
                                 "ZONE INDUS"       : "ZI",
                                 "ZONE D ACTIVITE"  : "ZA",
                                 "ZONE ACTIVITE"    : "ZA",
                                 "ZONE D ACTIVITES" : "ZA",
                                 "ZONE ACTIVITES"   : "ZA",
                                 "ZONE ARTISANALE"  : "ZA",
                                 "ZONE ARTISANAL"   : "ZA",
                                 "ZAC"              : "ZA",
                                 "BOULEVARD"        : "BD",
                                 "PLACE"            : "PL",
                                 "AVENUE"           : "AV",
                                 "SQUARE"           : "SQ",
                                 "LIEU DIT"         : "LD",
                                 "PETITE RUE"       : "PR",
                                 "PETIT RUE"        : "PR",
                                 "GRANDE RUE"       : "GR",
                                 "GRAND RUE"        : "GR"]

    private List<String> mainWays = [ "RUE", "AV", "BD", "PL", "COURS", "GR", "PR" ]

    private List weakWords = [" DE ", " DU ", " D ", " DES ", " LE ", " LA ", " L " ]

    private Pattern numberAtBeginning = ~/^(\d+)/

    Address() { println "Address Constructor" }

    Address(String number, String way, String name, String postBoxNumber = "", String roadNumber = "") {

        logger.debug("Input address: number=$number way=$way name=$name postBoxNumber=$postBoxNumber roadNumber=$roadNumber")

        if (number.empty) { number = "0" }
        if (postBoxNumber.empty) { postBoxNumber = "0" }
        if (roadNumber.empty) { roadNumber = "0" }

        def rawNumber = extractNumber(number)
        def rawName = clean(name)

        this.way = translate(way)

        if (!rawName.empty && this.way.empty) {
            def extractedNumber = catchAgainNumber(rawName)

            if (extractedNumber) {
                this.number = extractedNumber
                this.name = (rawName - Integer.toString(extractedNumber)).trim()
            } else {
                this.number = rawNumber
                this.name = rawName
            }
        } else {
            this.number = rawNumber
            this.name = rawName
        }

        this.postBox = Integer.parseInt(postBoxNumber)

        this.roadNumber = Integer.parseInt(roadNumber)

        this.mainAddress = mainWays.contains(this.way)

        logger.debug("$this")
    }

    int catchAgainNumber(String s) {
        def matcher = s =~ numberAtBeginning

        if (matcher) { Integer.parseInt(matcher[0][0])}
        else 0
    }

    String clean(String s) {
        if (s.trim()) {
            def cleanValue = " $s "

            weakWords.each { cleanValue = cleanValue.replaceAll(it, " ") }

            cleanValue.trim()
        } else { "" }
    }

    String translate(String s) {
        def trimed = s.trim()

        if (wayTranslator.containsKey(trimed)) {
            def translated = wayTranslator[trimed]
            logger.debug("Translate the way $trimed to $translated")
            translated
        }

        else { trimed }
    }

    String toString() {
        "Address [number: $number, way: $way, name: $name, postbox: $postBox, road: $roadNumber]"
    }

    int extractNumber(String candidate) {
        def trimed = candidate.trim()
        def i = 0
        for (; i < trimed.size(); ++i) {
            def c = trimed[i] as Character
            if (!c.isDigit()) { break }
        }

        if (i > 0 && i < 5) {
            def foundNumber = Integer.parseInt(trimed[0..i - 1])
            logger.debug("extractNumber : Candidate: $candidate Number: $foundNumber")
            foundNumber
        }
        else { 0 }
    }
}
