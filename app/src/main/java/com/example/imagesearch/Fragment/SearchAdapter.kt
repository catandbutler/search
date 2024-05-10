package com.example.imagesearch.Fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.Data.Search
import com.example.imagesearch.Data.SearchDocument
import com.example.imagesearch.MainActivity
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ItemBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class SearchAdapter(
    private val Context: Context,
    private val Items: MutableList<SearchDocument>,
    val Data: List<Search>
) :

    RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {
    override fun getItemCount(): Int {
        return Items.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchAdapter.SearchViewHolder {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = Items[position]
        val data = Data[position]
        // 문자열 파싱
        val parsed = OffsetDateTime.parse(item.datetime)
        // 원하는 형식으로 변환
        val parseDate = parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val parseTime = parsed.format(DateTimeFormatter.ofPattern("HH:mm"))
        Glide.with(Context)
            .load(item.image_url)
            .into(holder.image)
        holder.title.text = item.display_sitename
        holder.date.text = parseDate
        holder.time.text = parseTime

        if(data.isSave)
            holder.bookmark.setImageResource(R.drawable.bookmark)
        else
            holder.bookmark.setImageResource(R.drawable.bookmarkcheck)
    }

    inner class SearchViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val image = view.findViewById<ImageView>(R.id.image)
        val title = view.findViewById<TextView>(R.id.title)
        val date = view.findViewById<TextView>(R.id.date)
        val time = view.findViewById<TextView>(R.id.time)
        val bookmark = view.findViewById<ImageButton>(R.id.bookmark)

        init {
            bookmark.setOnClickListener {
                // 아이템의 위치
                val position = adapterPosition
                // 아이템의 위치가 유효하다면
                if (position != RecyclerView.NO_POSITION) {
                    val userData = Data[position]
                    if (userData.isSave) {
                        (Context as MainActivity).removeItemList(userData)
                        Log.d("remove", "제거")
                    } else {
                        (Context as MainActivity).addItemList(userData)
                        Log.d("add", "추가")
                    }
                    userData.isSave = !userData.isSave
                    notifyDataSetChanged()
                }
            }
        }
    }
}