package day7

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

internal class BagsKtTest {
    private val gold = Bag("gold")
    private val red = Bag("red")
    private val white = Bag("white")
    private val orange = Bag("orange")
    private val yellow = Bag("yellow")
    private val blue = Bag("blue")
    private val olive = Bag("olive")
    private val plum = Bag("plum")
    private val black = Bag("black")

    private val rules = listOf(
        BagRule(parent = red, mapOf(white to Quantity(1), yellow to Quantity(2))),
        BagRule(parent = orange, mapOf(white to Quantity(3), yellow to Quantity(4))),
        BagRule(parent = white, mapOf(gold to Quantity(1))),
        BagRule(parent = yellow, mapOf(gold to Quantity(2), blue to Quantity(9))),
        BagRule(parent = gold, mapOf(olive to Quantity(1), plum to Quantity(2))),
        BagRule(parent = olive, mapOf(blue to Quantity(3), black to Quantity(4))),
        BagRule(parent = plum, mapOf(blue to Quantity(5), black to Quantity(6))),
        BagRule(parent = blue, emptyMap()),
        BagRule(parent = black, emptyMap()),
    )

    @Test
    fun `find my bag`() {
        val result = FindMyBag(gold, rules).insideWhichOthers()
        assertThat(result).containsExactlyInAnyOrder(red, white, orange, yellow)
    }

    @Test
    fun `find bags inside my bag`() {
        val result = FindMyBag(gold, rules).containsHowManyOthers()
        assertThat(result).isEqualTo(32)
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