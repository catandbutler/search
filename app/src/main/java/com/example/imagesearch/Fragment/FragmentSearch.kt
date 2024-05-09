package com.example.imagesearch.Fragment


import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.Data.Search
import com.example.imagesearch.Data.SearchDocument
import com.example.imagesearch.MainActivity
import com.example.imagesearch.Retrofit.NetWorkClient
import com.example.imagesearch.databinding.FragmentSearchBinding
import com.example.searchimageprogram.Constrant.Constrant
import kotlinx.coroutines.launch

class FragmentSearch : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val main by lazy { activity as MainActivity }
    private val handler = Handler(Looper.getMainLooper())
    private var items = mutableListOf<SearchDocument>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        // 앱 재시작 시 이전에 저장했던 검색어 불러오기
        main.loadData(binding.searchEditText)

        // 검색 창 선택 시 이전 텍스트 제거
        binding.searchEditText.setOnClickListener {
            binding.searchEditText.text.clear()

        }
        // 검색 버튼 눌렀을 때의 이벤트 처리
        binding.searchbtn.setOnClickListener {
            val input = binding.searchEditText.text.toString()
            // input이 null이 아니라면 input의 공백 제거
            if (!input.isNullOrEmpty()) {
                val trimInput = input.trim()
                main.saveData(trimInput)
                communicateNetwork(queryParameter(trimInput))
                // 키보드 내리기
                hideKeyboard()
            } else
                Toast.makeText(context, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
        }

        return view
    }
    private fun communicateNetwork(param: HashMap<String, String>) = lifecycleScope.launch {
        val authorization = "KakaoAK ${Constrant.REST_API_KEY}"
        val response = NetWorkClient.service.searchImages(authorization, param)
        items = response.searchDocument

        // Search에 데이터 추가
        val searchDataList = items.map {
            Search(it.image_url, it.display_sitename, it.dateTime)
        }

        // api에서 받아온 데이터로 recyclerview 구성
        handler.post {
            binding.searchRecyclerView.apply {
                // 어댑터 업데이트
                adapter = SearchAdapter(context, items, searchDataList)
                // 이미지 격자 모양으로
                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                // recyclerview 일정하게
                setHasFixedSize(true)
            }
        }
    }

        // 키보드 내리기
        private fun hideKeyboard() {
            val inputMS = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMS.hideSoftInputFromWindow(binding.searchEditText.windowToken, 0)
        }

        private fun queryParameter(input: String): HashMap<String, String> {
            return hashMapOf(
                "query" to input,
                "sort" to "recency",
                "page" to "1",
                "size" to "80"
            )
        }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}