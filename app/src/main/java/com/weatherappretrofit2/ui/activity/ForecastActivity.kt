package com.weatherappretrofit2.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.weatherappretrofit2.R
import com.weatherappretrofit2.model.local.forecast.ForecastData
import com.weatherappretrofit2.model.local.forecast.ForecastDataResult
import com.weatherappretrofit2.ui.adapter.CustomRecyclerAdapter
import com.weatherappretrofit2.ui.presenter.ForecastPresenter.forecastLiveData
import com.weatherappretrofit2.ui.presenter.ForecastPresenter.getForecastByCityName
import com.weatherappretrofit2.ui.presenter.ForecastPresenter.isLoading
import com.weatherappretrofit2.ui.presenter.WeatherPresenter.currentCity
import com.weatherappretrofit2.util.Constants
import com.weatherappretrofit2.util.Constants.CONST_EMPTY_VALUE
import com.weatherappretrofit2.util.MarginItemDecorator

class ForecastActivity : AppCompatActivity() {

    private var rvForecastList: RecyclerView? = null
    private var button: ImageButton? = null
    private var Loader: ProgressBar? = null
    private var iImage: ImageView? = null

    private val adapter by lazy {
        CustomRecyclerAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forecast)

        rvForecastList = findViewById(R.id.rvForecastList)
        button = findViewById(R.id.imageButtonForecast)
        iImage = findViewById(R.id.iImage)
        Loader = findViewById(R.id.loader)

        rvForecastList?.layoutManager = LinearLayoutManager(this)
        rvForecastList?.adapter = adapter


        if (rvForecastList?.itemDecorationCount == CONST_EMPTY_VALUE) {
            rvForecastList?.addItemDecoration(
                MarginItemDecorator(
                    this.resources.getDimension(R.dimen.dp_16).toInt(),
                    this.resources.getDimension(R.dimen.dp_8).toInt()
                )
            )
        }

        getForecastByCityName(cityName = currentCity.value)
        onClick()
    }

    override fun onResume() {
        super.onResume()

        setObservers()
    }

    private fun setObservers() {
        forecastLiveData.observe(this, Observer(this::setResult))
        isLoading.observe(this, Observer(this::setLoadingProgress))
    }

    private fun setResult(dataResult: ForecastDataResult) {
        when (dataResult.status) {
            Constants.SUCCESS -> setSuccessResult(dataResult.forecastData)
            Constants.ERROR -> setErrorResult()
            Constants.EMPTY -> setEmptyResult()
        }
    }

    private fun setSuccessResult(weatherSuccessData: ForecastData?) {
        weatherSuccessData?.forecastList?.let { adapter.addItems(it) }
    }

    private fun setErrorResult() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show()
    }

    private fun setEmptyResult() {
        Toast.makeText(this, getString(R.string.empty_message), Toast.LENGTH_LONG).show()
    }

    private fun setLoadingProgress(isLoading: Boolean) {
        Loader?.isVisible = isLoading
        iImage?.isVisible = !isLoading
        rvForecastList?.isVisible = !isLoading
    }

    private fun onClick() {
        button?.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}