package com.example.appfood.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appfood.activites.MainActivity
import com.example.appfood.adapter.FavoritesAdapter
import com.example.appfood.databinding.FragmentFavoritesBinding
import com.example.appfood.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar

class FavoritesFragment : androidx.fragment.app.Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var favoritiesRecyclerView:FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).vielModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewRycyclerView()
        obseveFatoryites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )= true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritiesRecyclerView.mealList[position])
                Snackbar.make(requireView(), "Meal delete", Snackbar.LENGTH_SHORT)
                    .setAction("UNDO", View.OnClickListener {
                        viewModel.insertMeal(favoritiesRecyclerView.mealList[position])
                    })
                    .show()
            }
        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerViewFavorites)
    }

    private fun setUpViewRycyclerView() {
        favoritiesRecyclerView = FavoritesAdapter()
        binding.recyclerViewFavorites.apply {
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            adapter = favoritiesRecyclerView
        }
    }

    private fun obseveFatoryites() {
        viewModel.observeFavoritesLiveData().observe(requireActivity(), { meal ->
            favoritiesRecyclerView.setMealList(meal)
        })
    }
}