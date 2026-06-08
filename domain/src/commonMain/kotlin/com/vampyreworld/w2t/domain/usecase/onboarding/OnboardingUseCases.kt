package com.vampyreworld.w2t.domain.usecase.onboarding

interface IsOnboardingCompletedUseCase {
    operator fun invoke(): Boolean
}

interface SetOnboardingCompletedUseCase {
    operator fun invoke(completed: Boolean)
}
