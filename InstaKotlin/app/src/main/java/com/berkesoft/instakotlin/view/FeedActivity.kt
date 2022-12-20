package com.berkesoft.instakotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkesoft.instakotlin.R
import com.berkesoft.instakotlin.adapter.PostAdapter
import com.berkesoft.instakotlin.databinding.ActivityFeedBinding
import com.berkesoft.instakotlin.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FeedActivity : AppCompatActivity() {
    private lateinit var binding : ActivityFeedBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postList : ArrayList<Post>
    private lateinit var adapter : PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        postList = ArrayList()
        auth = Firebase.auth
        db = Firebase.firestore
        getData()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PostAdapter(postList)
        binding.recyclerView.adapter = adapter


    }

    private fun getData(){

        db.collection("Post").orderBy("time",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(this@FeedActivity,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if(value != null){

                    if (!value.isEmpty){
                        postList.clear()

                        val documents = value.documents
                        for (document in documents){
                            val comment = document.get("comment") as String
                            val downloadUrl = document.get("downloadUrl") as String
                            val email = document.get("email") as String

                            val post = Post(email,downloadUrl, comment)
                            postList.add(post)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        }
    }





    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.insta_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_post){
            val intent = Intent(this@FeedActivity, UploadActivity::class.java)
            startActivity(intent)
        }else if(item.itemId == R.id.signout){
            val intent = Intent(this@FeedActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


}