/*
 * Copyright 2024 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.jetcaster.tv.ui.library

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.tv.material3.Button
import androidx.tv.material3.ExperimentalTvMaterial3Api
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text
import com.example.jetcaster.tv.R
import com.example.jetcaster.tv.ui.JetcasterAppDefaults
import com.example.jetcaster.tv.ui.component.Catalog
import com.example.jetcaster.tv.ui.component.Loading

@Composable
fun LibraryScreen(
    modifier: Modifier = Modifier,
    navigateToDiscover: () -> Unit,
    libraryScreenViewModel: LibraryScreenViewModel = viewModel()
) {
    val uiState by libraryScreenViewModel.uiState.collectAsState()
    when (val s = uiState) {
        LibraryScreenUiState.Loading -> Loading(modifier = modifier)
        LibraryScreenUiState.NoSubscribedPodcast -> {
            NavigateToDiscover(onNavigationRequested = navigateToDiscover, modifier = modifier)
        }

        is LibraryScreenUiState.Ready -> Catalog(
            podcastList = s.subscribedPodcastList,
            latestEpisodeList = s.latestEpisodeList,
            onPodcastSelected = libraryScreenViewModel::unsubscribe,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
private fun NavigateToDiscover(
    onNavigationRequested: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column {
            Text(
                text = stringResource(id = R.string.display_no_subscribed_podcast),
                style = MaterialTheme.typography.displayMedium
            )
            Text(text = stringResource(id = R.string.message_no_subscribed_podcast))
            Button(
                onClick = onNavigationRequested,
                modifier = Modifier.padding(top = JetcasterAppDefaults.gapSettings.catalogItemGap)
            ) {
                Text(text = stringResource(id = R.string.label_navigate_to_discover))
            }
        }
    }
}