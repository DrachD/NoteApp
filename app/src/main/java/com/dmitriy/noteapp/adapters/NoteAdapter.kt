package com.dmitriy.noteapp.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmitriy.noteapp.databinding.ListItemNoteBinding
import com.dmitriy.noteapp.fragments.HomeFragment
import com.dmitriy.noteapp.fragments.UpdateNoteFragment
import com.dmitriy.noteapp.model.Note

class NoteAdapter(private val list: List<Note>, private val mainFragment: HomeFragment) : RecyclerView.Adapter<NoteAdapter.NoteHolder>() {

    private var _binding: ListItemNoteBinding? = null
    private val binding get() = _binding!!
    private var fragmentCallbacks: UpdateNoteFragment? = null

    inner class NoteHolder(itemBinding: ListItemNoteBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        _binding = ListItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NoteHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {

        val currentNote = list[position]

        binding.apply {
            tvNoteTitle.text = currentNote.title
            tvNoteBody.text = currentNote.body
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cardView.setBackgroundColor(holder.itemView.resources.getColor(currentNote.resColor, null))
            } else {
                cardView.setBackgroundColor(holder.itemView.resources.getColor(currentNote.resColor))
            }
        }

        holder.itemView.setOnClickListener {
            mainFragment.fragmentCallbacks?.onFragmentReplacement(UpdateNoteFragment.newInstance(currentNote.id), false, true)
        }
    }

    override fun getItemCount(): Int = list.size
}