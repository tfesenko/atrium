@file:Suppress("ObjectPropertyName")

package ch.tutteli.atrium.domain.creating

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.creating.Expect
import ch.tutteli.atrium.domain.creating.changers.ExtractedFeaturePostStep
import ch.tutteli.atrium.domain.creating.impl.*
import kotlin.js.JsName

/**
 * Access to the domain level of Atrium where this [Expect] is passed along,
 * scoping it to the domain of subjects whose type extends [Collection];
 * i.e. it returns a [CollectionDomain] for this [Expect].
 */
val <E, T : Collection<E>> Expect<T>._domain: CollectionDomain<E, T>
    get() = CollectionDomainImpl(
        CollectionSubDomainImpl(this),
        //TODO simplify once we have expect.config.impl in 0.10.0
        IterableDomainImpl(IterableSubDomainImpl(this), AnyDomainImpl(this, AnyInclNullableDomainImpl(this)))
    )

/**
 * Access to the domain level of Atrium where this [Expect] is passed along,
 * scoping it to the domain of subjects whose type extends [Collection] with an element type extending [Comparable];
 * i.e. it returns a [CollectionDomain] for this [Expect].
 */
val <E : Comparable<E>, T : Collection<E>> Expect<T>._domain: CollectionElementComparableDomain<E, T>
    // note, the generated JS is wrong but it works due to dynamic typing in JS and
    // as long as this definition is defined after the one above (as it is a subtype)
    // TODO remove this comment as soon as https://youtrack.jetbrains.com/issue/KT-33294 is fixed
    @JsName("_domainCollectionElementComparable")
    get() = CollectionElementComparableDomainImpl(
        CollectionElementComparableSubDomainImpl(this),
        CollectionSubDomainImpl(this),
        //TODO simplify once we have expect.config.impl in 0.10.0
        IterableElementComparableDomainImpl(
            IterableElementComparableSubDomainImpl(this),
            IterableDomainImpl(IterableSubDomainImpl(this), AnyDomainImpl(this, AnyInclNullableDomainImpl(this)))
        )
    )

/**
 * Represents the [ExpectDomain] whose type extends [Collection];
 * i.e. the subject of the underlying [expect] has such a type.
 */
interface CollectionDomain<E, T : Collection<E>> : CollectionSubDomain<E, T>, IterableDomain<E, T>

/**
 * Represents a sub-[ExpectDomain] whose type extends [Collection]
 * -- i.e. the subject of the underlying [expect] has such a type --
 * where it does not include the sub domains of super types of [Collection] (e.g. the functions of the [AnyDomain]).
 */
interface CollectionSubDomain<E, T : Collection<E>> : ExpectDomain<T> {

    fun isEmpty(): Assertion
    fun isNotEmpty(): Assertion

    /**
     * @since 0.9.0
     */
    val size: ExtractedFeaturePostStep<T, Int>
}

/**
 * Represents the [ExpectDomain] whose type extends [Collection] with an element type extending [Comparable];
 * i.e. the subject of the underlying [expect] has such a type.
 */
interface CollectionElementComparableDomain<E : Comparable<E>, T : Collection<E>> :
    CollectionElementComparableSubDomain<E, T>,
    CollectionDomain<E, T>,
    IterableElementComparableDomain<E, T>

/**
 * Represents a sub-[ExpectDomain] whose type extends [Collection] with an element type extending [Comparable]
 * -- i.e. the subject of the underlying [expect] has such a type --
 * where it does not include the sub domains of super types of [Collection] (e.g. the functions of the [AnyDomain]).
 */
interface CollectionElementComparableSubDomain<E : Comparable<E>, T : Collection<E>> : ExpectDomain<T>
