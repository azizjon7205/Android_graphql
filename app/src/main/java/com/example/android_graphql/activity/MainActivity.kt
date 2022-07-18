package com.example.android_graphql.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql.DeleteUserMutation
import com.example.android_graphql.InsertUserMutation
import com.example.android_graphql.UpdateUserMutation
import com.example.android_graphql.UsersListQuery
import com.example.android_graphql.adapter.UsersAdapter
import com.example.android_graphql.databinding.ActivityMainBinding
import com.example.android_graphql.network.GraphQL
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter:UsersAdapter by lazy { UsersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    override fun onResume() {
        super.onResume()

        getUserList()
    }

    private fun initViews() {
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        binding.fabCreate.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        adapter.onClick = {
            val intent = Intent(this, ChangeActivity::class.java)
            intent.putExtra("id", it.id.toString())
            intent.putExtra("name", it.name)
            intent.putExtra("rocket", it.rocket)
            intent.putExtra("twitter", it.twitter.toString())
            startActivity(intent)
        }
    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(UsersListQuery(10)).execute()
            } catch (e: ApolloException){
                Log.d("@@@", e.toString())
                return@launchWhenResumed
            }
            val users = response.data?.users
            adapter.submitList(users!!)
            binding.rvMain.adapter = adapter
            Log.d("@@@", users.size.toString())
        }
    }


}