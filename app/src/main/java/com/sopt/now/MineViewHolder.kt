package com.sopt.now

import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding

class MineViewHolder(private val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(friendData: Friend) {
        binding.run {
            // ivMyprofile.setImageResource(friendData.profileImage)
            // tvMyname.text = friendData.name
            // btnMymusic.text = "Last Chance - 소수빈"
        }
    }
}