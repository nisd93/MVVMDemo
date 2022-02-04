package com.mvvmdemo.view.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvmdemo.R
import com.mvvmdemo.model.request.MerchantDataRequest
import com.mvvmdemo.model.response.PreminumMerchant
import com.mvvmdemo.network.Resource
import com.mvvmdemo.view.adapter.MerchantListAdapter
import com.mvvmdemo.view.viewmodel.MerchantListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    // declaration of view model
    private val viewModel by viewModel<MerchantListViewModel>()
    private var merchantListAdapter: MerchantListAdapter? = null
    private var merchantList = ArrayList<PreminumMerchant>()
    private var dummyList = ArrayList<PreminumMerchant>()
    private var categoryList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAdapter()
        loadData()
        setDropDownList()

    }

    /**
     * get data from API
     */
    private fun loadData() {
        try {
            // observing data from API and update UI
            viewModel.merchantListViewModel.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        try {
                            hideProgress()
                            setData(it)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    is Resource.Error -> {
                        hideProgress()
                        showNoData()
                        tvNoDataFound.text="Error:" + it.throwable
                    }
                    is Resource.Loading -> {
                        showProgress()
                    }
                }
            }

            // request for data from API
            val request = MerchantDataRequest()
            request.companyID = 1
            request.menuID = 0
            request.subCategoryID = 0
            request.imageSize = "small"
            request.searchMerchant = ""
            viewModel.getMerchantList(request)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Initialize drop down list menu
     */
    private fun setDropDownList() {
        try {
            val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_menu, categoryList)
            autoCompleteTextView.setAdapter(arrayAdapter)
            autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
                filterList(i)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Filter list by SubCategoryName
     */
    private fun filterList(position:Int)
    {
        try {
            tvHeaderCategoryName.text=categoryList.get(position)
            if (categoryList.get(position)!="All") {
                val filterList=merchantList.filter { s -> s.subCategoryName==categoryList.get(position) }
                if (filterList.isNotEmpty()) {
                    showDataView()
                    merchantList.clear()
                    merchantList.addAll(filterList)
                    merchantListAdapter!!.notifyDataSetChanged()
                } else {
                    showNoData()
                }
            } else {
                showDataView()
                merchantList.clear()
                merchantList.addAll(dummyList)
                merchantListAdapter!!.notifyDataSetChanged()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Initialize adapter for list
     */
    private fun setAdapter() {
        try {
            merchantListAdapter =
                MerchantListAdapter(this@MainActivity, merchantList)
            rvMerchantList.adapter = merchantListAdapter
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    /**
     * update list from API
     */
    private fun setData(it: Resource.Success<List<PreminumMerchant>>) {
        try {

            setDropDownList()

            if (it.data.isNotEmpty()) {

                showDataView()

                if (merchantList.isNotEmpty()) {
                    merchantList.clear()
                }
                if (dummyList.isNotEmpty()) {
                    dummyList.clear()
                }
                if (categoryList.isNotEmpty()) {
                    categoryList.clear()
                }

                merchantList.addAll(it.data)
                dummyList.addAll(merchantList)

                // add default string to dropdown
                categoryList.add("All")
                // get dropdown list
                for (i in it.data.indices) {
                    categoryList.add(it.data[i].subCategoryName!!)
                }

                merchantListAdapter!!.notifyDataSetChanged()
            } else {
                categoryList.add("All")
                showNoData()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // show progress bar
    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    // hide progress bar
    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    private fun showDataView()
    {
        rvMerchantList.visibility=View.VISIBLE
        tvNoDataFound.visibility=View.GONE
    }

    private fun showNoData()
    {
        rvMerchantList.visibility=View.GONE
        tvNoDataFound.visibility=View.VISIBLE
    }

}