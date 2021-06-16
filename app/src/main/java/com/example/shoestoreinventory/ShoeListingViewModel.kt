package com.example.shoestoreinventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoeListingViewModel : ViewModel(){

    private lateinit var shoeDetails:MutableList<String>
    private val _shoeList =  MutableLiveData<MutableList<String>>()
    val shoeList:  LiveData<MutableList<String>>
        get() = _shoeList

    init{
        loadShoes()
    }
    //Function to Add Shoes
    fun addShoe(shoeName:String,shoeBrand:String,shoeSize: String,shoeDescription:String) {
        shoeDetails.add("ShoeName : "+shoeName+"\nShoeBrand : "+shoeBrand+"\nShoeSize : "+shoeSize+"\nShoeDescription : "+shoeDescription)
        _shoeList.setValue(shoeDetails)
        }
    //Function to initially load a set of Shoes
    fun loadShoes() {
        shoeDetails = mutableListOf("ShoeName : Sneakers \nShoeBrand : Nike \nShoeSize : 8 \nShoeDescription : Comfy Shoes",
            "ShoeName : Sport Shoes \nShoeBrand : Addidas \nShoeSize : 9 \nShoeDescription : Sporty Shoes")
        _shoeList.setValue(shoeDetails)
    }

    override fun onCleared() {
        super.onCleared()
    }
    }
