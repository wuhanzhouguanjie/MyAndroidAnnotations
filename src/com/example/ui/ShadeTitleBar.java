package com.example.ui;

import java.util.LinkedList;

import com.example.Util.PublicUtils;
import com.example.myandroidannotations.R;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShadeTitleBar extends RelativeLayout implements ScrollAnimInterface{
	
	private static final String TAG = "ShadeTitleBar";

	public static int SHADOW_MODE_PURE_COLOR = 1;
	public static int SHADOW_MODE_DRAWABLE = 2;

	private static final boolean TRANSLUCENT_STATUS_BAR = true;

	private boolean mFitSystemWindow = false;
	private boolean mImmersiveTitleBarEnabled;

	// ============================================================================
	// Draw shadow on status bar above API 19
	// ============================================================================
	private int mShadowMode = SHADOW_MODE_DRAWABLE;
	private Paint mPaint;
	private int mStatusBarHeight;
	private Drawable mShadowDrawable = null;
	
	
	// ============================================================================
    // Scroll animation
    // ============================================================================

    // store original color
    private static final int ORIGIN_COLOR = -12345;

    private boolean mTransparentEnabled = false;
    // start position of fading animation
    private int mStartFadePosition = 80;
    // end position of fading animation
    private int mEndFadePosition = 380;
    // Max alpha, by default 247(0.97*255)
    private int mMaxAlpha = 247;
    // fade area
    private int mFadeDuration = mEndFadePosition - mStartFadePosition;
    // text shadow when completely transparent
    private float mShadowRadius = 0;
    private int mBarTitleTextShadowColor;

    // View needs to do gradual change during scroll animation
    private LinkedList<View> mFadeViewList = null;
    private TextView mTitleTextView = null;
    private ColorStateList mOriginBarTitleColor = null;

	public ShadeTitleBar(Context context) {
		super(context);
	}

	public ShadeTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ShadeTitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		mImmersiveTitleBarEnabled = isImmersiveTitleBarEnabled();
		if (mImmersiveTitleBarEnabled) {
			mPaint = new Paint();
			mStatusBarHeight = PublicUtils.getStatusBarHeight(context);
			mPaint.setColor(getResources().getColor(
					R.color.status_bar_shadow_bg));
			setShadowDrawable(R.drawable.status_bar_shadow, context);
		}
	}

	/**
	 * Set shadow drawable
	 * 
	 * @param resourceId
	 *            shadow drawable resourceId
	 */
	public void setShadowDrawable(int resourceId, Context context) {
		mShadowDrawable = getContext().getResources().getDrawable(resourceId);
		if (mShadowDrawable != null) {
			mShadowDrawable.setBounds(0, 0, PublicUtils.getScreenWidth(),
					mShadowDrawable.getIntrinsicHeight());
		}
		// To make it nature, we don't set bounds to status bar height
		// mShadowDrawable.setBounds(0, 0, ViewUtils.getScreenWidth(),
		// mStatusBarHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// The layout has actually already been performed and the positions
		// cached.
		// Apply the cached values to the children.
		super.onLayout(changed, l, t, r, b);

		int count = getChildCount();
		for (int i = 0; i < count; i++) {
			View child = getChildAt(i);
			if (child.getVisibility() == GONE) {
				continue;
			}

			int xOffset = 0;
			LayoutParams params = (LayoutParams) child.getLayoutParams();
			final int[] rules = params.getRules();
			if (rules[CENTER_IN_PARENT] != 0 || rules[CENTER_VERTICAL] != 0) {
				xOffset = (getPaddingTop() - getPaddingBottom()) / 2;
			}
			if (xOffset != 0) {
				child.offsetTopAndBottom(xOffset);
			}
		}
	}

	public static boolean isImmersiveTitleBarEnabled() {
		return TRANSLUCENT_STATUS_BAR
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// Immersive title bar is open, deal with shadow
		if (mShadowMode == SHADOW_MODE_DRAWABLE && mShadowDrawable != null) {
			mShadowDrawable.draw(canvas);
		} else {
			canvas.drawRect(0, 0, PublicUtils.getScreenWidth(),
					mStatusBarHeight, mPaint);
		}
	}

	

	/**
	 * open this if using fitsSystemWindows on root layout
	 * 
	 * @param fitsSystemWindows
	 *            android:fitsSystemWindows
	 */
	public void setTitleBarFitsSystemWindows(boolean fitsSystemWindows) {
		if (mFitSystemWindow != fitsSystemWindows) {
			mFitSystemWindow = fitsSystemWindows;
			if (mFitSystemWindow) {
				ViewGroup.MarginLayoutParams marginLayoutParam = (ViewGroup.MarginLayoutParams) getLayoutParams();
				marginLayoutParam.topMargin = -mStatusBarHeight;
				setLayoutParams(marginLayoutParam);
			}
		}
	}

	@Override
	public int getPaddingTop() {
		if (mImmersiveTitleBarEnabled) {
			return super.getPaddingTop() + mStatusBarHeight;
		}
		return super.getPaddingTop();
	}

	@Override
	protected int getSuggestedMinimumHeight() {
		if (!mImmersiveTitleBarEnabled) {
			return super.getSuggestedMinimumHeight();
		} else {
			int addition = 0;
			if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
				addition = mStatusBarHeight;
			} else if (Build.VERSION.SDK_INT >= 20) {
				// Lollipop has a pure alpha black bar, so make title bar higher
				// than kitkat
				addition = mStatusBarHeight +  PublicUtils.dpToPx(10);
			}
			return super.getSuggestedMinimumHeight() + addition;
		}
	}

	/**
     * Set transparent title bar anim.
     *
     * @param enabled whether to open anim
     */
    public void setTransparentEnabled(boolean enabled) {
        setTransparentEnabled(enabled, mStartFadePosition, mEndFadePosition, mMaxAlpha);
    }

    /**
     * Set transparent title bar anim.
     *
     * @param enabled whether to open anim
     * @param start   start position of fading animation
     * @param end     end position of fading animation
     */
    public void setTransparentEnabled(boolean enabled, int start, int end) {
        setTransparentEnabled(enabled, start, end, mMaxAlpha);
    }

    /**
     * Set transparent title bar anim.
     *
     * @param enabled  whether to open anim
     * @param start    start position of fading animation
     * @param end      end position of fading animation
     * @param maxAlpha max alpha(0-255)
     */
    public void setTransparentEnabled(boolean enabled, int start, int end, int maxAlpha) {
        mTransparentEnabled = enabled;
        if (mTransparentEnabled) {
            mStartFadePosition = start;
            mEndFadePosition = end;
            mMaxAlpha = maxAlpha;
            mFadeDuration = mEndFadePosition - mStartFadePosition;
        }
    }

    /**
     * Add view that needs gradual change
     */
    public void addViewToFadeList(View view) {
        if (mFadeViewList == null) {
            mFadeViewList = new LinkedList<View>();
        }
        if (view != null) {
            mFadeViewList.add(view);
        }
    }

    /**
     * remove view that has been added by addViewToFadeList
     */
    public void removeViewFromFadeList(View view) {
        if (mFadeViewList != null && view != null) {
            mFadeViewList.remove(view);
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title) && mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
    }

    public void setTitleTextView(TextView textView) {
        mTitleTextView = textView;
        if (mTitleTextView != null) {
            mOriginBarTitleColor = mTitleTextView.getTextColors();
        }
    }

    public void setTextShadowColor(int color) {
        mBarTitleTextShadowColor = color;
    }

    private void setTitleBarTranslate(int alpha) {
        if (getBackground() == null) {
            return;
        }
        getBackground().mutate().setAlpha(alpha);
        if (mFadeViewList == null || mTitleTextView == null) {
            return;
        }
        if (alpha > 0) {
            setTitleBarShadowLayer(0);
        }
        if (alpha >= mMaxAlpha) {
            setTitleBarColor(ORIGIN_COLOR);
        } else {
            setTitleBarColor(interpolateColor(Color.WHITE, mOriginBarTitleColor.getDefaultColor(), alpha, mMaxAlpha));
            if (alpha == 0) {
                setTitleBarShadowLayer(1f);
            }
        }
    }

    private void setTitleBarColor(int color) {
        for (View view : mFadeViewList) {
            setViewColor(view, color);
        }
    }

    private void setViewColor(View view, int color) {
        if (view == null || view.getVisibility() != VISIBLE) {
            return;
        }
        if (view instanceof TextView) {
            setViewColor((TextView) view, color);
        }
        if (view instanceof ImageView) {
            setViewColor((ImageView) view, color);
        }
    }

    private void setViewColor(TextView view, int color) {
        if (color == ORIGIN_COLOR) {
            view.setTextColor(mOriginBarTitleColor);
            if (view.getBackground() != null) {
                view.getBackground().clearColorFilter();
            }
        } else {
            view.setTextColor(color);
            if (view.getBackground() != null) {
                view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    private void setViewColor(ImageView view, int color) {
        if (color == ORIGIN_COLOR) {
            view.clearColorFilter();
        } else {
            view.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void setTitleBarShadowLayer(float radius) {
        if (mShadowRadius != radius) {
            mShadowRadius = radius;
            for (View view : mFadeViewList) {
                if (view instanceof TextView) {
                    ((TextView) view).setShadowLayer(mShadowRadius, 0, 1, mBarTitleTextShadowColor);
                }
            }
        }
    }

    /**
     * onScroll callback interface for ListView
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int endFadeItem) {
        if (mTransparentEnabled && firstVisibleItem <= endFadeItem) {
            View v = view.getChildAt(0);
            // Top of visible window, originally 0, turn to negative when scroll to lower position
            int top = (v == null) ? 0 : v.getTop();
            // distance from top of current window to ending position of fade
            int delta = top + mEndFadePosition;
            setTitleBarTranslate(interpolate(delta));
        }
    }

    /**
     * onScroll callback interface for ScrollView
     */
    @Override
    public void onScroll(int y) {
        if (mTransparentEnabled) {
            setTitleBarTranslate(interpolate(mEndFadePosition - y));
        }
    }

    /**
     * @param delta distance to ending position
     * @return alpha value at current position
     */
    private int interpolate(int delta) {
        if (delta > mFadeDuration) {
            return 0;
        } else if (delta <= 0) {
            return mMaxAlpha;
        } else {
            float temp = ((float) delta) / mFadeDuration;
            return (int) ((1 - temp) * mMaxAlpha);
        }
    }

    public static int interpolateColor(int colorFrom, int colorTo, int posFrom, int posTo) {
        float delta = posTo - posFrom;
        int red = (int) ((Color.red(colorFrom) - Color.red(colorTo)) * delta / posTo + Color.red(colorTo));
        int green = (int) ((Color.green(colorFrom) - Color.green(colorTo)) * delta / posTo + Color.green(colorTo));
        int blue = (int) ((Color.blue(colorFrom) - Color.blue(colorTo)) * delta / posTo) + Color.blue(colorTo);
        return Color.argb(255, red, green, blue);
    }

}
