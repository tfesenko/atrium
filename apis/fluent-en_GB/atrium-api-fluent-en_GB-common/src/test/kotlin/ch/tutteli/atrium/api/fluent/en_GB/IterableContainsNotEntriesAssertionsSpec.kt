package ch.tutteli.atrium.api.fluent.en_GB

import ch.tutteli.atrium.creating.Expect

class IterableContainsNotEntriesAssertionsSpec :
    ch.tutteli.atrium.specs.integration.IterableContainsNotEntriesAssertionsSpec(
        getContainsNotPair(),
        getContainsNotNullablePair(),
        "◆ ", "✔ ", "✘ ", "⚬ ", "» ", "▶ ", "◾ ",
        "[Atrium][Builder] "
    ) {
    companion object : IterableContainsSpecBase() {

        private fun getContainsNotPair() = containsNot to Companion::containsNotFun

        private fun containsNotFun(
            expect: Expect<Iterable<Double>>,
            a: Expect<Double>.() -> Unit,
            aX: Array<out Expect<Double>.() -> Unit>
        ): Expect<Iterable<Double>> =
            if (aX.isEmpty()) expect.containsNot.entry(a)
            else expect.containsNot.entries(a, *aX)

        private fun getContainsNotNullablePair() = "$containsNot nullable" to Companion::containsNotNullableFun

        private fun containsNotNullableFun(
            expect: Expect<Iterable<Double?>>,
            a: (Expect<Double>.() -> Unit)?,
            aX: Array<out (Expect<Double>.() -> Unit)?>
        ): Expect<Iterable<Double?>> =
            if (aX.isEmpty()) expect.containsNot.entry(a)
            else expect.containsNot.entries(a, *aX)
    }
}
