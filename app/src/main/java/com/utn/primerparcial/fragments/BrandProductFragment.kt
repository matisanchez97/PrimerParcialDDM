package com.utn.primerparcial.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utn.primerparcial.R
import com.utn.primerparcial.adapters.ShoppingListAdapter
import com.utn.primerparcial.database.appDatabase
import com.utn.primerparcial.database.productDao
import com.utn.primerparcial.entities.Product


/**
 * A simple [Fragment] subclass.
 * Use the [BrandProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BrandProductFragment(productId:Int) : Fragment() {

    lateinit var v: View
    lateinit var recyclerBrandProducts: RecyclerView
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var shoppingListAdapter: ShoppingListAdapter

    private var db: appDatabase? = null
    private var productDao: productDao? = null

    var productId = productId
    var selectedProduct: Product? = null
    var brandProductList: MutableList<Product>? = ArrayList<Product>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_brand_product, container, false)
        recyclerBrandProducts = v.findViewById(R.id.recyclerBrandProducts)
        return v
    }

    override fun onStart() {
        super.onStart()
        db = appDatabase.getAppDataBase(v.context)
        productDao = db?.productDao()
        selectedProduct = productDao?.loadProductById(productId)
        brandProductList = productDao?.loadProductsByBrand(selectedProduct?.brand)

        recyclerBrandProducts.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerBrandProducts.layoutManager = linearLayoutManager
        if(!(brandProductList.isNullOrEmpty())){
            shoppingListAdapter = ShoppingListAdapter(brandProductList!!,{position,cardView -> OnItemClick(position,cardView)},{position, cardView -> OnItemLongClick(position,cardView)})
            recyclerBrandProducts.adapter = shoppingListAdapter
        }
    }
    fun OnItemClick(position: Int,cardView: CardView){
        selectedProduct = brandProductList!![position]
    }
    fun OnItemLongClick(position: Int, cardView: CardView){

    }




}