package space.lobanov.reqrestapplication.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import space.lobanov.reqrestapplication.R
import space.lobanov.reqrestapplication.model.Images

class MyRecyclerViewAdapter(listOfImage: List<Images>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    private val listOfImage: List<Images> = listOfImage
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = listOfImage[position]
        val url: String = item.userImageUrl
        val mail: String = item.userEmail
        val name: String = item.userName
        Glide.with(holder.imgView.context).load(url).into(holder.imgView)
        holder.textView.text = name
        holder.textMail.text = mail
    }

    override fun getItemCount(): Int {
        return listOfImage.size
    }

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var imgView: ImageView = itemView.findViewById(R.id.img)
        var textView: TextView = itemView.findViewById(R.id.userTxtView)
        var textMail: TextView = itemView.findViewById(R.id.emailText)

        fun create(parent: ViewGroup): ViewHolder {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_layout, parent, false)
            return ViewHolder(view)

        }


    }
}




