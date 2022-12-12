package com.example.meditation.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.meditation.databinding.ActivityMainBinding
import com.example.meditation.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.profileExit.setOnClickListener {  }

        val avatar = activity?.intent?.getStringExtra("avatar")
        val nickName = activity?.intent?.getStringExtra("nickName")
        binding.profilePhoto.load("$avatar")
        binding.profileName.text = "$nickName"

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}