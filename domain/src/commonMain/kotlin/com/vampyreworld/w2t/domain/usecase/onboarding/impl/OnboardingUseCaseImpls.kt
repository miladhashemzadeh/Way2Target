package com.vampyreworld.w2t.domain.usecase.onboarding.impl

import com.vampyreworld.w2t.domain.repository.SettingsRepository
import com.vampyreworld.w2t.domain.usecase.onboarding.IsOnboardingCompletedUseCase
import com.vampyreworld.w2t.domain.usecase.onboarding.SetOnboardingCompletedUseCase

class IsOnboardingCompletedUseCaseImpl(
    private val repository: SettingsRepository
) : IsOnboardingCompletedUseCase {
    override fun invoke(): Boolean = repository.getBoolean("onboarding_completed", false)
}

class SetOnboardingCompletedUseCaseImpl(
    private val repository: SettingsRepository
) : SetOnboardingCompletedUseCase {
    override fun invoke(completed: Boolean) = repository.setBoolean("onboarding_completed", completed)
}
