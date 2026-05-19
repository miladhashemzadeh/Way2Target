package com.vampyreworld.w2t.shomeft

import com.arkivanov.decompose.ComponentContext

interface HomeComponent {
    fun onNavigateToTarget()
    fun onNavigateToMoodAdd()
    fun onNavigateToSChallenge()
    fun onNavigateToDecisionMaking()
    fun onNavigateToSolution()
    fun onNavigateToPreferences()
    fun onNavigateToAboutUs()
}

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val navigateToTarget: () -> Unit,
    private val navigateToMoodAdd: () -> Unit,
    private val navigateToSChallenge: () -> Unit,
    private val navigateToDecisionMaking: () -> Unit,
    private val navigateToSolution: () -> Unit,
    private val navigateToPreferences: () -> Unit,
    private val navigateToAboutUs: () -> Unit
) : HomeComponent, ComponentContext by componentContext {
    override fun onNavigateToTarget() = navigateToTarget()
    override fun onNavigateToMoodAdd() = navigateToMoodAdd()
    override fun onNavigateToSChallenge() = navigateToSChallenge()
    override fun onNavigateToDecisionMaking() = navigateToDecisionMaking()
    override fun onNavigateToSolution() = navigateToSolution()
    override fun onNavigateToPreferences() = navigateToPreferences()
    override fun onNavigateToAboutUs() = navigateToAboutUs()
}
