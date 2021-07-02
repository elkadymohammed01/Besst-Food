/*
 * Copyright (c) 2020. rogergcc
 */

package com.e.goodcheif.adapter;

import android.widget.ImageView;

import com.e.goodcheif.model.PopularFood;


public interface ShopItemClickListener {

    void onDashboardCourseClick(PopularFood popularFood, ImageView imageView); // Shoud use imageview to make the shared animation between the two activity

}
