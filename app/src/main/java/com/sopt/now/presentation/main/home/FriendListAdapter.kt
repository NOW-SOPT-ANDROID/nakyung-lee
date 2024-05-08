package com.sopt.now.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding
import com.sopt.now.databinding.ItemMyprofileBinding

class FriendListAdapter(private val myProfile: MyProfile) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var friendList: List<Friend> = emptyList()

    companion object {
        private const val VIEW_TYPE_MY_PROFILE = 0
        private const val VIEW_TYPE_FRIEND = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_MY_PROFILE -> {
                val binding = ItemMyprofileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MyProfileViewHolder(binding)
            }
            VIEW_TYPE_FRIEND -> {
                val binding = ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FriendViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_MY_PROFILE -> {
                val myProfileHolder = holder as MyProfileViewHolder
                myProfileHolder.bind(myProfile)
            }
            VIEW_TYPE_FRIEND -> {
                val friendHolder = holder as FriendViewHolder
                friendHolder.onBind(friendList[position - 1]) // -1을 해줘서 내 프로필의 위치를 제외함
            }
        }
    }

    override fun getItemCount(): Int = friendList.size + 1

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_MY_PROFILE
        } else {
            VIEW_TYPE_FRIEND
        }
    }

    fun setFriendList(friendList: List<Friend>) {
        this.friendList = friendList
        notifyDataSetChanged()
    }
}