package com.michael.github.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.mdroid.lib.core.base.BaseApp;
import com.mdroid.lib.core.dialog.CenterDialog;
import com.mdroid.lib.core.dialog.IDialog;
import com.mdroid.lib.core.utils.Toost;
import com.mdroid.lib.recyclerview.BaseRecyclerViewAdapter;
import com.mdroid.utils.AndroidUtils;
import com.mdroid.utils.Ln;
import com.mdroid.utils.ReflectUtils;
import com.michael.github.R;
import com.michael.github.base.App;
import com.orhanobut.dialogplus.DialogPlus;
import de.greenrobot.common.io.FileUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.michael.github.base.Constants.NormalCons.SEPARATOR_POINT;

/**
 * Created by ouyangzn on 2016/11/15.<br/>
 * Description：放置一些公用的方法
 */
public class CommonUtils {

  /**
   * 限制输入框输入数字的位数 (xxx.xxx)
   *
   * @param editText EditText
   * @param frontDigit 小数点前的位数
   * @param lastDigit 小数点后的位数
   */
  public static void limitEditDecimal(final EditText editText, final int frontDigit,
      final int lastDigit) {
    editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
    RxTextView.textChanges(editText).skip(1).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence charSequence) {
        String str = charSequence.toString();
        if (!TextUtils.isEmpty(str)) {
          if (str.startsWith(SEPARATOR_POINT)) {
            editText.setText(0 + str);
            return;
          }
          if (str.contains(SEPARATOR_POINT)) {
            String[] split = str.split("\\.");
            if (split[0].length() > frontDigit) {
              split[0] = split[0].substring(0, frontDigit);
              String text = split[0];
              if (split.length == 2) {
                text = split[0] + SEPARATOR_POINT + split[1];
              }
              editText.setText(text);
              editText.setSelection(text.length());
              return;
            }
            if (split.length == 2 && split[1].length() > lastDigit) {
              String text = split[0] + SEPARATOR_POINT + split[1].subSequence(0, lastDigit);
              editText.setText(text);
              editText.setSelection(text.length());
              return;
            }
          } else {
            if (str.length() > frontDigit) {
              editText.setText(str.subSequence(0, frontDigit));
              editText.setSelection(frontDigit);
            }
          }
        }
      }
    }, new Consumer<Throwable>() {
      @Override public void accept(Throwable throwable) {
        Ln.e(throwable);
      }
    });
  }

  /**
   * 禁止输入emoji
   *
   * @param editText EditText
   */
  public static void filterEmoji(EditText editText) {
    InputFilter[] filters = editText.getFilters();
    InputFilter[] inputFilters = Arrays.copyOf(filters, filters.length + 1);
    inputFilters[filters.length] = getEmojiFilter();
    editText.setFilters(inputFilters);
  }

  /**
   * 过滤掉emoji的InputFilter
   */
  public static InputFilter getEmojiFilter() {
    return new InputFilter() {
      Pattern pattern =
          Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
              Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

      @Override
      public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
          int dend) {
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
          return "";
        }
        return null;
      }
    };
  }

  public static void addDivider2TabLayout(TabLayout tabLayout) {
    BaseApp app = App.Instance();
    try {
      LinearLayout tabStrip = ReflectUtils.getFieldValue(tabLayout, "mTabStrip");
      tabStrip.setDividerPadding(AndroidUtils.dp2px(app, 13));
      tabStrip.setDividerDrawable(app.getResources().getDrawable(R.drawable.divider));
      tabStrip.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    } catch (NoSuchFieldException e) {
      Ln.e(e);
    } catch (IllegalAccessException e) {
      Ln.e(e);
    }
  }

  /**
   * 把标题字体加粗、颜色白色
   *
   * @param titleView TextView
   */
  public static void updateTitleText(TextView titleView) {
    titleView.setTextColor(Color.WHITE);
    titleView.getPaint().setFakeBoldText(true);
    titleView.setMaxEms(10);
  }

  /***
   * 给toolbar右边增加一个按钮
   *
   * @param toolbar Toolbar
   * @param inflater LayoutInflater
   * @param btnText 按钮上的字
   * @return 按钮TextView
   */
  public static TextView addBtn2ToolbarRight(Toolbar toolbar, LayoutInflater inflater,
      String btnText) {
    TextView btn = (TextView) inflater.inflate(R.layout.view_toolbar_right_text, toolbar, false);
    ((Toolbar.LayoutParams) btn.getLayoutParams()).gravity = Gravity.END;
    btn.setText(btnText);
    toolbar.addView(btn);
    return btn;
  }

  /***
   * 给toolbar右边增加一个按钮
   *
   * @param toolbar Toolbar
   * @param inflater LayoutInflater
   * @param btnText 按钮上的字
   * @return 按钮TextView
   */
  public static TextView addBtn2ToolbarRight(Toolbar toolbar, LayoutInflater inflater,
      @StringRes int btnText) {
    TextView btn = (TextView) inflater.inflate(R.layout.view_toolbar_right_text, toolbar, false);
    ((Toolbar.LayoutParams) btn.getLayoutParams()).gravity = Gravity.END;
    btn.setText(btnText);
    toolbar.addView(btn);
    return btn;
  }

  /**
   * 获取一个数据为空时的view，样式见: view_empty.xml
   *
   * @param inflater LayoutInflater
   * @param parent ViewGroup
   * @param desc 文字描述
   * @param resId 图片
   */
  public static View getEmptyView(LayoutInflater inflater, ViewGroup parent, String desc,
      @DrawableRes int resId) {
    View emptyView = inflater.inflate(R.layout.view_empty, parent, false);
    TextView text = (TextView) emptyView.findViewById(R.id.desc);
    text.setText(desc);
    ImageView image = (ImageView) emptyView.findViewById(R.id.empty);
    image.setImageResource(resId);
    return emptyView;
  }

  /**
   * 获取一个数据为空时的view，样式见: view_empty.xml
   *
   * @param inflater LayoutInflater
   * @param parent ViewGroup
   * @param descResId 文字描述
   * @param resId 图片
   */
  public static View getEmptyView(LayoutInflater inflater, ViewGroup parent,
      @StringRes int descResId, @DrawableRes int resId) {
    View emptyView = inflater.inflate(R.layout.view_empty, parent, false);
    TextView text = (TextView) emptyView.findViewById(R.id.desc);
    text.setText(descResId);
    ImageView image = (ImageView) emptyView.findViewById(R.id.empty);
    image.setImageResource(resId);
    return emptyView;
  }

  public static void setRecyclerViewLoadMore(Context context,
      final BaseRecyclerViewAdapter mAdapter, RecyclerView recyclerView) {
    LayoutInflater inflater = LayoutInflater.from(context);
    mAdapter.setLoadMoreView(inflater.inflate(R.layout.item_load_more, recyclerView, false));
    View loadMoreFail = inflater.inflate(R.layout.item_load_more_failure, recyclerView, false);
    loadMoreFail.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mAdapter.reloadMore();
      }
    });
    mAdapter.setLoadMoreFailureView(loadMoreFail);
  }

  /**
   * 保存图片对话框
   *
   * @param activity {@link Activity}
   * @param url 图片路径
   * @return {@link Dialog}
   */
  public static DialogPlus savePicture2Local(final Activity activity, final String url) {
    CenterDialog centerDialog = new CenterDialog.Builder(activity).footer()
        .build()
        .setContent("保存该图片到本地?")
        .setNegative("取消", new IDialog.OnClickListener() {
          @Override public void onClick(DialogPlus dialog, View view) {
            dialog.dismiss();
          }
        })
        .setPositive("确定", new IDialog.OnClickListener() {
          @Override public void onClick(DialogPlus dialog, View view) {
            Observable.just(url).subscribeOn(Schedulers.io()).map(new Function<String, File>() {
              @Override public File apply(String s) throws Exception {
                File dest = AndroidUtils.getTmpFile();
                File file = Glide.with(activity)
                    .load(s)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
                FileUtils.copyFile(file, dest);
                return dest;
              }
            }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<File>() {
              @Override public void accept(@NonNull File file) throws Exception {
                activity.sendBroadcast(
                    new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
                Toost.message("该图片已被存至" + file.getPath());
              }
            }, new Consumer<Throwable>() {
              @Override public void accept(@NonNull Throwable throwable) throws Exception {
                Toost.message("保存图片失败, 请检查网络连接是否正常!");
              }
            });
            dialog.dismiss();
          }
        })
        .show();
    return centerDialog.getDialog();
  }
  /**
   *                 activity.sendBroadcast(
   new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
   Toost.message("该图片已被存至" + file.getPath());

   Toost.message("保存图片失败, 请检查网络连接是否正常!");

   */
}
