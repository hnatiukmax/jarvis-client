package com.bugmakers.jarvistime.presentation.di.modules

import android.content.Context
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.data.auth.getGoogleSignInClient
import com.bugmakers.jarvistime.data.network.client.*
import com.bugmakers.jarvistime.data.network.mapper.*
import com.bugmakers.jarvistime.data.service.AuthService
import com.bugmakers.jarvistime.data.service.TaskService
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.squareup.moshi.Moshi
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal fun networkDependencies(context: Context) = Kodein.Module("networkDependencies") {
    bind<AuthenticationInterceptor>() with singleton { AuthenticationInterceptor() }
    bind<Authenticator>() with singleton { instance<AuthenticationInterceptor>() }

    bind<Moshi>() with singleton { MoshiFactory().newInstance() }
    bind<Deserializer>() with singleton { MoshiDesirializer(instance()) }
    bind<Serializer>() with provider { MoshiSerializer(instance()) }

    bind<RestClient>() with singleton {
        RestClient(instance(AppConstant.BASE_URL), instance(), instance())
    }

    bind<AuthService>() with singleton { instance<RestClient>().authService }
    bind<TaskService>() with singleton { instance<RestClient>().taskService }
    bind<GoogleSignInClient>() with singleton {
        getGoogleSignInClient(context, R.string.server_client_id)
    }
}