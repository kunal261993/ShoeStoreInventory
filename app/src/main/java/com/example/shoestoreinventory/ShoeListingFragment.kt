package com.example.shoestoreinventory

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.core.widget.TextViewCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.shoestoreinventory.databinding.FragmentShoelistingBinding

class ShoeListingFragment : Fragment() {
    private val viewModel: ShoeListingViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentShoelistingBinding  = DataBindingUtil.inflate(
            inflater, R.layout.fragment_shoelisting, container, false)
        setHasOptionsMenu(true)
        binding.setLifecycleOwner(this)
        viewModel.shoeList.observe(viewLifecycleOwner, { shoeList ->
            //Updating UI
            for (i in 0..shoeList.size-1) {
                val tv = TextView(activity)
                tv.text = shoeList[i]
                TextViewCompat.setTextAppearance(tv, R.style.scrollViewTextStyle)
                tv.setPadding(resources.getInteger(R.integer.twenty))
                tv.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                binding.scrollViewLinearLayout.addView(tv)
                if(i!=shoeList.size-1) {
                    val v = View(activity)
                    v.layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        resources.getInteger(R.integer.five)
                    )
                    v.setBackgroundColor(resources.getColor(R.color.silver))
                    binding.scrollViewLinearLayout.addView(v)
                }
            }
        })
        binding.addShoesButton.setOnClickListener { v: View ->
            v.findNavController().navigate(ShoeListingFragmentDirections.actionShoeListingFragmentToShoeDetailsFragment())
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

}