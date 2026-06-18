package com.vampyreworld.w2t.profileft.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.vampyreworld.w2t.domain.data.model.MentalType
import com.vampyreworld.w2t.domain.data.model.Worldview
import com.vampyreworld.w2t.profileft.ProfileContract
import com.vampyreworld.w2t.sharedui.catalog.W2TCard
import com.vampyreworld.w2t.sharedui.catalog.W2THeader
import com.vampyreworld.w2t.sharedui.theme.color.LocalAppColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(component: ProfileContract.Component) {
    val state by component.state.subscribeAsState()
    val colors = LocalAppColorScheme.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("پروفایل کاربر") },
                navigationIcon = {
                    IconButton(onClick = { component.onIntent(ProfileContract.Intent.OnBackClicked) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "برگشت")
                    }
                },
                actions = {
                    if (!state.isEditMode) {
                        IconButton(onClick = { component.onIntent(ProfileContract.Intent.SetEditMode(true)) }) {
                            Icon(Icons.Default.Edit, contentDescription = "ویرایش")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = colors.accent)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(scrollState)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                W2THeader(
                    title = state.profile.name.ifEmpty { "کاربر عزیز" },
                    subtitle = "اطلاعات شخصی برای شخصی‌سازی تجربه شما",
                    avatarText = state.profile.name.take(1).uppercase().ifEmpty { "U" },
                    avatarUrl = state.profile.avatarUrl
                )

                if (state.isEditMode) {
                    ProfileEditView(component, state)
                } else {
                    ProfileReadView(state)
                }

                if (state.isEditMode) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { component.onIntent(ProfileContract.Intent.SetEditMode(false)) },
                            modifier = Modifier.weight(1f).height(56.dp),
                            shape = RoundedCornerShape(28.dp)
                        ) {
                            Text("انصراف")
                        }

                        Button(
                            onClick = { component.onIntent(ProfileContract.Intent.SaveProfile) },
                            modifier = Modifier.weight(1f).height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                            shape = RoundedCornerShape(28.dp),
                            enabled = !state.isSaving
                        ) {
                            if (state.isSaving) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(24.dp))
                            } else {
                                Text("ذخیره تغییرات", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                } else {
                    Button(
                        onClick = { component.onIntent(ProfileContract.Intent.SetEditMode(true)) },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colors.accent),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("ویرایش پروفایل", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileReadView(state: ProfileContract.State) {
    val colors = LocalAppColorScheme.current
    W2TCard {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            InfoRow(label = "نام", value = state.profile.name.ifEmpty { "ثبت نشده" }, colors = colors)
            InfoRow(label = "سن", value = state.profile.age?.toString() ?: "ثبت نشده", colors = colors)
            InfoRow(label = "تایپ روحی", value = state.profile.mentalType.displayName, colors = colors)
            InfoRow(label = "نوع دیدگاه", value = state.profile.worldview.displayName, colors = colors)
        }
    }
}

@Composable
private fun InfoRow(label: String, value: String, colors: com.vampyreworld.w2t.sharedui.theme.color.AppColorScheme) {
    Column {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = colors.muted)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = colors.border.copy(alpha = 0.5f))
    }
}

@Composable
private fun ProfileEditView(component: ProfileContract.Component, state: ProfileContract.State) {
    val colors = LocalAppColorScheme.current
    W2TCard {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ProfileTextField(
                label = "نام",
                value = state.profile.name,
                onValueChange = { component.onIntent(ProfileContract.Intent.UpdateProfile(state.profile.copy(name = it))) },
                colors = colors
            )

            ProfileTextField(
                label = "آدرس تصویر پروفایل (URL)",
                value = state.profile.avatarUrl ?: "",
                onValueChange = { component.onIntent(ProfileContract.Intent.UpdateProfile(state.profile.copy(avatarUrl = it))) },
                colors = colors
            )

            ProfileTextField(
                label = "سن",
                value = state.profile.age?.toString() ?: "",
                onValueChange = { 
                    val age = it.toIntOrNull()
                    component.onIntent(ProfileContract.Intent.UpdateProfile(state.profile.copy(age = age)))
                },
                colors = colors
            )

            EnumSelector(
                label = "تایپ روحی",
                options = MentalType.entries,
                selectedOption = state.profile.mentalType,
                onOptionSelected = { component.onIntent(ProfileContract.Intent.UpdateProfile(state.profile.copy(mentalType = it))) },
                displayName = { it.displayName },
                colors = colors
            )

            EnumSelector(
                label = "نوع دیدگاه",
                options = Worldview.entries,
                selectedOption = state.profile.worldview,
                onOptionSelected = { component.onIntent(ProfileContract.Intent.UpdateProfile(state.profile.copy(worldview = it))) },
                displayName = { it.displayName },
                colors = colors
            )
        }
    }
}

@Composable
private fun <T> EnumSelector(
    label: String,
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    displayName: (T) -> String,
    colors: com.vampyreworld.w2t.sharedui.theme.color.AppColorScheme
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = colors.muted,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.bgLight.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                .clickable { expanded = true }
                .padding(16.dp)
        ) {
            Text(text = displayName(selectedOption), style = MaterialTheme.typography.bodyLarge)
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(displayName(option)) },
                        onClick = {
                            onOptionSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    colors: com.vampyreworld.w2t.sharedui.theme.color.AppColorScheme
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = colors.muted,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.accent,
                unfocusedBorderColor = colors.border,
                unfocusedContainerColor = colors.bgLight.copy(alpha = 0.5f)
            )
        )
    }
}
