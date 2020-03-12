package com.bugmakers.jarvistime.presentation.view.introductionslider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bugmakers.jarvistime.R
import com.bugmakers.jarvistime.databinding.ItemViewPagerIntroductionBinding

class IntroductionSliderAdapter(
    private val introductionSliderItems: List<IntroductionSliderItem>
) : RecyclerView.Adapter<IntroductionSliderAdapter.IntroductionSliderItemViewHolder>() {

    private val maxItemCount = 100

    override fun getItemCount() = maxItemCount

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): IntroductionSliderItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_view_pager_introduction, parent, false)

        return IntroductionSliderItemViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: IntroductionSliderItemViewHolder, position: Int) {
        val item = introductionSliderItems[position % introductionSliderItems.size]
        holder.bind(item)
    }

    class IntroductionSliderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemViewPagerIntroductionBinding? = DataBindingUtil.bind(this.itemView)

        fun bind(item: IntroductionSliderItem) {
            binding?.apply {
                itemLogo.setImageResource(item.itemLogoResId)
                introductionLabelFirst.setText(item.introductionLabelFirstId)
                introductionLabelSecond.setText(item.introductionLabelSecondId)
            }
        }
    }
}