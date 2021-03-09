package com.example.findmovies.fragments.category

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findmovies.R
import com.example.findmovies.adapters.GenreAdapter
import com.example.findmovies.adapters.MovieAdapter
import com.example.findmovies.di.App
import com.example.findmovies.models.GenreModel
import com.example.findmovies.network.NetworkHelper.API_KEY
import com.example.findmovies.network.RetrofitService
import com.example.findmovies.presenters.CategoryPresenter
import com.example.findmovies.views.CategoryView
import com.jakewharton.rxbinding4.widget.textChangeEvents
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CategoryFragment : MvpAppCompatFragment(), CategoryView {

    @InjectPresenter lateinit var presenter: CategoryPresenter
    @Inject lateinit var movieAdapter: MovieAdapter

    private lateinit var genreAdapter: GenreAdapter
    private lateinit var recyclerGenres: RecyclerView
    private lateinit var searchEt: EditText

    private lateinit var viewModel: CategoryViewModel
    @Inject lateinit var mService: RetrofitService


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_category, container, false)

        App.appComponent?.inject(this)
        viewModel = ViewModelProvider(activity as ViewModelStoreOwner).get(CategoryViewModel::class.java)

        recyclerGenres = view.findViewById(R.id.recycler_category)
        searchEt = view.findViewById(R.id.movie_search_et)

        genreAdapter = GenreAdapter()
        recyclerGenres.layoutManager = LinearLayoutManager(context)
        recyclerGenres.adapter = genreAdapter


        genreAdapter.setOnGenreClickListener(object : GenreAdapter.OnGenreClickListener {
            override fun onGenreClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt("genreId", genreAdapter.genres[position].id)
                findNavController().navigate(R.id.action_categoryFragment_to_moviesWithCategoryFragment, bundle)

                //Change ActionBar title
                activity?.findViewById<Toolbar>(R.id.main_toolbar)?.title = genreAdapter.genres[position].name
            }
        })

        searchEt.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0) {
                    recyclerGenres.layoutManager = GridLayoutManager(context, 2)
                    recyclerGenres.adapter = movieAdapter
                } else {
                    recyclerGenres.layoutManager = LinearLayoutManager(context)
                    recyclerGenres.adapter = genreAdapter
                }
            }
        })

        movieAdapter.setOnPosterClickListener(object: MovieAdapter.OnPosterClickListener {
            override fun onPosterClick(position: Int) {
                val bundle = Bundle()
                bundle.putInt(getString(R.string.movie_id_for_navigate), movieAdapter.movies[position].id)
                findNavController().navigate(R.id.action_categoryFragment_to_movieInfoFragment, bundle)
            }
        })


        if (viewModel.getCategories.value == null) {
            presenter.loadGenres()
        } else {
            genreAdapter.setGenres(viewModel.getCategories.value!!)
        }

        searchObserve()

        return view
    }

    private fun searchObserve() {
        searchEt.textChangeEvents()
            .debounce(1000, TimeUnit.MILLISECONDS)
            .filter {
                return@filter it.text.isNotEmpty()
            }
            .distinctUntilChanged()
            .switchMap {
                return@switchMap mService.getMovieFromQuery(API_KEY, it.text.toString())
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieAdapter.setMovies(it.results)
                Log.e("TAG", "onViewCreated: ${it.results}")
            }, {
                Log.e("TAG", "etChange: ", it)
            })
    }

    override fun setupGenres(genres: List<GenreModel>) {
        genreAdapter.setGenres(genres)
        viewModel.addCategories(genres)
    }

    override fun onDestroy() {
        presenter.disposeAll()
        super.onDestroy()
    }
}