package pl.mradtke.testapp.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import pl.mradtke.model.ui.UserItem
import pl.mradtke.testapp.databinding.ItemListUserBinding

/**
 * User list adapter. Data loading is supported by diff util.
 *
 * @author Michał Radtke
 * @version 28.07.2020
 */
class UserListAdapter : ListAdapter<UserItem, UserListViewHolder>(Companion) {

    companion object : DiffUtil.ItemCallback<UserItem>() {
        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean = oldItem.username == newItem.username
    }

    private var onItemClickListener: ((UserItem, ImageView) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemListUserBinding.inflate(layoutInflater, parent, false)

        return UserListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.user = item
        holder.binding.root.setOnClickListener {
            onItemClickListener?.invoke(item, holder.binding.itemListUserAvatar)
        }
        holder.binding.executePendingBindings()
    }

    /**
     * Set item click listener. Pass item data and view necessary to make transition.
     */
    fun setOnItemClickListener(listener: ((UserItem, ImageView) -> Unit)) {
        onItemClickListener = listener
    }
}
