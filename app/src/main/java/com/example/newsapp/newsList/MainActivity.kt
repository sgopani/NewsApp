package com.example.newsapp.newsList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.databinding.ActivityMainBinding.inflate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main)
        //val view: View=inflate(R.layout.fragment_news_list,container,false)
        //setContentView(R.layout.fragment_news_list)
        val navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        //setContentView(R.layout.activity_main)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }


}

//<TextView
//android:id="@+id/newsTitle"
//style="@style/TextAppearance.MaterialComponents.Headline6"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="10dp"
//android:layout_marginTop="10dp"
//android:layout_marginEnd="10dp"
//android:text="TextView"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintHorizontal_bias="0.461"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toTopOf="parent" />
//
//<TextView
//android:id="@+id/newsDescription"
//style="@style/TextAppearance.MaterialComponents.Body1"
//android:layout_width="0dp"
//android:layout_height="wrap_content"
//android:layout_marginStart="10dp"
//android:layout_marginTop="10dp"
//android:layout_marginEnd="10dp"
//android:text="TextView"
//app:layout_constraintEnd_toEndOf="parent"
//app:layout_constraintStart_toStartOf="parent"
//app:layout_constraintTop_toBottomOf="@+id/newsTitle" />