package services

import domain.Match
import info.debatty.java.stringsimilarity.JaroWinkler
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Created by Christian Sperandio on 26/08/2016.
 */
class ExpressionsMatcher {
    private static Logger logger = LoggerFactory.getLogger(ExpressionsMatcher.class)

    private List weakWords
    private JaroWinkler jaroWinkler = new JaroWinkler()
    private boolean orderedMode


    ExpressionsMatcher(List weakWords, boolean orderedMode = false) {
        this.weakWords = weakWords
        this.orderedMode = orderedMode
    }

    Match match(List words1, List words2) {

        logger.debug("Match $words1 vs $words2")

        if (!words1 || !words2) { return Match.EMPTY }
        
        def keptWords1
        def keptWords2
        def combinations
        
        if (!orderedMode) {
            keptWords1 = words1.findAll { !weakWords.contains(it)} as Set
            keptWords2 = words2.findAll { !weakWords.contains(it)} as Set
            combinations = [keptWords1, keptWords2].combinations()
        } else {
            keptWords1 = words1.findAll { !weakWords.contains(it)}
            keptWords2 = words2.findAll { !weakWords.contains(it)}
            combinations = [keptWords1, keptWords2].transpose()
        }

        if (!keptWords1 || !keptWords2) { return Match.EMPTY }


        def comparisons = compareWords(combinations)

        def matchComparisons = comparisons.findAll { it[2] == Match.MATCH }

        def matchWords1 = matchComparisons.collect{ it[0] } as Set
        def matchWords2 = matchComparisons.collect{ it[1] } as Set


        def score1 = matchWords1.size() / keptWords1.size() as float
        def score2 = matchWords2.size() / keptWords2.size() as float

        def totalScore = (score1 + score2) / 2

        logger.debug("Score=$totalScore")

        totalScore > 0.85 ? Match.MATCH : Match.UNMATCH
    }

    private List compareWords(List combinations) {
        combinations.collect {
            def (w1, w2) = it
            def score = jaroWinkler.similarity(w1, w2)

            logger.debug("Words comparison [$w1] vs [$w2] = $score")

            score > 0.9 ? [w1, w2, Match.MATCH] : [w1, w2, Match.UNMATCH]
        }
    }

}
