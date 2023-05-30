package com.rv198510.asiaflagsfragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import java.io.File
import java.io.InputStream


class FlagsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_flags, container, false)
        val countryNameTextView = view.findViewById<TextView>(R.id.countryNameTextView)
        val flagImageView = view.findViewById<ImageView>(R.id.flagImageView)


        val args = arguments
        countryNameTextView.text = args?.getString(JsonHelper.KEY_NAME)
        val imageName =   args?.getString(JsonHelper.KEY_ISO_ALPHA_2)?.lowercase()
        val imageUrl ="${resources.getIdentifier("${imageName}_flag", "drawable", activity?.packageName)}"

        if (imageUrl != null && imageUrl.toInt() != 0) {
            Picasso.get()
                ?.load(
                    resources.getIdentifier(
                        "${imageName + "_flag"}",
                        "drawable",
                        activity?.packageName
                    )
                )
                ?.into(flagImageView)
        }else{
            Picasso.get()
                ?.load(
                    resources.getIdentifier(
                        "dawn",
                        "drawable",
                        activity?.packageName
                    )
                )
                ?.into(flagImageView)
        }


        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(flags: Flags) =
            FlagsFragment().apply {
                arguments = Bundle().apply {

                    putString(JsonHelper.KEY_NAME, flags.name)
                    putString(JsonHelper.KEY_POPULATION, flags.population)
                    putString(JsonHelper.KEY_GDP, flags.gdp)
                    putString(JsonHelper.KEY_ISO_ALPHA_2, flags.iso_alpha_2)
                }
            }
    }
}