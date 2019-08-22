package com.example.wp.awesomemmz.skill;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.TextView;

import com.example.wp.awesomemmz.R;
import com.example.wp.resource.utils.rich.ImageTextUtil;

public class RichTextActivity extends AppCompatActivity {
	
	public final static String HTML_TEXT =
			"<p><font size=\"3\" color=\"red\">设置了字号和颜色</font></p>" +
					"<b><font size=\"5\" color=\"blue\">设置字体加粗 蓝色 5号</font></font></b></br>" +
					"<h1>这个是H1标签</h1></br>" +
					"<p>这里显示图片：</p><img src=\"https://img0.pconline.com.cn/pconline/1808/06/11566885_13b_thumb.jpg\"";
	
	private final String rich2 = "1shddfhd111" +
			"<img src=\"http://img.t.sinajs.cn/t4/appstyle/expression/ext/normal/e2/zhajibeer_thumb.gif\" height=\"40\" width=\"40\">";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().setEnterTransition(new Explode().setDuration(400));
			getWindow().setExitTransition(new Explode().setDuration(400));
		}
		
		setContentView(R.layout.activity_rich_text);
		
		TextView tvRich1 = findViewById(R.id.tvRich1);
		TextView tvRich2 = findViewById(R.id.tvRich2);
		
		ImageTextUtil.setImageText(tvRich1, HTML_TEXT);
		ImageTextUtil.setImageText(tvRich2, rich2);
	}
}
