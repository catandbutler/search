package com.example.imagesearch.Fragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.Data.Search
import com.example.imagesearch.Data.SearchDocument
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ItemBinding
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


class SaveAdapter(
    private val Context: Context,
    var Data: MutableList<Search>,

    ): RecyclerView.Adapter<SaveAdapter.SaveViewHolder>() {
    private val Items = mutableListOf<SearchDocument>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaveViewHolder {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view.bookmark.setImageResource(R.drawable.bookmark)
        return SaveViewHolder(view)
    }

    override fun getItemCount(): Int {
        return Data.size
    }

    override fun onBindViewHolder(holder: SaveViewHolder, position: Int) {
        val item = Items[position]
        // 문자열 파싱
        val parsed = OffsetDateTime.parse(item.dateTime)
        // 원하는 형식으로 변환
        val parseDate = parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val parseTime = parsed.format(DateTimeFormatter.ofPattern("HH:mm"))

        Glide.with(Context)
            .load(item.image_url)
            .into(holder.image)
        holder.title.text = item.display_sitename
        holder.date.text = parseDate
        holder.time.text = parseTime

        // 저장된 항목 제거
        holder.bookmark.setOnClickListener {
            val position = holder.adapterPosition
            // 인덱스 오류 방지
            if(position != RecyclerView.NO_POSITION){
                // SearchData 리스트에서 해당 위치의 아이템 제거
                Data.removeAt(position)
                Log.d("removeData", "$Data")
                // position 항목 제거 후 recyclerview 갱신
                notifyItemRemoved(position)
            }
        }
    }

    inner class SaveViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.image
        val title = binding.title
        val date = binding.date
        val time = binding.time
        val bookmark = binding.bookmark

        // 생성자에서 Documnet를 리스트를 mData 항목을 복사해 초기화
        init {
            // 생성자에서 Document 초기화
            Items.addAll(Data.map {
                SearchDocument(it.url, it.site, it.dateTime)
            })
        }
    }
}


