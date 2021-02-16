package com.weatherappretrofit2.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.squareup.picasso.Picasso
import com.weatherappretrofit2.R
import com.weatherappretrofit2.model.local.weather.DataResult
import com.weatherappretrofit2.model.local.weather.WeatherData
import com.weatherappretrofit2.ui.presenter.WeatherPresenter.currentCity
import com.weatherappretrofit2.ui.presenter.WeatherPresenter.getCurrentWeatherByCityName
import com.weatherappretrofit2.ui.presenter.WeatherPresenter.isLoading
import com.weatherappretrofit2.ui.presenter.WeatherPresenter.weatherLiveData
import com.weatherappretrofit2.util.Constants.API_KEY
import com.weatherappretrofit2.util.Constants.EMPTY
import com.weatherappretrofit2.util.Constants.ERROR
import com.weatherappretrofit2.util.Constants.SUCCESS
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var tvAddress: TextView? = null
    private var tvStatus: TextView? = null
    private var tvUpdatedAt: TextView? = null
    private var tvTemp: TextView? = null
    private var tvTempMax: TextView? = null
    private var tvTempMin: TextView? = null
    private var tvPressure: TextView? = null
    private var tvHumidity: TextView? = null
    private var tvSunrise: TextView? = null
    private var tvSunset: TextView? = null
    private var tvWindSpeed: TextView? = null
    private var tvIcon: TextView? = null
    private var ivImage: ImageView? = null
    private var iconUrl: String? = null
    private var pbLoader: ProgressBar? = null
    private var ibImageButton: ImageButton? = null
    private var mainSwipeRefresh: SwipeRefreshLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getCurrentWeatherByCityName(null)
        initUiComponents()

        with(mainSwipeRefresh) {
            this?.setOnRefreshListener { getCurrentWeatherByCityName(null) }
            mainSwipeRefresh
        }
    }

    override fun onResume() {
        super.onResume()

        setObservers()
        initPlaces()
    }

    private fun setObservers() {
        weatherLiveData.observe(this, Observer(this::setResult))
        isLoading.observe(this, Observer(this::setLoadingProgress))
    }

    private fun initUiComponents() {
        tvAddress = findViewById(R.id.tvAddress)
        tvStatus = findViewById(R.id.tvStatus)
        tvUpdatedAt = findViewById(R.id.tvUpdatedAt)
        tvTemp = findViewById(R.id.tvTemp)
        tvTempMax = findViewById(R.id.tvTempMax)
        tvTempMin = findViewById(R.id.tvTempMin)
        tvPressure = findViewById(R.id.tvPressure)
        tvHumidity = findViewById(R.id.tvHumidity)
        tvSunrise = findViewById(R.id.tvSunrise)
        tvSunset = findViewById(R.id.tvSunset)
        tvWindSpeed = findViewById(R.id.tvWind)
        tvIcon = findViewById(R.id.tvIcon)
        ivImage = findViewById(R.id.ivImage)
        pbLoader = findViewById(R.id.pbLoader)
        ibImageButton = findViewById(R.id.imageButton)
        mainSwipeRefresh = findViewById(R.id.mainSwipeRefresh)

        initClickListener()
    }

    private fun initPlaces() {
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, API_KEY)
        }

        Places.createClient(this)

        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        autocompleteFragment.apply {
            setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))
            setOnPlaceSelectedListener(object : PlaceSelectionListener {
                override fun onPlaceSelected(place: Place) {
                    getCurrentWeatherByCityName(cityName = place.name)
                    currentCity.postValue(place.name)
                }

                override fun onError(p0: Status) {
                    setErrorResult()
                    Toast.makeText(
                        this@MainActivity,
                        p0.statusMessage.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    private fun setResult(dataResult: DataResult) {
        when (dataResult.status) {
            SUCCESS -> setSuccessResult(dataResult.weatherData)
            ERROR -> setErrorResult()
            EMPTY -> setEmptyResult()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setSuccessResult(weatherSuccessData: WeatherData?) {
        weatherSuccessData?.apply {
            tvAddress?.text = "$name, $address"
            tvUpdatedAt?.text = getString(R.string.updateAt) + SimpleDateFormat(
                getString(R.string.timeDatePattern),
                Locale.ENGLISH
            ).format(updatedAt * 1000)
            tvTemp?.text = "$temp℃"
            tvTempMax?.text = getString(R.string.maxTemp) + " $tempMax℃"
            tvTempMin?.text = getString(R.string.minTemp) + "$tempMin℃"
            tvPressure?.text = "$pressure hPa"
            tvHumidity?.text = "$humidity %"
            tvSunrise?.text = SimpleDateFormat(
                getString(R.string.timePattern),
                Locale.US
            ).format(sunrise.times(1000))
            tvSunset?.text = SimpleDateFormat(
                getString(R.string.timePattern),
                Locale.US
            ).format(sunset.times(1000))
            tvWindSpeed?.text = "$windSpeed m/s"
            tvStatus?.text =
                weatherDescription.substring(0,1).toUpperCase(Locale.ROOT) + weatherDescription.substring(1)

            iconUrl =
                getString(R.string.urlMessageStratIcon) + icon + getString(R.string.urlMessageEndIcon)

            Picasso.with(this@MainActivity)
                .load(iconUrl)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .into(ivImage)

        }
    }

    private fun setErrorResult() {
        Toast.makeText(this, getString(R.string.error_message), Toast.LENGTH_LONG).show()
    }

    private fun setEmptyResult() {
        Toast.makeText(this, getString(R.string.empty_message), Toast.LENGTH_LONG).show()
    }

    private fun setLoadingProgress(isLoading: Boolean) {
        pbLoader?.isVisible = isLoading
        ivImage?.isVisible = !isLoading
        tvStatus?.isVisible = !isLoading
        mainSwipeRefresh?.isRefreshing = isLoading
    }

    private fun initClickListener() {
        ibImageButton?.setOnClickListener {
            val intent = Intent(this, ForecastActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}

