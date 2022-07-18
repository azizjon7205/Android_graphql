package com.example.android_graphql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql.InsertUserMutation
import com.example.android_graphql.R
import com.example.android_graphql.databinding.ActivityCreateBinding
import com.example.android_graphql.network.GraphQL
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        with(binding){
            bCreate.setOnClickListener {
                if (etName.text.isNotEmpty() && etRocket.text.isNotEmpty()){
                    insertUser(etName.text.toString(), etRocket.text.toString(), etTwitter.text.toString())
                }
            }
        }

    }


    private fun insertUser(name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(
                    InsertUserMutation(name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("MainActivity", e.toString())
                return@launchWhenResumed
            }
            finish()
            Log.d("MainActivity", result.toString())
        }
    }

}