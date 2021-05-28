package com.tallin.wallet.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.CopyOnWriteArrayList

open class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var items: CopyOnWriteArrayList<Any> = CopyOnWriteArrayList()

    val entities: MutableMap<Class<*>, Int> = mutableMapOf()

    val itemsTyped: MutableMap<Int, BaseAdapterItemModel<*>> = mutableMapOf()

    val itemTypes: ArrayList<Int> = arrayListOf()

    fun itemsLoaded(newItems: List<Any>?) {
        items.clear()
        newItems?.let(items::addAll)
        notifyDataSetChanged()
    }

    fun itemsAdded(newItems: List<Any>?) {
        newItems?.let(items::addAll)
        notifyDataSetChanged()
    }

    fun remove(item: Any) {
        items.remove(item)
        notifyDataSetChanged()
    }

    fun removeAll() {
        items = CopyOnWriteArrayList()
        notifyDataSetChanged()
    }

    fun getAllItems(): List<Any> = items

    inline fun <reified T: Any> getAllTypedItems() =
            getAllItems().filterIsInstance<T>()

    inline fun <reified T : Any> map(layout: Int, holder: Holder<T>): BaseAdapter {
        val itemType = entities.size
        entities[T::class.java] = itemType
        itemsTyped[itemType] = BaseAdapterItemModel(layout, holder)
        itemTypes.add(itemType)
        return this
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(view.context).inflate(itemsTyped[viewType]?.layout
                ?: 0, view, false)
        return RecyclerViewHolder(itemView, itemsTyped[viewType]?.holder)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return entities[items[position]::class.java] ?: 1234
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        bind(holder, items[position])
    }

    inline fun <reified T> bind(holder: RecyclerView.ViewHolder, item: T) {
        (holder as? RecyclerViewHolder<T>)?.bind(item)
    }

}

abstract class Holder<T> {
    abstract fun bind(itemView: View, item: T)
}

class RecyclerViewHolder<T>(itemView: View,
                            val holder: Holder<T>?) : RecyclerView.ViewHolder(itemView) {
    fun bind(item: T) {
        holder?.bind(itemView, item)
    }
}

data class BaseAdapterItemModel<T>(
        @DrawableRes val layout: Int,
        val holder: Holder<T>
)