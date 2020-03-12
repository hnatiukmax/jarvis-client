package com.bugmakers.jarvistime.presentation.pages.introduction

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.data.auth.getFacebookCallback
import com.bugmakers.jarvistime.databinding.ActivityIntroductionBinding
import com.bugmakers.jarvistime.presentation.application.JarvisApplication
import com.bugmakers.jarvistime.presentation.extensions.goTo
import com.bugmakers.jarvistime.presentation.extensions.showToast
import com.bugmakers.jarvistime.presentation.pages.login.LogInActivity
import com.bugmakers.jarvistime.presentation.view.introductionslider.IntroductionSliderAdapter
import com.bugmakers.jarvistime.presentation.view.introductionslider.IntroductionSliderItem
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookAuthorizationException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class IntroductionActivity : AppCompatActivity(), KodeinAware {

    override val kodein: Kodein by lazy {
        (application as JarvisApplication).kodein
    }

    private val viewModel by instance<IntroductionActivityViewModel>()

    private lateinit var binding: ActivityIntroductionBinding

    private lateinit var signInClient: GoogleSignInClient
    private lateinit var facebookCallbackManager: CallbackManager

    companion object {
        private const val REQUEST_CODE_GOOGLE_SIGN_IN = 1101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_introduction)
        binding.apply {
            lifecycleOwner = this@IntroductionActivity
            viewModel = this@IntroductionActivity.viewModel.apply {
                observeViewModel()
            }

            continueWithEmail.setOnClickListener {
                LoginManager.getInstance().apply {
                    logOut()
                }
//                goTo(LogInActivity::class.java)
            }
        }

        initGoogleClient()
        initFacebookClient()

//        initIntroductionSlider()
        viewModel.observeViewModel()
    }

    private fun initIntroductionSlider() {
        val introductionSliderItems = getIntroductionResources() ?: return

        val itemCount = introductionSliderItems.size

        val introductionSliderAdapter = IntroductionSliderAdapter(introductionSliderItems)
        val onIntroductionPageChangeCallback = OnIntroductionPageChangeCallback(itemCount)

        binding.apply {
            viewPager.let {
                it.adapter = introductionSliderAdapter
                it.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                it.offscreenPageLimit = 2
                it.setCurrentItem((introductionSliderAdapter.itemCount / 2) + 1, false)
                it.registerOnPageChangeCallback(onIntroductionPageChangeCallback)
            }
            pageIndicator.count = itemCount
        }
    }

    /**
     * This method return list of viewPager item.
     */
    @SuppressLint("Recycle")
    private fun getIntroductionResources(): List<IntroductionSliderItem>? {
        val itemLogoIds = resources.obtainTypedArray(R.array.introduction_item_logo_ids)
        val firstLabelIds = resources.obtainTypedArray(R.array.introduction_first_label_ids)
        val secondLabelIds = resources.obtainTypedArray(R.array.introduction_second_label_ids)

        if (itemLogoIds.length() != firstLabelIds.length() ||
            itemLogoIds.length() != secondLabelIds.length() ||
            firstLabelIds.length() != secondLabelIds.length()
        ) {
            return null
        }

        return List(itemLogoIds.length()) {
            IntroductionSliderItem(
                itemLogoResId = itemLogoIds.getResourceId(it, 0),
                introductionLabelFirstId = firstLabelIds.getResourceId(it, 0),
                introductionLabelSecondId = secondLabelIds.getResourceId(it, 0)
            )
        }
    }

    /**
     * Control state of pageIndicator and introductionBottomBackground.
     */
    private inner class OnIntroductionPageChangeCallback(
        val itemCount: Int
    ) : ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            binding.pageIndicator.selection = position % itemCount
        }
    }

    private fun IntroductionActivityViewModel.observeViewModel() {
        onEmailAuth.observe(this@IntroductionActivity, Observer {
            goTo(LogInActivity::class.java)
        })
        onGoogleAuth.observe(this@IntroductionActivity, Observer {
            startActivityForResult(signInClient.signInIntent, REQUEST_CODE_GOOGLE_SIGN_IN)
        })
        onFacebookAuth.observe(this@IntroductionActivity, Observer {
            LoginManager.getInstance()
                .logInWithReadPermissions(this@IntroductionActivity, listOf("public_profile"))
        })
    }

    private fun initGoogleClient() {
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestServerAuthCode(getString(R.string.server_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        signInClient = GoogleSignIn.getClient(this, signInOptions)
    }

    private fun initFacebookClient() {
        facebookCallbackManager = CallbackManager.Factory.create()
        val callback = getFacebookCallback<LoginResult>(
            doOnSuccess = {
                val accessToken = AccessToken.getCurrentAccessToken()
                if (accessToken != null && !accessToken.isExpired) {
                    showToast("Success Login")
                }
            },
            doOnError = {
                if (it is FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().apply {
                            logOut()
                            logInWithReadPermissions(
                                this@IntroductionActivity,
                                listOf("public_profile")
                            )
                        }
                    }
                }
            }
        )

        LoginManager.getInstance().registerCallback(facebookCallbackManager, callback)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_GOOGLE_SIGN_IN && resultCode == Activity.RESULT_OK) {
            handleGoogleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.idToken?.let {
                showToast("Success Google")
                //viewModel.onSocialNetworkTokenFetched(it, AuthenticationType.SocialNetwork.GOOGLE)
            } ?: showToast("Error Google")

        } catch (e: ApiException) {
            showToast("Error Google")
            //startErrorDialog(AppException.SomethingBadHappenedException(cause = e))
        }
    }

    private fun signOutFromSocialNetwork() {
        AccessToken.getCurrentAccessToken()?.let {
            if (!it.isExpired) {
                LoginManager.getInstance().logOut()
            }
        }
        signInClient.signOut()
    }
}
