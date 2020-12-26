package com.example.myfinalproject.ui.home

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myfinalproject.R
import com.example.weather.CityItem
import com.example.weather.CityViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    private lateinit var viewModel: CityViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel=ViewModelProvider(this).get(CityViewModel::class.java)
        viewModel.cities.observe(viewLifecycleOwner, Observer {
            val cities=it

            //在fragment不能使用this上下文
            val adapter = ArrayAdapter<CityItem>(requireActivity(), android.R.layout.simple_list_item_1, cities)

            listview.adapter = adapter
            listview.setOnItemClickListener { _, _, position, _ ->
                val cityCode = cities[position].city_code
                val intent = Intent(requireActivity(), WeatherActivity2::class.java)
                intent.putExtra("city_code", cityCode)
                startActivity(intent)
            }
        })
    }


}