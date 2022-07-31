package com.dmitriy.noteapp.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmitriy.noteapp.ColorController
import com.dmitriy.noteapp.ListView
import com.dmitriy.noteapp.MainActivity
import com.dmitriy.noteapp.R
import com.dmitriy.noteapp.databinding.FragmentAddNoteBinding
import com.dmitriy.noteapp.model.Note
import com.dmitriy.noteapp.utils.ColorCallbacks
import com.dmitriy.noteapp.utils.FragmentCallbacks
import com.dmitriy.noteapp.viewmodel.NoteListViewModel

class AddNoteFragment : Fragment(), ColorCallbacks {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private var fragmentCallbacks: FragmentCallbacks? = null

    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProvider(this)[NoteListViewModel::class.java]
    }

    @ColorRes
    private var currentColor: Int = R.color.dialog_white

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallbacks = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater)

        return binding.root
    }

    private fun saveNote() {
        val title = binding.etNoteTitle.text.toString()
        val body = binding.etNoteBody.text.toString()

        if (title.isEmpty()) return

        val note = Note(0, title, body, currentColor)
        noteListViewModel.noteRepository.addNote(note)

        fragmentCallbacks?.onFragmentReplacement(HomeFragment(), true, false)
    }

    override fun onDetach() {
        super.onDetach()
        fragmentCallbacks = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                Log.d("myLogs", "OnOptionItemSelected()")
                saveNote()
            }
            R.id.menu_select_color -> {
                showDialogColor()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    fun showDialogColor() {
        val colorController = ColorController(this, activity as MainActivity)
        colorController.showDialog(currentColor)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_add_note, menu)
    }

    companion object {
        fun newInstance() = AddNoteFragment()
    }

    override fun onColorReplacementColorRes(@ColorRes idColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.cardViewNote.setBackgroundColor(resources.getColor(idColor, null))
        } else {
            binding.cardViewNote.setBackgroundColor(resources.getColor(idColor))
        }
        currentColor = idColor
    }

    override fun onColorReplacementIdRes(@IdRes idColor: Int) {

    }
}