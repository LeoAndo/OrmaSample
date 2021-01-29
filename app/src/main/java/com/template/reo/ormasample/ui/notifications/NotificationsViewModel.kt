package com.template.reo.ormasample.ui.notifications

import androidx.lifecycle.ViewModel
import com.template.reo.ormasample.domain.TodoService
import javax.inject.Inject

class NotificationsViewModel @Inject constructor(
    private val todoService: TodoService
) : ViewModel() {
}