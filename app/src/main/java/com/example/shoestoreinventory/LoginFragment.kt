package com.example.shoestoreinventory

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.shoestoreinventory.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val userDetails: UserDetails = UserDetails()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false)

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        binding.userDetails=userDetails
        binding.loginButton.setOnClickListener{ v: View ->
            validateAndNavigate(v)
        }
        binding.existingAccountLogin.setOnClickListener{ v: View ->
            clearData()
            navigate(v)
        }
        return binding.root
    }
    //Function to validate Email
    fun isValidEmail(emailId: String): Boolean {
        return !TextUtils.isEmpty(emailId) && Patterns.EMAIL_ADDRESS.matcher(emailId).matches()
    }
    //Function to validate Password
    fun isValidPassword(password: String): Boolean {
        return !TextUtils.isEmpty(password) && password.length>(resources.getInteger(R.integer.seven))
    }
    //Function to validate Email and Password and navigate to WelcomeFragment
    fun validateAndNavigate(v:View)
    {
        binding.apply {
            if (isValidEmail(userDetails?.emailId!!) && isValidPassword(userDetails?.password!!)) {
                clearData()
                navigate(v)
            } else {
                if (!isValidEmail(userDetails?.emailId!!))
                    editTextEmailAddress.setError(getString(R.string.emailError))
                if (!isValidPassword(userDetails?.password!!))
                    editTextPassword.setError(getString(R.string.passwordError))
            }
        }
    }
    ////Function to navigate to WelcomeFragment
    fun navigate(v:View)
    {
        v.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
    }
    fun clearData()
    {
        binding.apply {
            editTextEmailAddress.text.clear()
            editTextPassword.text.clear()
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (::binding.isInitialized) {
            binding.apply {
                outState.putString("EMAIL_ID", userDetails?.emailId)
                outState.putString("PASSWORD", userDetails?.password!!)
            }
    }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.apply {
            if (savedInstanceState != null) {
                if(!savedInstanceState.getString("EMAIL_ID").toString().equals("null"))
                userDetails?.emailId= savedInstanceState.getString("EMAIL_ID").toString()
                if(!savedInstanceState.getString("PASSWORD").toString().equals("null"))
                userDetails?.password= savedInstanceState.getString("PASSWORD").toString()
            }
        }
    }
}