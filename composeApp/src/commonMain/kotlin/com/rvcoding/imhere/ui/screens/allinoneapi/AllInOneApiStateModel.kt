package com.rvcoding.imhere.ui.screens.allinoneapi

import com.rvcoding.imhere.domain.data.api.IHApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AllInOneApiStateModel(
    private val api: IHApi
) {
    private val _content = MutableStateFlow("Waiting for responses")
    val content: StateFlow<String> = _content.asStateFlow()

    fun requestConfiguration() {
        CoroutineScope(Dispatchers.IO).launch {
            _content.update { api.getConfiguration().toString() }
//            when (val res = api.getConfiguration()) {
//                is Error -> _content.update { res.error.toString() }
//                is Success -> _content.update { res.data.configuration.toString() }
//            }
        }
    }
}