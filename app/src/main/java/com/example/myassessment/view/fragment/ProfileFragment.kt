package com.example.myassessment.view.fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myassessment.databinding.FragmentProfileBinding
import com.example.myassessment.utils.CheckInternetConnection
import com.example.myassessment.network.Resource
import com.example.myassessment.network.TokenManager
import com.example.myassessment.view.LoginActivity
import com.example.myassessment.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var  progressDialog: ProgressDialog

    private lateinit var profileViewModel: ProfileViewModel

    @Inject
   lateinit var lokenManager: TokenManager

    @Inject
    lateinit var checkInternetConnection: CheckInternetConnection

    private var _binding:FragmentProfileBinding? = null;
    private val binding get() = _binding!!;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false);
        val view = binding.root


        progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("please wait...")
        progressDialog.show()

        binding.cardLogoutId.setOnClickListener {

            lokenManager.saveToken("")

            val intent= Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            Toast.makeText(requireContext(), "LogOut SuccessFull..", Toast.LENGTH_SHORT).show()
        }

        profileViewModel= ViewModelProvider(this)[ProfileViewModel::class.java]

        profileViewModel.profileLiveData.observe(requireActivity(), Observer { it->

            when(it){
                is Resource.Loading -> {
                    progressDialog.show()

                }

                is Resource.Error -> {

                    progressDialog.dismiss()

                    Toast.makeText(requireContext(), "${it.message}", Toast.LENGTH_SHORT).show()
                }

                is  Resource.Success -> {

                     _binding!!.textUsAddress.text ="Address: ${it.data?.address}"
                    _binding!!.textUsbalance.text ="Balance: ${it.data?.balance}"
                    _binding!!.textUscity.text ="City: ${it.data?.city}"
                    _binding!!.textUsCountry.text ="Country: ${it.data?.country}"
                    progressDialog.dismiss()

                }
            }

        })
        return view
    }

    override fun onResume() {
        super.onResume()

        InternetConnection()
    }

    @SuppressLint("SuspiciousIndentation")
    private  fun InternetConnection(){

        if (checkInternetConnection.isInternetAvailable(requireContext())) else
        Toast.makeText(requireContext(), "No Internet", Toast.LENGTH_SHORT).show()

    }

}