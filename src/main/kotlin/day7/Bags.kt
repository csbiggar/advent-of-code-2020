package day7

import helpers.FileReader

private val input = FileReader("/day7/input.txt").readLines()

fun main() {
    val rules = input.map {
        BagRule.fromText(it)
    }

    println("All rules mapped! Now to find my bag ...")

    val myBag = Bag("shiny gold")
    val bagsContainingMine = FindMyBag(myBag).withRules(rules)

    println("${bagsContainingMine.size} bags could eventually contain my $myBag ")
}


data class Bag(val id: String)
data class Quantity(val value: Int) {
    operator fun plus(another: Quantity) = Quantity(this.value + another.value)
    operator fun times(another: Quantity) = Quantity(this.value * another.value)
}

data class BagRule(
    val parent: Bag,
    val children: Map<Bag, Quantity>
) {
    companion object {
        fun fromText(text: String): BagRule {
            val parent = Bag(text.substringBefore("bag").trim())

            val children = text
                .substringAfter("contain")
                .split(",")
                .mapNotNull {
                    val parts = it.split(" ")
                    parts[1].toIntOrNull()
                        ?.let { quantity -> Bag("${parts[2]} ${parts[3]}") to Quantity(quantity) }

                }
                .toMap()

            return BagRule(parent, children)
        }
    }
}

class FindMyBag(private val myBag: Bag) {

    tailrec fun withRules(
        bagRules: List<BagRule> = emptyList(),
        bagsContainingMine: List<Bag> = emptyList()
    ): List<Bag> {

        println("remainingRules: ${bagRules.size}, running total ${bagsContainingMine.size}")

        val (containsMine, doesNotContainMine) = bagRules
            .filterNot { it.parent == myBag }
            .filterNot { it.children.isEmpty() }
            .partition { (listOf(myBag) + bagsContainingMine).intersect(it.children.keys).isNotEmpty() }

        val runningTotal = containsMine.map { it.parent } + bagsContainingMine

        if (
            doesNotContainMine.isEmpty()    // Nothing more to search
            || containsMine.isEmpty() // No more found containing mine, stop searching
        ) {
            return runningTotal
        }
        return withRules(doesNotContainMine, runningTotal)

    }
}
/*
       val relationships = listOf(
            Relationship(parent = red, mapOf(white to Quantity(1))),
            Relationship(parent = orange, mapOf(white to Quantity(3), yellow to Quantity(1))),
            Relationship(parent = gold, mapOf(olive to Quantity(1), plum to Quantity(9))),
        )
*/



fun findParents(bag: Bag, bagRule: List<BagRule>) {

}