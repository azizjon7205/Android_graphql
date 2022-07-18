package com.example.android_graphql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql.DeleteUserMutation
import com.example.android_graphql.UpdateUserMutation
import com.example.android_graphql.databinding.ActivityChangeBinding
import com.example.android_graphql.network.GraphQL
import kotlinx.coroutines.launch

class ChangeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val rocket = intent.getStringExtra("rocket")
        val twitter = intent.getStringExtra("twitter")

        with(binding){
            etName.text.insert(0, name)
            etRocket.text.insert(0, rocket)
            etTwitter.text.insert(0, twitter)

            bCreate.setOnClickListener {
                if (etName.text.isNotEmpty() && etRocket.text.isNotEmpty()){
                    updateUser(id!!, etName.text.toString(), etRocket.text.toString(), etTwitter.text.toString())
                }
            }

            bDelete.setOnClickListener {
                deleteUser(id!!)
            }
        }
    }

    private fun updateUser(id: String, name: String, rocket: String, twitter: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(
                    UpdateUserMutation(id, name, rocket, twitter)
                ).execute()
            } catch (e: ApolloException) {
                Log.d("@@@", e.toString())
                return@launchWhenResumed
            }
            finish()
            Log.d("@@@", result.toString())
        }
    }

    private fun deleteUser(id: String) {
        lifecycleScope.launch launchWhenResumed@{
            val result = try {
                GraphQL.get().mutation(DeleteUserMutation(id)).execute()
            } catch (e: ApolloException) {
                Log.d("@@@", e.toString())
                return@launchWhenResumed
            }
            finish()
            Log.d("@@@", result.toString())
        }
    }
}