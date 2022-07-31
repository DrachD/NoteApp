package com.dmitriy.noteapp.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dmitriy.noteapp.ColorController
import com.dmitriy.noteapp.MainActivity
import com.dmitriy.noteapp.R
import com.dmitriy.noteapp.databinding.FragmentUpdateNoteBinding
import com.dmitriy.noteapp.model.Note
import com.dmitriy.noteapp.utils.ColorCallbacks
import com.dmitriy.noteapp.utils.FragmentCallbacks
import com.dmitriy.noteapp.viewmodel.NoteListViewModel

private const val ARG_NOTE_ID = "note_id"

class UpdateNoteFragment : Fragment(), ColorCallbacks {

    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!
    private var fragmentCallbacks: FragmentCallbacks? = null

    @ColorRes
    private var currentColor: Int = R.color.dialog_white

    private val noteListViewModel: NoteListViewModel by lazy {
        ViewModelProvider(this)[NoteListViewModel::class.java]
    }

    private lateinit var currentNote: Note

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentCallbacks = context as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val noteId: Int = arguments?.getSerializable(ARG_NOTE_ID) as Int
        noteListViewModel.loadNote(noteId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateNoteBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteListViewModel.noteLiveData.observe(viewLifecycleOwner) {
            currentNote = it
            updateUI()
        }

        binding.fabUpdate.setOnClickListener {
            updateNote()
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentCallbacks = null
    }

    private fun updateUI() {
        binding.etNoteTitle.setText(currentNote.title)
        binding.etNoteBody.setText(currentNote.body)
        currentColor = currentNote.resColor
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.cardView.setBackgroundColor(resources.getColor(currentColor, null))
        } else {
            binding.cardView.setBackgroundColor(resources.getColor(currentColor))
        }
    }

    private fun updateNote() {
        val title = binding.etNoteTitle.text.toString()
        val body = binding.etNoteBody.text.toString()

        if (title.isEmpty()) return

        currentNote.title = title
        currentNote.body = body
        currentNote.resColor = currentColor

        noteListViewModel.noteRepository.updateNote(currentNote)
        fragmentCallbacks?.onFragmentReplacement(HomeFragment.newInstance(), true, false)
    }

    private fun deleteNote() {

        noteListViewModel.noteRepository.deleteNote(currentNote)
        fragmentCallbacks?.onFragmentReplacement(HomeFragment.newInstance(), true, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.menu_update_note, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
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

    companion object {
        fun newInstance(noteId: Int): UpdateNoteFragment {

            val args = Bundle().apply {
                putSerializable(ARG_NOTE_ID, noteId)
            }
            return UpdateNoteFragment().apply {
                arguments = args
            }
        }
    }

    override fun onColorReplacementColorRes(@ColorRes idColor: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.cardView.setBackgroundColor(resources.getColor(idColor, null))
        } else {
            binding.cardView.setBackgroundColor(resources.getColor(idColor))
        }
        currentColor = idColor
    }

    override fun onColorReplacementIdRes(idColor: Int) {

    }
}