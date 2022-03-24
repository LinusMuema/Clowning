package com.moose.clowning.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.moose.clowning.GITHUB_CLIENT_ID
import com.moose.clowning.GITHUB_CLIENT_SECRET
import com.moose.clowning.github.GithubActivity
import com.moose.clowning.ui.theme.ClowningTheme
import dagger.hilt.android.AndroidEntryPoint
import net.openid.appauth.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var service: AuthorizationService
    private val viewmodel: MainViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        service = AuthorizationService(this)

        setContent {
            ClowningTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = { githubAuth() }) {
                            Text(text = "Login with Github")
                        }
                    }
                }
            }
        }
    }

    private val launcher = registerForActivityResult(StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
            val ex = AuthorizationException.fromIntent(it.data!!)
            val response = AuthorizationResponse.fromIntent(it.data!!)

            if (ex != null) {
                // handle the exception
                Toast.makeText(this, ex.message, Toast.LENGTH_LONG).show()
            } else {
                // get the code and exchange it for the token
                val client = ClientSecretBasic(GITHUB_CLIENT_SECRET)
                val request = response!!.createTokenExchangeRequest()
                service.performTokenRequest(request, client) { res, exception ->
                    if (exception != null) {
                        // handle the exception
                        Toast.makeText(this, exception.message, Toast.LENGTH_LONG).show()
                    } else {
                        // get the token
                        val token = res!!.accessToken!!
                        viewmodel.setToken(token)

                        // move to the github screen
                        val intent = Intent(this, GithubActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private fun githubAuth() {
        // 1. configure the authorisation service
        val auth = Uri.parse("https://github.com/login/oauth/authorize")
        val token = Uri.parse("https://github.com/login/oauth/access_token")
        val config = AuthorizationServiceConfiguration(auth, token)

        // 2. create the request to obtain the code
        val redirect = Uri.parse("clowning://moose.ac")
        val request = AuthorizationRequest
            .Builder(config, GITHUB_CLIENT_ID, ResponseTypeValues.CODE, redirect)
            .setScope("repo user")
            .build()

        // 3. start the auth flow
        val intent = service.getAuthorizationRequestIntent(request)
        launcher.launch(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        service.dispose()
    }
}
