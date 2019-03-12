package com.example.wp.resource.widget.banner;

import android.support.v4.view.ViewPager.PageTransformer;

import com.example.wp.resource.widget.banner.transformer.AccordionTransformer;
import com.example.wp.resource.widget.banner.transformer.BackgroundToForegroundTransformer;
import com.example.wp.resource.widget.banner.transformer.CubeInTransformer;
import com.example.wp.resource.widget.banner.transformer.CubeOutTransformer;
import com.example.wp.resource.widget.banner.transformer.DefaultTransformer;
import com.example.wp.resource.widget.banner.transformer.DepthPageTransformer;
import com.example.wp.resource.widget.banner.transformer.FlipHorizontalTransformer;
import com.example.wp.resource.widget.banner.transformer.FlipVerticalTransformer;
import com.example.wp.resource.widget.banner.transformer.ForegroundToBackgroundTransformer;
import com.example.wp.resource.widget.banner.transformer.RotateDownTransformer;
import com.example.wp.resource.widget.banner.transformer.RotateUpTransformer;
import com.example.wp.resource.widget.banner.transformer.ScaleInOutTransformer;
import com.example.wp.resource.widget.banner.transformer.StackTransformer;
import com.example.wp.resource.widget.banner.transformer.TabletTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomInTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomOutSlideTransformer;
import com.example.wp.resource.widget.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}