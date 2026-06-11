package com.vampyreworld.w2t.domain.data.model

import kotlinx.serialization.Serializable

/**
 * Sealed interface for all nodes that can appear as steps in a [Way].
 *
 * Implementors:
 * - [Challenges] — an obstacle the user must overcome
 * - [Solution]   — a concrete action taken to resolve a challenge
 *
 * Using a sealed interface guarantees exhaustive `when` handling and
 * lets the domain layer traverse a [Way.steps] list in a type-safe manner.
 */
@Serializable
sealed interface WayNode
