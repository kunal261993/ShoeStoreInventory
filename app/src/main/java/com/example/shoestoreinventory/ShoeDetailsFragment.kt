package com.example.shoestoreinventory

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.shoestoreinventory.databinding.FragmentShoedetailsBinding

class ShoeDetailsFragment : Fragment() {
    private val viewModel: ShoeListingViewModel by activityViewModels()
    private val shoeDetails: ShoeDetails = ShoeDetails()
    private lateinit var binding: FragmentShoedetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoedetails, container, false)
        binding.shoeDetails = shoeDetails

        binding.addBackButton.setOnClickListener { v: View ->
            addShoeDetails(v)
        }
        binding.exitButton.setOnClickListener { v: View ->
            navigate(v)
        }
        return binding.root
    }
    //Function to validate all entries
    fun validation(shoeName: String,shoeBrand: String,shoeSize: String,shoeDescription: String): Boolean {
        return !TextUtils.isEmpty(shoeName) && !TextUtils.isEmpty(shoeBrand) && (!TextUtils.isEmpty(shoeSize) && shoeSize.toInt()>=(resources.getInteger(R.integer.seven)) && shoeSize.toInt()<=(resources.getInteger(R.integer.fifteen))) && !TextUtils.isEmpty(shoeDescription)
    }
    //Function to add shoe details in ShoeListingViewModel and navigation to ShoeListingFragment
    fun addShoeDetails(v:View)
    {
        binding.apply {

            if (validation(
                    shoeDetails?.shoeName!!,
                    shoeDetails?.shoeBrand!!,
                    shoeDetails?.shoeSize!!,
                    shoeDetails?.shoeDescription!!))
            {
                //Add values to ShoeListingViewModel
                viewModel.addShoe(
                    shoeDetails?.shoeName!!,
                    shoeDetails?.shoeBrand!!,
                    shoeDetails?.shoeSize!!,
                    shoeDetails?.shoeDescription!!)
                //Navigate to ShoeListingFragment
                navigate(v)
            } else {
                if (TextUtils.isEmpty(shoeDetails?.shoeName))
                    editTextShoeName.setError(getString(R.string.shoeNameError))
                if (TextUtils.isEmpty(shoeDetails?.shoeBrand))
                    editTextShoeBrand.setError(getString(R.string.shoeBrandError))
                if (TextUtils.isEmpty(shoeDetails?.shoeSize) || !(shoeDetails?.shoeSize!!.toInt() >= (resources.getInteger(R.integer.seven)) && shoeDetails?.shoeSize!!.toInt() <= (resources.getInteger(
                        R.integer.fifteen))))
                    editTextShoeSize.setError(getString(R.string.shoeSizeError))
                if (TextUtils.isEmpty(shoeDetails?.shoeDescription))
                    editTextShoeDescription.setError(getString(R.string.shoeDescriptionError))
            }
        }
    }
    //Function to navigate to ShoeListingFragment
    fun navigate(v:View)
    {
        v.findNavController()
            .navigate(ShoeDetailsFragmentDirections.actionShoeDetailsFragmentToShoeListingFragment())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.apply {
            outState.putString("SHOE_NAME",shoeDetails?.shoeName)
            outState.putString("SHOE_BRAND",shoeDetails?.shoeBrand)
            outState.putString("SHOE_SIZE",shoeDetails?.shoeSize)
            outState.putString("SHOE_DESCRIPTION",shoeDetails?.shoeDescription)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        binding.apply {
            if (savedInstanceState != null) {
                shoeDetails?.shoeName= savedInstanceState.getString("SHOE_NAME").toString()
                shoeDetails?.shoeBrand= savedInstanceState.getString("SHOE_BRAND").toString()
                shoeDetails?.shoeSize= savedInstanceState.getString("SHOE_SIZE").toString()
                shoeDetails?.shoeDescription= savedInstanceState.getString("SHOE_DESCRIPTION").toString()
            }
        }
    }
}