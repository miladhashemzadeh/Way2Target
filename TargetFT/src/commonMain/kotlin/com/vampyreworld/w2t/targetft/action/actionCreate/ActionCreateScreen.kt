package com.vampyreworld.w2t.targetft.action.actionCreate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.ActionSchedule
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2THeader
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ActionCreateScreen(
    component: ActionCreateContract.Component,
    padding: PaddingValues
) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val scrollState = rememberScrollState()
    
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var completionCriteria by remember { mutableStateOf("") }
    var energyCost by remember { mutableStateOf(50f) }
    var timeCost by remember { mutableStateOf(50f) }
    var moneyCost by remember { mutableStateOf(50f) }
    var selectedIcon by remember { mutableStateOf("🎯") }
    
    // Schedule state
    var scheduleType by remember { mutableStateOf("Once") }
    
    // FixedDate state
    var onceStartTime by remember { mutableStateOf<Long?>(null) }
    var deadline by remember { mutableStateOf<Long?>(null) }
    var onceReminderMins by remember { mutableStateOf("15") }
    
    // Recurring state
    var intervalMs by remember { mutableStateOf(86_400_000L) }
    var recurringStartTime by remember { mutableStateOf(LocalTime(9, 0)) }
    var selectedDays by remember { mutableStateOf(setOf<Int>()) }
    var recurrenceEndTime by remember { mutableStateOf<Long?>(null) }
    var notifyBeforeMins by remember { mutableStateOf("15") }
    
    // Habit state
    var habitStartTime by remember { mutableStateOf<Long?>(null) }
    var sessionDurationMins by remember { mutableStateOf("30") }
    var habitPeriodDays by remember { mutableStateOf("1") }
    var habitTotalWeeks by remember { mutableStateOf("4") }

    var showDatePickerFor by remember { mutableStateOf("") } // "", "once_start", "once_deadline", "recurring_end", "habit_start"
    var showTimePicker by remember { mutableStateOf(false) }
    
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(initialHour = 9, initialMinute = 0)

    if (showDatePickerFor.isNotEmpty()) {
        DatePickerDialog(
            onDismissRequest = { showDatePickerFor = "" },
            confirmButton = {
                TextButton(onClick = {
                    val selected = datePickerState.selectedDateMillis
                    when (showDatePickerFor) {
                        "once_start" -> onceStartTime = selected
                        "once_deadline" -> deadline = selected
                        "recurring_end" -> recurrenceEndTime = selected
                        "habit_start" -> habitStartTime = selected
                    }
                    showDatePickerFor = ""
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showDatePickerFor = "" }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    recurringStartTime = LocalTime(timePickerState.hour, timePickerState.minute)
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    recurringStartTime = LocalTime(timePickerState.hour, timePickerState.minute)
                    showTimePicker = false
                }) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel") }
            },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

    val icons = listOf("💻", "📈", "🧘‍♀️", "📚", "💰", "🚀", "🎨", "🏡", "🎯", "✨", "🏃")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Create Action Goal", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)) },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(ActionCreateContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

        W2TCard {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // ... (Existing Title, Description, Criteria fields remain the same)
                Column {
                    Text(
                        text = "Goal Title",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        placeholder = { Text("e.g., Study 30 min/day") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border,
                            unfocusedContainerColor = colors.bgLight.copy(alpha = 0.5f)
                        )
                    )
                }

                Column {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        placeholder = { Text("How will you do this?") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 4,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border,
                            unfocusedContainerColor = colors.bgLight.copy(alpha = 0.5f)
                        )
                    )
                }

                Column {
                    Text(
                        text = "Schedule Type",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("Once", "Recurring", "Habit").forEach { type ->
                            FilterChip(
                                selected = scheduleType == type,
                                onClick = { scheduleType = type },
                                label = { Text(type) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                if (scheduleType == "Once") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Task Timing", style = MaterialTheme.typography.labelLarge, color = colors.muted, fontWeight = FontWeight.SemiBold)
                        
                        // Start Time
                        OutlinedCard(
                            onClick = { showDatePickerFor = "once_start" },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.outlinedCardColors(containerColor = colors.bgLight.copy(alpha = 0.5f))
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = colors.accent)
                                Text(
                                    text = if (onceStartTime != null) {
                                        val date = Instant.fromEpochMilliseconds(onceStartTime!!)
                                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                                        "Starts: ${date.dayOfMonth}/${date.monthNumber}/${date.year}"
                                    } else "Start Date (Optional)",
                                    color = if (onceStartTime != null) MaterialTheme.colorScheme.onSurface else colors.muted
                                )
                            }
                        }

                        // Deadline
                        OutlinedCard(
                            onClick = { showDatePickerFor = "once_deadline" },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.outlinedCardColors(containerColor = colors.bgLight.copy(alpha = 0.5f))
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = colors.accent)
                                Text(
                                    text = if (deadline != null) {
                                        val date = Instant.fromEpochMilliseconds(deadline!!)
                                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                                        "Deadline: ${date.dayOfMonth}/${date.monthNumber}/${date.year}"
                                    } else "Deadline Date",
                                    color = if (deadline != null) MaterialTheme.colorScheme.onSurface else colors.muted
                                )
                            }
                        }

                        OutlinedTextField(
                            value = onceReminderMins,
                            onValueChange = { onceReminderMins = it },
                            label = { Text("Reminder (mins before)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                        )
                    }
                }

                if (scheduleType == "Recurring") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Repeat Settings", style = MaterialTheme.typography.labelLarge, color = colors.muted, fontWeight = FontWeight.SemiBold)
                        
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            RadioButton(selected = intervalMs == 86_400_000L, onClick = { intervalMs = 86_400_000L })
                            Text("Daily", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.width(8.dp))
                            RadioButton(selected = intervalMs == 604_800_000L, onClick = { intervalMs = 604_800_000L })
                            Text("Weekly", style = MaterialTheme.typography.bodyMedium)
                        }

                        // Time Picker Trigger
                        OutlinedCard(
                            onClick = { showTimePicker = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Schedule, contentDescription = null, tint = colors.accent)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("Starting at: ${recurringStartTime.hour.toString().padStart(2, '0')}:${recurringStartTime.minute.toString().padStart(2, '0')}")
                            }
                        }

                        // Recurrence End Date
                        OutlinedCard(
                            onClick = { showDatePickerFor = "recurring_end" },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = colors.accent)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = if (recurrenceEndTime != null) {
                                        val date = Instant.fromEpochMilliseconds(recurrenceEndTime!!)
                                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                                        "Ends on: ${date.dayOfMonth}/${date.monthNumber}/${date.year}"
                                    } else "End Date (Optional)",
                                    color = if (recurrenceEndTime != null) MaterialTheme.colorScheme.onSurface else colors.muted
                                )
                            }
                        }

                        OutlinedTextField(
                            value = notifyBeforeMins,
                            onValueChange = { notifyBeforeMins = it },
                            label = { Text("Notify me (mins before)") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                        )
                        
                        // Day multi-select
                        Text("Days of Week", style = MaterialTheme.typography.bodySmall, color = colors.muted)
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                            days.forEachIndexed { index, day ->
                                val dayNum = index + 1
                                FilterChip(
                                    selected = selectedDays.contains(dayNum),
                                    onClick = {
                                        selectedDays = if (selectedDays.contains(dayNum)) selectedDays - dayNum else selectedDays + dayNum
                                    },
                                    label = { Text(day, fontSize = 10.sp) }
                                )
                            }
                        }
                    }
                }

                if (scheduleType == "Habit") {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text("Habit Strategy", style = MaterialTheme.typography.labelLarge, color = colors.muted, fontWeight = FontWeight.SemiBold)
                        
                        // Habit Start Date
                        OutlinedCard(
                            onClick = { showDatePickerFor = "habit_start" },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = colors.accent)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = if (habitStartTime != null) {
                                        val date = Instant.fromEpochMilliseconds(habitStartTime!!)
                                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                                        "Starts on: ${date.dayOfMonth}/${date.monthNumber}/${date.year}"
                                    } else "Start Date (Immediately)",
                                    color = if (habitStartTime != null) MaterialTheme.colorScheme.onSurface else colors.muted
                                )
                            }
                        }

                        OutlinedTextField(
                            value = sessionDurationMins,
                            onValueChange = { sessionDurationMins = it },
                            label = { Text("Session Duration (mins)") },
                            placeholder = { Text("e.g. 30") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = habitPeriodDays,
                            onValueChange = { habitPeriodDays = it },
                            label = { Text("Frequency (every X days)") },
                            placeholder = { Text("1 for daily, 7 for weekly") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                        )

                        OutlinedTextField(
                            value = habitTotalWeeks,
                            onValueChange = { habitTotalWeeks = it },
                            label = { Text("Commitment (Total weeks)") },
                            placeholder = { Text("e.g. 12") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                        )
                    }
                }

                Column {
                    Text(
                        text = "Completion Criteria",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = completionCriteria,
                        onValueChange = { completionCriteria = it },
                        placeholder = { Text("What does 'done' look like?") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.accent,
                            unfocusedBorderColor = colors.border,
                            unfocusedContainerColor = colors.bgLight.copy(alpha = 0.5f)
                        )
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Estimated Costs (0-100)",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    CostSlider(label = "Energy", value = energyCost, onValueChange = { energyCost = it }, colors = colors)
                    CostSlider(label = "Time", value = timeCost, onValueChange = { timeCost = it }, colors = colors)
                    CostSlider(label = "Money", value = moneyCost, onValueChange = { moneyCost = it }, colors = colors)
                }

                Column {
                    Text(
                        text = "Select an Icon",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.muted,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        icons.forEach { icon ->
                            val isSelected = selectedIcon == icon
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isSelected) colors.accent.copy(alpha = 0.1f) else colors.bgLight)
                                    .border(2.dp, if (isSelected) colors.accent else colors.border, RoundedCornerShape(14.dp))
                                    .clickable { selectedIcon = icon },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = icon, fontSize = 24.sp)
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = { 
                if (title.isNotBlank()) {
                    val finalSchedule = when(scheduleType) {
                        "Once" -> ActionSchedule.FixedDate(
                            startTime = onceStartTime,
                            deadline = deadline,
                            reminderMs = onceReminderMins.toLongOrNull()?.let { it * 60 * 1000 }
                        )
                        "Recurring" -> {
                            val now = kotlinx.datetime.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                            val startDateTime = LocalDateTime(now.year, now.month, now.dayOfMonth, recurringStartTime.hour, recurringStartTime.minute)
                            val startMillis = startDateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                            
                            ActionSchedule.Recurring(
                                startTime = startMillis, 
                                endTime = recurrenceEndTime,
                                intervalMs = intervalMs,
                                daysOfWeek = selectedDays.toList(),
                                notifyBeforeMs = notifyBeforeMins.toLongOrNull()?.let { it * 60 * 1000 }
                            )
                        }
                        "Habit" -> {
                            val sessionMs = sessionDurationMins.toLongOrNull()?.let { it * 60 * 1000 } ?: 0L
                            val periodMs = habitPeriodDays.toLongOrNull()?.let { it * 24 * 60 * 60 * 1000 } ?: 86_400_000L
                            val totalDurationMs = habitTotalWeeks.toLongOrNull()?.let { it * 7 * 24 * 60 * 60 * 1000 }
                            
                            val startMillis = habitStartTime?.let {
                                val date = Instant.fromEpochMilliseconds(it).toLocalDateTime(TimeZone.currentSystemDefault()).date
                                val dateTime = LocalDateTime(date.year, date.month, date.dayOfMonth, recurringStartTime.hour, recurringStartTime.minute)
                                dateTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
                            }

                            ActionSchedule.HabitBased(
                                sessionDurationMs = sessionMs,
                                periodMs = periodMs,
                                totalDurationMs = totalDurationMs,
                                startTime = startMillis
                            )
                        }
                        else -> null
                    }
                    component.onIntent(ActionCreateContract.Intent.OnSaveGoal(
                        title = title,
                        description = description,
                        completionCriteria = completionCriteria,
                        energyCost = energyCost.toInt(),
                        timeCost = timeCost.toInt(),
                        moneyCost = moneyCost.toInt(),
                        schedule = finalSchedule
                    ))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = title.isNotBlank(),
            colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text("Create Action Goal", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}
}

@Composable
private fun CostSlider(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    colors: com.vampyreworld.w2t.sharedui.theme.color.AppColorScheme
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = colors.muted)
            Text(text = value.toInt().toString(), style = MaterialTheme.typography.bodySmall, color = colors.accent)
        }
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = colors.accent,
                activeTrackColor = colors.accent,
                inactiveTrackColor = colors.border
            )
        )
    }
}
