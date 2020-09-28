package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.FragmentNewsListBinding
import kotlinx.android.synthetic.main.fragment_news_list.*


class NewsList : Fragment() {
    // TODO: Rename and change types of parameters
    //private var _binding: FragmentNewsListBinding? = null
    //private lateinit var factory: NewsViewModelFactory
    private lateinit var viewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentNewsListBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_news_list,container,false)
        binding.nextButton.setOnClickListener {view:View->
            view.findNavController().navigate(R.id.action_newsList2_to_newsInfoFragment2)

        }
        //viewModel = ViewModelProvider(this).get
        viewModel = ViewModelProvider(this).get( NewsViewModel::class.java)
        activity?.title = "Latest News"
        return binding.root


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        /*
        val news=News()
        factory=NewsViewModelFactory(news)
        viewModel.getNews()
        */
        val newsObjects=mutableListOf<News>()
        newsObjects.add(News("Covid: J&J vaccine shows strong immune response",
                "A single dose of Johnson & Johnson's experimental Covid-19 "))
        newsObjects.add(News("Section 144 imposed in Rajasthan's Banswara",
                "Banswara (Rajasthan) [India], September 26 (ANI) The Banswara administration on Saturday imposed Section " +
                "144 of IPC in the district, which prohibits the gathering of four or more persons at one place."))
        newsObjects.add(News("Most COVID positive patients have one of these symptoms, as per CDC",
            " While coronavirus is a virus which attacks the respiratory system, it induces a host of " +
                "symptoms in patients ranging from flu-like symptoms, immune disorders, muscle pain and aches"))
        newsObjects.add(News("First man cured of HIV infection now has terminal cancer",
                "Timothy Ray Brown, the first person known to have been cured of HIV infection, says he is now terminally " +
                "ill from a recurrence of cancer that prompted his historic treatment 12 yrs ago"))
        newsObjects.add(News("IPL 2020: Anushka Sharma calls Sunil Gavaskar's remark 'distasteful",
            "The legendary Sunil Gavaskar on Friday found himself in the midst of a controversy for making   comment about Virat Kohli " +
                "and his wife Anushka Sharma after the Indian captain's forgettable outing in the IPL. Gavaskar commented that Kohli has only faced Anushka's" +
                " bowling during lockdown, referring to the time when the couple was spotted playing cricket in their house"))
        newsObjects.add(News("Anil Ambani discloses worldwide assets to UK court in Chinese banks case",
            "Anil Ambani discloses worldwide assets to UK court in Chinese banks case"))
        newsObjects.add(News("Highly effective coronavirus antibodies identified, may lead to passive Covid-19 vaccine",
                "Scientists have identified highly effective antibodies against the novel coronavirus," +
                 " which they say can lead to the development of a passive vaccination for Covid-19. Unlike in the active vaccination, passive vaccination involves the administration of ready-made antibodies," +
                " which are degraded after some time. However, the effect of a passive vaccination is almost immediate"))
        //val title=mutableListOf("Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello","Hello",)
        newsList.adapter=NewsAdapter(newsObjects)
        newsList.layoutManager=LinearLayoutManager(activity)
        super.onActivityCreated(savedInstanceState)

    }
    override fun onDestroyView() {
        super.onDestroyView()
    }

}


