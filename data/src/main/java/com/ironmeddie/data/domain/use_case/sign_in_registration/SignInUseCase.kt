package com.ironmeddie.data.domain.use_case.sign_in_registration

import com.ironmeddie.data.domain.repository.MyRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(private val repository: MyRepository) {
    suspend operator fun invoke(email:String, password: String) = repository.signIn(email = email, password = password)
}