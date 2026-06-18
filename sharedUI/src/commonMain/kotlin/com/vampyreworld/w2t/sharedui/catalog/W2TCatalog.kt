package com.vampyreworld.w2t.sharedui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import com.vampyreworld.w2t.sharedui.theme.color.OD_Accent
import androidx.compose.ui.tooling.preview.Preview
import com.vampyreworld.w2t.sharedui.theme.W2TTheme
import com.vampyreworld.w2t.sharedui.theme.LocalUserProfile
import coil3.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale

@Composable
fun W2TCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            content = content
        )
    }
}

@Composable
fun W2TSectionTitle(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            fontWeight = FontWeight.Bold,
            fontSize = 1.3.sp * 16 // approximate based on 1.3rem
        ),
        color = MaterialTheme.colorScheme.onSurface,
        modifier = modifier.padding(bottom = 16.dp)
    )
}

@Composable
fun W2TProgressBar(
    progress: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier,
    height: androidx.compose.ui.unit.Dp = 8.dp,
    color: Color = LocalAppColorScheme.current.accent
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(LocalAppColorScheme.current.border)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .clip(CircleShape)
                .background(color)
        )
    }
}

@Composable
fun W2TMoodWidget(
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColorScheme.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(colors.moodHighEnergyStart, colors.moodHighEnergyEnd)
                )
            )
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Color.White
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f)
            )
            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = colors.accent
                ),
                shape = CircleShape,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Text(text = buttonText, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun W2TAiInsightsCard(
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColorScheme.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(colors.moodFocusedStart, colors.moodFocusedEnd)
                )
            )
            .padding(20.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "🤖", fontSize = 20.sp)
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f)
            )
            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = colors.moodFocusedStart
                ),
                shape = CircleShape,
                modifier = Modifier.padding(top = 12.dp)
            ) {
                Text(text = buttonText, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
fun W2TGoalItem(
    icon: String,
    title: String,
    progress: Float,
    progressText: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val colors = LocalAppColorScheme.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(colors.accent.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 16.sp)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                W2TProgressBar(progress = progress, modifier = Modifier.weight(1f), height = 6.dp)
                Text(
                    text = progressText,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.muted
                )
            }
        }
    }
}

@Composable
fun W2TActionItem(
    title: String,
    subtitle: String,
    time: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val colors = LocalAppColorScheme.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = colors.success,
                uncheckedColor = colors.border
            ),
            modifier = Modifier
                .size(24.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = colors.muted
            )
        }
        Text(
            text = time,
            style = MaterialTheme.typography.bodySmall,
            color = colors.muted
        )
    }
}

@Composable
fun W2THeader(
    title: String,
    subtitle: String,
    avatarText: String? = null,
    avatarUrl: String? = null,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColorScheme.current
    val userProfile = LocalUserProfile.current
    
    val finalAvatarUrl = avatarUrl ?: (if (avatarText == null) userProfile.avatarUrl else null)
    val finalAvatarText = avatarText ?: userProfile.name.take(1).uppercase().ifEmpty { "U" }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.muted
            )
        }
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(colors.accent.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            if (finalAvatarUrl != null) {
                AsyncImage(
                    model = finalAvatarUrl,
                    contentDescription = "Avatar",
                    modifier = Modifier.fillMaxSize().clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                Text(
                    text = finalAvatarText,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = colors.accent
                )
            }
        }
    }
}

@Composable
fun W2TTreeNode(
    icon: String,
    title: String,
    type: String, // "master", "milestone", "action"
    completed: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    content: (@Composable ColumnScope.() -> Unit)? = null
) {
    val colors = LocalAppColorScheme.current
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        when(type) {
                            "master" -> colors.accent
                            "milestone" -> Color(0xFF815512)
                            "action" -> colors.success
                            else -> colors.accent
                        }.copy(alpha = if (completed && type == "action") 0.7f else 1f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (icon.isNotEmpty()) Text(text = icon, fontSize = 12.sp, color = Color.White)
            }
            
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f))
                    .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                if (type == "action" && onCheckedChange != null) {
                    Checkbox(
                        checked = completed,
                        onCheckedChange = onCheckedChange,
                        colors = CheckboxDefaults.colors(checkedColor = colors.success)
                    )
                }
            }
        }
        if (content != null) {
            Row {
                Spacer(modifier = Modifier.width(11.dp))
                Box(modifier = Modifier.width(2.dp).fillMaxHeight().background(colors.border))
                Spacer(modifier = Modifier.width(11.dp))
                Column(modifier = Modifier.weight(1f), content = content)
            }
        }
    }
}

@Composable
fun W2TTabNav(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColorScheme.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = selectedTabIndex == index
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(if (isSelected) colors.accent else Color.Transparent)
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 10.dp, horizontal = 15.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    color = if (isSelected) Color.White else colors.muted,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.SemiBold)
                )
            }
        }
    }
}

@Composable
fun W2TChallengeCard(
    title: String,
    goalTitle: String,
    description: String,
    status: String, // "Ongoing", "Finished", "Failed"
    modifier: Modifier = Modifier,
    content: (@Composable ColumnScope.() -> Unit)? = null
) {
    val colors = LocalAppColorScheme.current
    val statusColor = when(status) {
        "Ongoing" -> colors.challengeColor
        "Finished" -> colors.success
        "Failed" -> Color(0xFFE47500) // approximate --failed oklch(70% 0.18 25)
        else -> colors.challengeColor
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box {
            // Left border accent
            Box(modifier = Modifier.matchParentSize().border(width = 6.dp, color = statusColor, shape = RoundedCornerShape(18.dp)))
            // Mask the border to only left side
            Box(modifier = Modifier.matchParentSize().padding(start = 6.dp).background(MaterialTheme.colorScheme.surface, RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)))

            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.weight(1f)
                    )
                    Surface(
                        color = statusColor,
                        shape = CircleShape
                    ) {
                        Text(
                            text = status,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "Goal: $goalTitle", style = MaterialTheme.typography.bodySmall, color = colors.muted)
                    Text(text = description, style = MaterialTheme.typography.bodySmall, color = colors.muted)
                }

                if (content != null) {
                    content()
                }
            }
        }
    }
}

@Composable
fun W2TSolutionItem(
    title: String,
    source: String,
    isAi: Boolean = false,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColorScheme.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(colors.bgLight.copy(alpha = 0.5f))
            .border(1.dp, colors.border, RoundedCornerShape(14.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(text = if (isAi) "🤖" else "💡", fontSize = 20.sp, color = colors.accent)
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
            )
            Text(
                text = source,
                style = MaterialTheme.typography.labelSmall,
                color = colors.muted
            )
        }
    }
}

@Composable
fun W2TStrategyCard(
    description: String,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColorScheme.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(
                Brush.linearGradient(
                    colors = listOf(colors.moodFocusedStart, colors.moodFocusedEnd)
                )
            )
            .padding(16.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "🤖", fontSize = 16.sp)
                Text(
                    text = "AI Strategy Recommendation",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = Color.White
                )
            }
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.White.copy(alpha = 0.9f)
            )
            Button(
                onClick = onButtonClick,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface, contentColor = colors.moodFocusedStart),
                shape = CircleShape,
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(text = "Implement Strategy", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

@Composable
fun W2TOnboardingItem(
    icon: String,
    title: String,
    description: String,
    iconBackgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(text = icon, fontSize = 24.sp, color = Color.White)
        }
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = LocalAppColorScheme.current.muted
            )
        }
    }
}

@Composable
fun W2TSelectableItem(
    title: String,
    subtitle: String? = null,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColorScheme.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) colors.accent.copy(alpha = 0.12f) else colors.bgLight.copy(alpha = 0.8f))
            .border(1.dp, if (selected) colors.accent else colors.border, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = colors.accent)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium))
            if (subtitle != null) {
                Text(text = subtitle, style = MaterialTheme.typography.labelSmall, color = colors.muted)
            }
        }
    }
}

@Composable
fun W2TRemovableItem(
    icon: String,
    title: String,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color = LocalAppColorScheme.current.accent
) {
    val colors = LocalAppColorScheme.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colors.bgLight.copy(alpha = 0.5f))
            .border(1.dp, colors.border, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = icon, fontSize = 20.sp, color = iconColor)
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onRemove, modifier = Modifier.size(24.dp)) {
            Text(text = "✕", fontSize = 16.sp, color = colors.muted)
        }
    }
}

@Composable
fun W2TStatusChip(
    text: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Surface(
        color = backgroundColor,
        shape = CircleShape,
        modifier = modifier
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = contentColor
        )
    }
}

@Composable
fun W2TDetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val colors = LocalAppColorScheme.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = colors.muted,
                textAlign = androidx.compose.ui.text.style.TextAlign.End,
                modifier = Modifier.weight(1f).padding(start = 16.dp)
            )
        }
        HorizontalDivider(color = colors.border.copy(alpha = 0.5f))
    }
}

@Composable
fun W2TBottomNavigation(
    onHomeClick: () -> Unit,
    onProfileClick: () -> Unit,
    onChallengesClick: () -> Unit,
    onSettingsClick: () -> Unit,
    selectedTab: Int // 0: Home, 1: Profile, 2: Challenges, 3: Settings
) {
    val colors = LocalAppColorScheme.current
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp,
        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = onHomeClick,
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.accent,
                selectedTextColor = colors.accent,
                unselectedIconColor = colors.muted,
                unselectedTextColor = colors.muted,
                indicatorColor = colors.accent.copy(alpha = 0.1f)
            )
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = onProfileClick,
            icon = { Icon(Icons.Default.Person, contentDescription = null) },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.accent,
                selectedTextColor = colors.accent,
                unselectedIconColor = colors.muted,
                unselectedTextColor = colors.muted,
                indicatorColor = colors.accent.copy(alpha = 0.1f)
            )
        )
        NavigationBarItem(
            selected = selectedTab == 2,
            onClick = onChallengesClick,
            icon = { Icon(Icons.Default.Flag, contentDescription = null) },
            label = { Text("Challenges") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.accent,
                selectedTextColor = colors.accent,
                unselectedIconColor = colors.muted,
                unselectedTextColor = colors.muted,
                indicatorColor = colors.accent.copy(alpha = 0.1f)
            )
        )
        NavigationBarItem(
            selected = selectedTab == 3,
            onClick = onSettingsClick,
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = colors.accent,
                selectedTextColor = colors.accent,
                unselectedIconColor = colors.muted,
                unselectedTextColor = colors.muted,
                indicatorColor = colors.accent.copy(alpha = 0.1f)
            )
        )
    }
}

@Preview
@Composable
fun W2TCatalogPreview() {
    W2TTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            W2THeader(title = "Header Title", subtitle = "Header Subtitle", avatarText = "JD")
            
            W2TCard {
                W2TSectionTitle(text = "Section Title")
                Text("This is a card content")
                Spacer(modifier = Modifier.height(8.dp))
                W2TProgressBar(progress = 0.6f)
            }
            
            W2TMoodWidget(
                title = "Mood Title",
                description = "Mood Description goes here",
                buttonText = "Action",
                onButtonClick = {}
            )
            
            W2TAiInsightsCard(
                title = "AI Insight",
                description = "AI suggests you do this and that.",
                buttonText = "View Detail",
                onButtonClick = {}
            )
            
            W2TGoalItem(icon = "🎯", title = "Master Goal", progress = 0.4f, progressText = "40%")
            
            W2TActionItem(title = "Action Item", subtitle = "Subtext", time = "10:00 AM", checked = false, onCheckedChange = {})
            
            W2TChallengeCard(
                title = "Sample Challenge",
                goalTitle = "Linked Goal",
                description = "Challenge description",
                status = "Ongoing"
            ) {
                W2TSolutionItem(title = "Suggested Solution", source = "User")
                W2TStrategyCard(description = "AI Strategy", onButtonClick = {})
            }
        }
    }
}

@Preview
@Composable
fun W2TTreePreview() {
    W2TTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            W2TTreeNode(icon = "🌟", title = "Master Goal", type = "master") {
                W2TTreeNode(icon = "📍", title = "Milestone", type = "milestone") {
                    W2TTreeNode(icon = "✅", title = "Action Task", type = "action", completed = true)
                }
            }
        }
    }
}
