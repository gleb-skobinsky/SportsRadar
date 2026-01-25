package org.sportsradar.sportsradarapp.auth

import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.sportsradar.sportsradarapp.auth.data.AuthRepositoryImpl
import org.sportsradar.sportsradarapp.auth.domain.AuthRepository
import org.sportsradar.sportsradarapp.auth.presentation.forgotPasswordScreen.ForgotPasswordViewModel
import org.sportsradar.sportsradarapp.auth.presentation.loginScreen.LoginViewModel
import org.sportsradar.sportsradarapp.auth.presentation.signupScreen.SignupViewModel
import org.sportsradar.sportsradarapp.di.AUTH_API_CLIENT_NAME

val authModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get(named(AUTH_API_CLIENT_NAME)), get())
    }
    viewModel { LoginViewModel() }
    viewModel { SignupViewModel() }
    viewModel { ForgotPasswordViewModel(getOrNull()) }
}