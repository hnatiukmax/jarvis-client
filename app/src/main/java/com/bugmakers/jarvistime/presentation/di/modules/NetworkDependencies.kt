package com.bugmakers.jarvistime.presentation.di.modules

import android.content.Context
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.data.auth.getGoogleSignInClient
import com.bugmakers.jarvistime.data.network.authorization.AuthorizationInterceptor
import com.bugmakers.jarvistime.data.network.client.*
import com.bugmakers.jarvistime.data.network.mapper.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

internal fun networkDependencies(context: Context) = Kodein.Module("networkDependencies") {
    bind<Moshi>() with singleton { MoshiFactory().newInstance() }
    bind<Deserializer>() with singleton { MoshiDesirializer(instance()) }
    bind<Serializer>() with provider { MoshiSerializer(instance()) }

    bind<AuthorizationInterceptor>() with singleton {
        AuthorizationInterceptor(
            instance()
        )
    }
    bind<OkHttpClient>() with singleton { getOkHttpClient(instance()) }
    bind<RestClient>() with singleton {
        RestClient(instance(AppConstant.BASE_URL), instance(), instance())
    }

    bind<GoogleSignInClient>() with singleton {
        getGoogleSignInClient(context, R.string.server_client_id)
    }
}