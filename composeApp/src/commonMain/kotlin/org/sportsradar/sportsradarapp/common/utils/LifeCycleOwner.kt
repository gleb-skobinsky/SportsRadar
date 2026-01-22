package org.sportsradar.sportsradarapp.common.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

val LifecycleOwner?.isCreated: Boolean
    get() = this?.lifecycle?.currentState == Lifecycle.State.CREATED

val LifecycleOwner?.isResumed: Boolean
    get() = this?.lifecycle?.currentState == Lifecycle.State.RESUMED

val LifecycleOwner?.isStarted: Boolean
    get() = this?.lifecycle?.currentState == Lifecycle.State.STARTED