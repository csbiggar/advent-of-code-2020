package day7

import day5.Seat
import day5.findMissingSeat
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class BagsKtTest {

    @Test
    fun `find my bag`() {
        val gold = Bag("gold")
        val red = Bag("red")
        val white = Bag("white")
        val orange = Bag("orange")
        val yellow = Bag("yellow")
        val blue = Bag("blue")
        val olive = Bag("olive")
        val plum = Bag("plum")

        val relationships = listOf(
            BagRule(parent = red, mapOf(white to Quantity(1))),
            BagRule(parent = orange, mapOf(white to Quantity(3), yellow to Quantity(1))),
            BagRule(parent = white, mapOf(gold to Quantity(1))),
            BagRule(parent = yellow, mapOf(gold to Quantity(2), blue to Quantity(9))),
            BagRule(parent = gold, mapOf(olive to Quantity(1), plum to Quantity(9))),
            BagRule(parent = olive, emptyMap()),
            BagRule(parent = olive, mapOf(blue to Quantity(2))),    //Trick!
            BagRule(parent = blue, emptyMap()),
            BagRule(parent = plum, emptyMap()),
        )
        val result = FindMyBag(gold).withRules(relationships)
        assertThat(result).containsExactlyInAnyOrder(red, white, orange, yellow)
    }

}

class BagRuleText {

    @ParameterizedTest
    @MethodSource("bagStringToRules")
    fun `parse a line into a bag rule`(text: String, expectedRule: BagRule) {
        assertThat(BagRule.fromText(text)).isEqualTo(expectedRule)
    }

    companion object {
        @JvmStatic
        fun bagStringToRules(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(
                    "light red bags contain 1 bright white bag, 2 muted yellow bags.",
                    BagRule(
                        parent = Bag("light red"),
                        children = mapOf(
                            Bag("bright white") to Quantity(1),
                            Bag("muted yellow") to Quantity(2)
                        )
                    )
                ),
                Arguments.of(
                    "faded blue bags contain no other bags.",
                    BagRule(
                        parent = Bag("faded blue"),
                        children = mapOf()
                    )
                )
            )
        }
    }
}