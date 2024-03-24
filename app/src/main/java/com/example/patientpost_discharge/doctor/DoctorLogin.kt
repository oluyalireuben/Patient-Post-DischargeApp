package com.example.patientpost_discharge.doctor

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.patientpost_discharge.ForgotPassword
import com.example.patientpost_discharge.MainActivity
import com.example.patientpost_discharge.databinding.ActivityDoctorLoginBinding
import com.google.firebase.auth.FirebaseAuth

class DoctorLogin : AppCompatActivity() {

    private lateinit var binding: ActivityDoctorLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        binding.textview10.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.textview9.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
        }
        binding.button8.setOnClickListener {

            val email = binding.edittext15.text.toString().trim()
            val password = binding.edittext16.text.toString().trim()

            if (email.isEmpty()) {
                binding.edittext15.error = "This is a required field!"
                binding.edittext15.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.edittext16.error = "This is a required field!"
                binding.edittext16.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 4) {
                binding.edittext16.error = "This is a required field!"
                binding.edittext16.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edittext15.error = "Enter a valid email address!"
                binding.edittext15.requestFocus()
                return@setOnClickListener
            }

            binding.progressBar6.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.progressBar6.visibility = View.GONE
                    startActivity(
                        Intent(
                            this@DoctorLogin,
                            DoctorDashboard::class.java
                        )
                    )
                    binding.edittext15.setText("")
                    binding.edittext16.setText("")
                } else {
                    binding.progressBar6.visibility = View.GONE
                    Toast.makeText(
                        this@DoctorLogin,
                        task.exception?.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}

