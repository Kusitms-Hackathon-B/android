package com.example.jjikmuk.ui

import android.animation.LayoutTransition
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.example.jjikmuk.R
import com.example.jjikmuk.databinding.FragmentHomeBinding
import com.example.jjikmuk.model.InsufficientNutrientResponse
import com.example.jjikmuk.network.RetrofitBuilder
import com.example.jjikmuk.ui.adapter.Data
import com.example.jjikmuk.ui.adapter.RecyclerViewAdapter
import com.example.jjikmuk.util.BaseFragment
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val dietResponse = MutableLiveData<DietResponse>()
    private val retrofitBuilder = RetrofitBuilder.api

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.home = this

        initRecyclerView()

        setupDietData()
        setNutrients()


    }

    private fun setNutrients(){
        retrofitBuilder.getNutrients(1).enqueue(object: Callback<InsufficientNutrientResponse> {
            override fun onResponse(
                call: Call<InsufficientNutrientResponse>,
                response: Response<InsufficientNutrientResponse>
            ) {
                val data = response.body()!!

                binding.loadingAnimation.apply {
                    pauseAnimation()
                    visibility = View.GONE
                }

                binding.num1.text = "+"+ data.nutrientInfoList[0].amount.toString() + "mg"
                binding.name1.text = data.nutrientInfoList[0].name
                binding.type1.text = if (data.nutrientInfoList[0].amount < 15.3) {
                    "부족해요"
                } else if (data.nutrientInfoList[0].amount > 18.7) {
                    "과해요"
                } else {
                    "충분해요"
                }

                binding.name2.text = response.body()!!.nutrientInfoList[1].name
                binding.num2.text = "-"+response.body()!!.nutrientInfoList[1].amount.toString() + "g"
                binding.type2.text = if (data.nutrientInfoList[1].amount < 40.5) {
                    "부족해요"
                } else if (data.nutrientInfoList[1].amount > 49.5) {
                    "과해요"
                } else {
                    "충분해요"
                }

                binding.name3.text = response.body()!!.nutrientInfoList[2].name
                binding.num3.text = "-"+response.body()!!.nutrientInfoList[2].amount.toString() + "mg"
                binding.type3.text = if (data.nutrientInfoList[2].amount < 8.1) {
                    "부족해요"
                } else if (data.nutrientInfoList[2].amount > 9.9) {
                    "과해요"
                } else {
                    "충분해요"
                }

                binding.num4.text = "+22.1g"
                binding.type4.text = "충분해요"

                binding.num5.text = "+1.2mg"
                binding.type5.text = "충분해요"

                binding.num6.text = "+181kcal"
                binding.type6.text = "과해요"

                binding.menu.text = response.body()!!.goodDietName
            }

            override fun onFailure(call: Call<InsufficientNutrientResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun initRecyclerView(){
        val item = ArrayList<Data>()
        for(i in 0 until 10){
            item.add(Data("고등어 구이"))
        }
        val adapter = RecyclerViewAdapter(item)
        binding.recyclerView.adapter = adapter
    }

    private fun setupDietData() {
        lifecycleScope.launch {
            val response = RetrofitBuilder.api.getDiet(1).body()
            dietResponse.postValue(response!!)
        }
    }

    fun expandCardView(view: View){
        val transition = LayoutTransition()
        transition.enableTransitionType(LayoutTransition.CHANGING)
        transition.enableTransitionType(LayoutTransition.APPEARING)
        transition.enableTransitionType(LayoutTransition.DISAPPEARING)
        binding.layouts.layoutTransition = transition

        val v = if(binding.hidden1.visibility == View.GONE) View.VISIBLE else View.GONE
        binding.hidden1.visibility = v
//        binding.hidden2.visibility = v
        binding.hidden3.visibility = v
        if(v.toString()=="0"){
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_fold) // 여기에 원하는 drawable 리소스 ID를 입력하세요
            binding.expandButton.text = "접기"
            binding.expandButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        }else{
            val drawable = ContextCompat.getDrawable(requireContext(), R.drawable.ic_expand) // 여기에 원하는 drawable 리소스 ID를 입력하세요
            binding.expandButton.text = "전체 보기"
            binding.expandButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null)
        }
    }
}