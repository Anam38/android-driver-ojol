package com.udacoding.driverojolfirebasekotlin.utama.history.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.udacoding.driverojolfirebasekotlin.R
import com.udacoding.driverojolfirebasekotlin.utama.home.model.Booking


import kotlinx.android.synthetic.main.booking_item.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick


class Historydapter(
    private val mValues: List<Booking>, val click : onClickItem
) : RecyclerView.Adapter<Historydapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.booking_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var status : String? = null
        val item = mValues[position]
        holder.mAwal.text = item.lokasiAwal
        holder.mTanggal.text = item.tanggal
        holder.mTujuan.text = item.lokasiTujuan
        holder.mStatus.text = checkStatus(item.status!!.toInt())

        holder.itemView.onClick {
            click.Click(item)
        }

    }

    fun checkStatus(status:Int):String{
        var hasil : String? = null
        if (status == 1){
            return "Pending"
        }else if(status == 2 ){
            return "Proses"
        }else if(status == 4){
            return "Selesai"
        }else{
            return "Cancel"
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        var mAwal: TextView = mView.itemAwal
        val mTujuan: TextView = mView.itemTujuan
        val mTanggal : TextView = mView.itemTanggal
        val mStatus : TextView = mView.status
    }

    interface onClickItem{
        fun Click(Item : Booking)
    }
}
