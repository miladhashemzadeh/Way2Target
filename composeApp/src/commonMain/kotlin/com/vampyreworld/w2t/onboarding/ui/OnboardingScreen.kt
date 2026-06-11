package com.vampyreworld.w2t.onboarding.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vampyreworld.w2t.onboarding.OnboardingComponent
import com.vampyreworld.w2t.onboarding.OnboardingContract
import com.vampyreworld.w2t.sharedui.catalog.W2TOnboardingItem
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(component: OnboardingComponent) {
    val colors = LocalAppColorScheme.current
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()

    val pageTitles = listOf(
        "Structure Your Ambition",
        "Overcome Any Obstacle",
        "Optimize Your Performance"
    )
    val pageSubtitles = listOf(
        "Break down big dreams into achievable steps.",
        "Turn blockers into breakthroughs with a powerful challenge system.",
        "Use AI-driven insights and mood tracking to reach your peak potential."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.bgLight)
            .padding(32.dp, 24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = pageTitles[pagerState.currentPage],
                style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                color = colors.accent,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pageSubtitles[pagerState.currentPage],
                style = MaterialTheme.typography.bodyLarge,
                color = colors.muted,
                textAlign = TextAlign.Center
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f).padding(vertical = 32.dp)
        ) { page ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when (page) {
                    0 -> {
                        W2TOnboardingItem(
                            icon = "🎯",
                            title = "Master Goal",
                            description = "Your long-term strategic vision.",
                            iconBackgroundColor = colors.accent
                        )
                        W2TOnboardingItem(
                            icon = "✨",
                            title = "Milestone Goal",
                            description = "Measurable checkpoints on your path.",
                            iconBackgroundColor = Color(0xFF815512)
                        )
                        W2TOnboardingItem(
                            icon = "✅",
                            title = "Action Goal",
                            description = "Small, executable daily tasks with reminders.",
                            iconBackgroundColor = colors.success
                        )
                    }
                    1 -> {
                        W2TOnboardingItem(
                            icon = "🚧",
                            title = "Challenge System",
                            description = "Create challenges when you feel blocked on a goal.",
                            iconBackgroundColor = colors.challengeColor
                        )
                        W2TOnboardingItem(
                            icon = "💡",
                            title = "Solution Engine",
                            description = "Explore multiple solutions, powered by AI.",
                            iconBackgroundColor = colors.success
                        )
                    }
                    2 -> {
                        W2TOnboardingItem(
                            icon = "🤖",
                            title = "AI Insights",
                            description = "Get smart recommendations based on your progress.",
                            iconBackgroundColor = colors.moodHighEnergyStart
                        )
                        W2TOnboardingItem(
                            icon = "🧠",
                            title = "Mood Stability",
                            description = "Track your mental state to optimize decisions.",
                            iconBackgroundColor = colors.moodFocusedStart
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = { 
                    if (pagerState.currentPage > 0) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) }
                    } else {
                        component.onIntent(OnboardingContract.Intent.OnFinishClicked)
                    }
                },
                colors = ButtonDefaults.textButtonColors(contentColor = colors.muted)
            ) {
                Text(if (pagerState.currentPage > 0) "Back" else "Skip", fontWeight = FontWeight.SemiBold)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(3) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .height(8.dp)
                            .width(if (isSelected) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (isSelected) colors.accent else colors.border)
                    )
                }
            }

            Button(
                onClick = {
                    if (pagerState.currentPage < 2) {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        component.onIntent(OnboardingContract.Intent.OnFinishClicked)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                shape = RoundedCornerShape(28.dp),
                contentPadding = PaddingValues(horizontal = 28.dp, vertical = 14.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage < 2) "Next" else "Get Started",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}
