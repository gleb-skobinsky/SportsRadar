package org.sportsradar.sportsradarapp.auth

import org.sportsradar.sportsradarapp.auth.domain.AuthRepository
import org.sportsradar.sportsradarapp.auth.data.AuthRepositoryImpl
import org.sportsradar.sportsradarapp.auth.presentation.LoginViewModel
import org.sportsradar.sportsradarapp.auth.presentation.forgotPasswordScreen.ForgotPasswordViewModel
import org.sportsradar.sportsradarapp.auth.presentation.signupScreen.SignupViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    viewModel { LoginViewModel() }
    viewModel { SignupViewModel() }
    viewModel { ForgotPasswordViewModel() }
}