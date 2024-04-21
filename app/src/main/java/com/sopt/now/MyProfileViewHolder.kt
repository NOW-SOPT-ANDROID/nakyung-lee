package com.sopt.now

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemMyprofileBinding

class MyProfileViewHolder(private val binding: ItemMyprofileBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(myProfile: MyProfile) {
        binding.run {
            ivMyprofile.setImageResource(myProfile.profileImage)
            tvMyname.text = myProfile.name
            btnMymusic.text = myProfile.description
        }
    }
}