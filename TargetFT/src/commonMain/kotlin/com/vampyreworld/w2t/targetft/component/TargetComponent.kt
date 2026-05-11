package com.vampyreworld.w2t.targetft.component

import com.vampyreworld.w2t.core.arch.MviComponent
import com.vampyreworld.w2t.targetft.store.TargetStore

interface TargetComponent : MviComponent<TargetStore.State, TargetStore.Intent, TargetStore.Label>
