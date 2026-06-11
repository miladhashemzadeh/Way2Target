package com.vampyreworld.w2t.di

import com.vampyreworld.w2t.database.*
import com.vampyreworld.w2t.repository.*
import com.vampyreworld.w2t.domain.repository.*
import org.koin.core.module.Module
import org.koin.dsl.module

expect val platformDataModule: Module

val dataModule = module {
    includes(platformDataModule)
    single {
        val driver = get<DatabaseDriverFactory>().createDriver()
        W2TDatabase(
            driver = driver,
            MasterGoalEntityAdapter = MasterGoalEntity.Adapter(
                priorityAdapter = intAdapter,
                statusAdapter = goalStatusAdapter,
                milestoneIdsAdapter = longListAdapter
            ),
            MilestoneGoalEntityAdapter = MilestoneGoalEntity.Adapter(
                priorityAdapter = intAdapter,
                statusAdapter = goalStatusAdapter,
                actionIdsAdapter = longListAdapter,
                wayIdsAdapter = longListAdapter
            ),
            ActionGoalEntityAdapter = ActionGoalEntity.Adapter(
                priorityAdapter = intAdapter,
                statusAdapter = goalStatusAdapter,
                scheduleAdapter = actionScheduleAdapter,
                costAdapter = costAdapter
            ),
            ChallengeEntityAdapter = ChallengeEntity.Adapter(
                statusAdapter = goalStatusAdapter,
                costAdapter = costAdapter,
                priorityAdapter = intAdapter,
                moodImpactAdapter = intAdapter,
                candidateSolutionIdsAdapter = longListAdapter,
                appliedSolutionIdsAdapter = longListAdapter,
                failedSolutionIdsAdapter = longListAdapter
            ),
            SolutionEntityAdapter = SolutionEntity.Adapter(
                solutionTypeAdapter = solutionTypeAdapter,
                costAdapter = costAdapter,
                aidStrengthAdapter = intAdapter,
                resultAdapter = solutionResultAdapter
            ),
            WayEntityAdapter = WayEntity.Adapter(
                statusAdapter = wayStatusAdapter
            ),
            WayStepEntityAdapter = WayStepEntity.Adapter(
                positionAdapter = intAdapter
            ),
            DecisionEntityAdapter = DecisionEntity.Adapter(
                contextAdapter = decisionContextAdapter,
                costAdapter = costAdapter,
                userRateAdapter = intAdapter,
                aiScoreAdapter = intAdapter
            ),
            UserMoodEntityAdapter = UserMoodEntity.Adapter(
                energyRateAdapter = intAdapter,
                creativityRateAdapter = intAdapter,
                focusRateAdapter = intAdapter,
                socialRateAdapter = intAdapter,
                selfControlRateAdapter = intAdapter
            ),
            GoalRelationEntityAdapter = GoalRelationEntity.Adapter(
                typeAdapter = relationTypeAdapter,
                strengthAdapter = intAdapter
            )
        )
    }

    single<GoalRepository> { GoalRepositoryImpl(get()) }
    single<DecisionRepository> { DecisionRepositoryImpl(get()) }
    single<SolutionRepository> { SolutionRepositoryImpl(get()) }
    single<ChallengeRepository> { ChallengeRepositoryImpl(get()) }
    single<UserMoodRepository> { UserMoodRepositoryImpl(get()) }
}
