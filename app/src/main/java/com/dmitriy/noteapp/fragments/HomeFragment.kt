package com.dmitriy.noteapp.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.IdRes
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dmitriy.noteapp.ListView
import com.dmitriy.noteapp.MainActivity
import com.dmitriy.noteapp.MainSharedPreferences
import com.dmitriy.noteapp.R
import com.dmitriy.noteapp.adapters.NoteAdapter
import com.dmitriy.noteapp.databinding.FragmentHomeBinding
import com.dmitriy.noteapp.dialogs.DialogListView
import com.dmitriy.noteapp.model.Note
import com.dmitriy.noteapp.utils.FragmentCallbacks
import com.dmitriy.noteapp.utils.ListViewCallbacks
import com.dmitriy.noteapp.viewmodel.NoteListViewModel

class HomeFragment : Fragment(), SearchView.OnQueryTextListener, ListViewCallbacks {

    private var _fragmentCallbacks: FragmentCallbacks? = null
    val fragmentCallbacks get() = _fragmentCallbacks
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteRecyclerView: RecyclerView
    private var adapter: NoteAdapter? = NoteAdapter(emptyList(), this)

    private lateinit var mainSharedPreferences: SharedPreferences

    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProvider(this)[NoteListViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _fragmentCallbacks = context as FragmentCallbacks?
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainSharedPreferences = (activity as MainActivity).getSharedPreferences("MySharedPref", Activity.MODE_PRIVATE)!!
        ListView.currentListView = mainSharedPreferences.getInt("data", R.drawable.ic_list)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteRecyclerView = view.findViewById(R.id.rvNote) as RecyclerView

        setListView()

        binding.addNote.setOnClickListener {
            _fragmentCallbacks?.onFragmentReplacement(AddNoteFragment.newInstance(), false, true)
        }
    }

    override fun onStop() {
        super.onStop()
        mainSharedPreferences = (activity as MainActivity).getSharedPreferences("MySharedPref", Activity.MODE_PRIVATE)!!
        val editor = mainSharedPreferences.edit()

        editor.putInt("data", ListView.currentListView).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_main, menu)
        val menuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        menuSearch.isSubmitButtonEnabled = true
        menuSearch.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_list_type_selection -> {
                DialogListView(this, activity as MainActivity)
                    .showDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchNotes(query: String?) {
        val search = "%$query%"
        noteListViewModel.noteRepository.searchNote(search).observe(this) { list ->
            updateUI(list)
        }
    }

    private fun updateUI(notes: List<Note>) {

        adapter = NoteAdapter(notes, this)
        noteRecyclerView.adapter = adapter
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchNotes(newText)
        }
        return true
    }

    override fun onListViewReplacement(@IdRes id: Int) {
        ListView.currentListView = ListView.list[id]!!
        setListView()
    }

    private fun setListView() {

        when (ListView.currentListView) {
            R.drawable.ic_list -> {
                noteRecyclerView.layoutManager = LinearLayoutManager(context)
                noteRecyclerView.setHasFixedSize(true)
            }
            R.drawable.ic_details -> {
                noteRecyclerView.layoutManager = LinearLayoutManager(context)
                noteRecyclerView.setHasFixedSize(true)
            }
            R.drawable.ic_tile -> {
                noteRecyclerView.layoutManager = GridLayoutManager(context, 3)
                noteRecyclerView.setHasFixedSize(true)
            }
            R.drawable.ic_big_tile -> {
                noteRecyclerView.layoutManager = GridLayoutManager(context, 2)
                noteRecyclerView.setHasFixedSize(true)
            }
        }

        noteListViewModel.noteListLiveData.observe(
            viewLifecycleOwner,
            Observer { notes ->
                updateUI(notes)
            }
        )
    }
}