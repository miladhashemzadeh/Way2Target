package com.vampyreworld.w2t.domain.di

import com.vampyreworld.w2t.domain.usecase.*
import com.vampyreworld.w2t.domain.usecase.decision.impl.*
import com.vampyreworld.w2t.domain.usecase.mood.impl.*
import com.vampyreworld.w2t.domain.usecase.target.impl.*
import com.vampyreworld.w2t.domain.usecase.solution.impl.*
import com.vampyreworld.w2t.domain.usecase.schallenge.impl.*
import com.vampyreworld.w2t.domain.usecase.onboarding.*
import com.vampyreworld.w2t.domain.usecase.onboarding.impl.*
import com.vampyreworld.w2t.domain.usecase.profile.GetUserProfileUseCase
import com.vampyreworld.w2t.domain.usecase.profile.SaveUserProfileUseCase
import com.vampyreworld.w2t.domain.usecase.prefrences.GetThemeUseCase
import com.vampyreworld.w2t.domain.usecase.prefrences.SetThemeUseCase
import org.koin.dsl.module

val domainModule = module {
    // Profile
    factory { GetUserProfileUseCase(get()) }
    factory { SaveUserProfileUseCase(get()) }

    // Preferences
    factory { GetThemeUseCase(get()) }
    factory { SetThemeUseCase(get()) }

    // Onboarding
    factory<IsOnboardingCompletedUseCase> { IsOnboardingCompletedUseCaseImpl(get()) }
    factory<SetOnboardingCompletedUseCase> { SetOnboardingCompletedUseCaseImpl(get()) }

    // Goals
    factory<GetGoalsUseCase> { GetGoalsUseCaseImpl(get()) }
    factory<SaveGoalUseCase> { SaveGoalUseCaseImpl(get()) }
    factory<DeleteGoalUseCase> { DeleteGoalUseCaseImpl(get()) }
    factory<GetGoalByIdUseCase> { GetGoalByIdUseCaseImpl(get()) }

    // Mood
    factory<AddMoodUseCase> { AddMoodUseCaseImpl(get()) }
    factory<GetMoodHistoryUseCase> { GetMoodHistoryUseCaseImpl(get()) }

    // Decisions
    factory<GetDecisionsUseCase> { GetDecisionsUseCaseImpl(get()) }
    factory<SaveDecisionUseCase> { SaveDecisionUseCaseImpl(get()) }

    // Solutions
    factory<GetSolutionsUseCase> { GetSolutionsUseCaseImpl(get()) }
    factory<AddSolutionUseCase> { AddSolutionUseCaseImpl(get()) }

    // Challenges
    factory<GetChallengesUseCase> { GetChallengesUseCaseImpl(get()) }
    factory<AddChallengeUseCase> { AddChallengeUseCaseImpl(get()) }
    factory<GetChallengeByIdUseCase> { GetChallengeByIdUseCaseImpl(get()) }
    factory<DeleteChallengeUseCase> { DeleteChallengeUseCaseImpl(get()) }
}
