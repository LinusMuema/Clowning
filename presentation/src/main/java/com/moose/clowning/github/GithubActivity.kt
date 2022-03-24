package com.moose.clowning.github

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moose.clowning.ui.theme.ClowningTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubActivity : ComponentActivity() {
    private val viewmodel: GithubViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClowningTheme {
                Surface {
                    GithubProfile()
                }
            }
        }
    }

    @Composable
    private fun GithubProfile() {
        val profile by remember { viewmodel.user }
        profile?.let {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    model = it.image,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Name: ${it.name}")

                Row {
                    Text(text = "Repos: ${it.repos}")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Gists: ${it.gists}")
                }

                Row {
                    Text(text = "Followers: ${it.followers}")
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Following: ${it.following}")
                }
            }
        }
    }
}