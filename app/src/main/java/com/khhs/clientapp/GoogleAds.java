package com.khhs.clientapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class GoogleAds {

    private InterstitialAd mInterstitialAd;
    public InterstitialAd loadInterstiialAds(Context context)
    {
        MobileAds.initialize(context,context.getString(R.string.app_id));
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(context.getString(R.string.interstitial_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());

            }
        });
        return mInterstitialAd;
    }



    FrameLayout container1,container2;
    AdView adView1,adView2;

    public void loadAdVerticalAds(View myView,Context context,Activity activity)
    {
        MobileAds.initialize(context,context.getString(R.string.app_id));
        adView1 = new AdView(context);
        adView2 = new AdView(context);
        container1 = myView.findViewById(R.id.container1);
        container2 = myView.findViewById(R.id.container2);

        adView1.setAdUnitId(context.getString(R.string.banner_unit_id));
        adView2.setAdUnitId(context.getString(R.string.banner_unit_id));

        adView1.setAdSize(getAdSize(activity,context));
        adView2.setAdSize(getAdSize(activity,context));

        adView1.loadAd(new AdRequest.Builder().build());
        adView2.loadAd(new AdRequest.Builder().build());

        container1.addView(adView1);
        container2.addView(adView2);





    }



    private AdSize getAdSize(Activity activity,Context context) {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context, adWidth);
    }


    private  RewardedVideoAd mRewardedVideoAd;
    public RewardedVideoAd loadRewaredVideoAds(final Context context)
    {
        MobileAds.initialize(context,context.getString(R.string.app_id));
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(context);
        loadRewardedVideoAd(context);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {

            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

                loadRewardedVideoAd(context);
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }

            @Override
            public void onRewardedVideoCompleted() {

            }
        });

        return  mRewardedVideoAd;
    }

    private void loadRewardedVideoAd(Context context) {
        mRewardedVideoAd.loadAd(context.getString(R.string.rewareded_id),
                new AdRequest.Builder().build());
    }

    FrameLayout adContainer,adContainer2,adContainer3;
    AdView adView3;
    AdRequest request1,request2,request3;
    public void loadThreeVerticalAds(View myView, Context context,Activity activity)
    {
        MobileAds.initialize(context,context.getString(R.string.app_id));

        adContainer = myView.findViewById(R.id.adcontainer);
        adView1 = new AdView(context);
        adView1.setAdUnitId(context.getString(R.string.banner_unit_id));
        adContainer.addView(adView1);

        adContainer2= myView.findViewById(R.id.adcontainer2);
        adContainer3 = myView.findViewById(R.id.adcontainer3);

        adView2 = new AdView(context);
        adView3 = new AdView(context);

        request1 = new AdRequest.Builder().build();
        adView1.setAdSize(getAdSize(activity,context));
        adView1.loadAd(request1);

        request2 = new AdRequest.Builder().build();
        adView2.setAdUnitId(context.getString(R.string.banner_unit_id));
        adView2.setAdSize(getAdSize(activity,context));
        adView2.loadAd(request2);
        adContainer2.addView(adView2);

        request3 = new AdRequest.Builder().build();
        adView3.setAdUnitId(context.getString(R.string.banner_unit_id));
        adView3.setAdSize(getAdSize(activity,context));
        adView3.loadAd(request3);
        adContainer3.addView(adView3);




    }


    public static TemplateView largetemplate,smalltemplate;



    public void loadNataiveAds(final View myView, Context context)
    {
        MobileAds.initialize(context,context.getString(R.string.app_id));
        AdLoader adLoader = new AdLoader.Builder(context, context.getString(R.string.native_unit_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        ColorDrawable background = new ColorDrawable(Color.WHITE);
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        smalltemplate = myView.findViewById(R.id.smalltemplate);

                        smalltemplate.setStyles(styles);
                        smalltemplate.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());



        AdLoader adLoader2 = new AdLoader.Builder(context,context.getString(R.string.native_unit_id))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        ColorDrawable background = new ColorDrawable(Color.WHITE);
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();

                        largetemplate = myView.findViewById(R.id.largetemplate);

                        largetemplate.setStyles(styles);
                        largetemplate.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader2.loadAd(new AdRequest.Builder().build());


    }


}
