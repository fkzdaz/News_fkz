
package com.example.fkz2.news_fkz.widget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.fkz2.news_fkz.R;


public class PlayerView extends RelativeLayout {
    private static final int NEEDLE_ANIMATOR_TIME = 300;
    private static final float NEEDLE_ROTATE_CIRCLE = -30.0f;
    private static final int PLAYER_ANIMATOR_TIME = 20 * 1000;
    private static final int PLAYER_ANIMATOR_REPEAT_COUNT = -1;
    private static final int PLAYER_REVERSE_ANIMATOR_TIME = 500;
    private RelativeLayout playerLayout;
    private ImageView playerDisc;
    private ImageView playerImage;
    private ImageView playerNeedle;

    private boolean isPlaying = false;

    private ObjectAnimator playerAnimator;
    private float playerLayoutAnimatorValue;

    private float needlePivotX = 0.0f;
    private float needlePivotY = 0.0f;
    private static final float X_FRACTION = 48.0f / 276.0f;
    private static final float Y_FRACTION = 48.0f / 414.0f;
    private Context context;

    public PlayerView(Context context) {
        super(context);
        init(context);
    }

    public PlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.play_needle);
        needlePivotX = bitmap.getWidth() * X_FRACTION;
        needlePivotY = bitmap.getHeight() * Y_FRACTION;
        bitmap.recycle();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        playerLayout = (RelativeLayout) findViewById(R.id.rl_player);
        playerDisc = (ImageView) findViewById(R.id.iv_player_disc);
        playerImage = (ImageView) findViewById(R.id.iv_player);
        playerNeedle = (ImageView) findViewById(R.id.iv_player_needle);

        playerNeedle.setPivotX(needlePivotX);
        playerNeedle.setPivotY(needlePivotY);
    }

    public void start() {
        if (isPlaying)
            return;
        startNeedleAnimator();
        startPlayerAnimator(0.0f);
        isPlaying = true;
    }

    public void restart() {
        if (isPlaying)
            return;
        startNeedleAnimator();
        startPlayerAnimator(playerLayoutAnimatorValue);
        isPlaying = true;
    }

    public void pause() {
        if (!isPlaying)
            return;
        startNeedleAnimator();
        if (playerAnimator.isRunning() || playerAnimator.isStarted())
            playerAnimator.cancel();

        isPlaying = false;
    }

    public void next() {
        if (isPlaying)
            startNeedleAnimator();
        playerAnimator.cancel();
        isPlaying = false;
        reversePlayerAnimator();
    }

    /**
     * needle动画
     */
    private void startNeedleAnimator() {
        ObjectAnimator needleAnimator;
        if (isPlaying) {
            needleAnimator = ObjectAnimator.ofFloat(playerNeedle, View.ROTATION, 0, NEEDLE_ROTATE_CIRCLE);
        } else {
            needleAnimator = ObjectAnimator.ofFloat(playerNeedle, View.ROTATION, NEEDLE_ROTATE_CIRCLE, 0);
        }
        needleAnimator.setDuration(NEEDLE_ANIMATOR_TIME);
        needleAnimator.setInterpolator(new DecelerateInterpolator());
        if (needleAnimator.isRunning() || needleAnimator.isStarted()) {
            needleAnimator.cancel();
        }
        needleAnimator.start();
    }

    /**
     * 背景旋转动画
     */
    private void startPlayerAnimator(float animatedValue) {
        playerAnimator = ObjectAnimator.ofFloat(playerLayout, View.ROTATION, animatedValue, 360 + animatedValue);
        playerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                playerLayoutAnimatorValue = (float) animation.getAnimatedValue();
            }
        });
        playerAnimator.setDuration(PLAYER_ANIMATOR_TIME);
        playerAnimator.setRepeatCount(PLAYER_ANIMATOR_REPEAT_COUNT);
        playerAnimator.setInterpolator(new LinearInterpolator());

        if (playerAnimator.isRunning() || playerAnimator.isStarted()) {
            playerAnimator.cancel();
        }
        playerAnimator.start();
    }

    /**
     * 暂停后继续动画
     */
    private void reversePlayerAnimator() {
        playerAnimator = ObjectAnimator.ofFloat(playerLayout, View.ROTATION, playerLayoutAnimatorValue, 360);
        playerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                playerLayoutAnimatorValue = (float) animation.getAnimatedValue();
            }
        });
        playerAnimator.setDuration(PLAYER_REVERSE_ANIMATOR_TIME);
        playerAnimator.setInterpolator(new AccelerateInterpolator());

        if (playerAnimator.isRunning() || playerAnimator.isStarted()) {
            playerAnimator.cancel();
        }
        playerAnimator.start();
    }


    public void loadPlayerDisc(Activity activity) {
        Glide.with(activity).load(R.drawable.placeholder_disk_play)
                .placeholder(R.drawable.placeholder_disk_play)
                .error(R.drawable.placeholder_disk_play)
                .transform(new GlideCircleTransform(context))
                .into(playerDisc);
    }

    public void loadPlayerImage(Activity activity, String url) {
        Glide.with(activity).load(url)
                .placeholder(R.color.bg_toolbar_color)
                .error(R.color.bg_toolbar_color)
                .transform(new GlideCircleTransform(context))
                .into(playerImage);
    }
}
