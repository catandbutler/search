package com.example.imagesearch

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.imagesearch.Data.Search
import com.example.imagesearch.Fragment.FragmentSave
import com.example.imagesearch.Fragment.FragmentSearch
import com.example.imagesearch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    var saveList = mutableListOf<Search>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigationView.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.searchpage -> {
                    tranFragment(FragmentSearch())
                    return@setOnItemSelectedListener true
                }
                R.id.savepage -> {
                    tranFragment(FragmentSave())
                    return@setOnItemSelectedListener true
                }

                else -> false
            }
        }
    }
    // fragment 변경 함수 생성
    private fun tranFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

    // sharedPreference를 이용한 검색어 저장하기
    fun saveData(input: String) {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("keyword", input)
        // 검색창에 입력한 값 저장
        edit.apply()
    }

    // sharedPreference를 이용한 검색어 불러오기
    fun loadData(text: EditText) {
        val pref = getSharedPreferences("pref", 0)
        text.setText(pref.getString("keyword", ""))
    }
    // 저장한 항목 리스트에 추가
    fun addItemList(item : Search) {
        // 항목이 저장되어 있지 않은 경우에만 저장
        val containList = saveList.any { it.url == item.url }
        if(!containList)
            saveList.add(item)
    }

    // 저장한 항목 리스트에서 제거
    fun removeItemList(item : Search) {
        saveList.remove(item)
    }

}