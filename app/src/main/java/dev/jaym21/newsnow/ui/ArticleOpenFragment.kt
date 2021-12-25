package dev.jaym21.newsnow.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.NavController
import androidx.navigation.Navigation
import dev.jaym21.newsnow.R
import dev.jaym21.newsnow.data.remote.models.entities.Article
import dev.jaym21.newsnow.databinding.FragmentArticleOpenBinding

class ArticleOpenFragment : Fragment() {

    private var binding: FragmentArticleOpenBinding? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleOpenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initializing navController
        navController = Navigation.findNavController(view)

        //getting the article passed as argument from other fragment
        val article = arguments?.getSerializable("OpenArticle") as Article

        //passing the url of the clicked article to webViewClient to view full article
        if (article.url != null) {
            binding?.webView?.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)
            }
        } else {
            //back to news fragment if link to article is null
            navController.popBackStack()
        }

        binding?.fabShare?.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type="text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share this article...")
            shareIntent.putExtra(Intent.EXTRA_TEXT, article.url)
            startActivity(shareIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}