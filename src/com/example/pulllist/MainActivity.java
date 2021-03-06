package com.example.pulllist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private PopupWindow popupWindow;
	private View parent;
	private int[] locations;
	private DataAdapter subjectAdapter;
	private DataAdapter menuAdapter;
	private View view;
	private ZuNiScrollView zns;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parent = getLayoutInflater().inflate(R.layout.activity_main, null);
		setContentView(parent);
		findViewById(R.id.ll_select_subject).setOnClickListener(this);
		findViewById(R.id.ll_select_subject2).setOnClickListener(this);
		zns = (ZuNiScrollView) findViewById(R.id.zns);
	}

	@Override
	public void onClick(View v) {
		if (popupWindow == null) {

			initPopuWindow(v);
		}
		Animation turnOn = AnimationUtils.loadAnimation(getBaseContext(), R.anim.fade_in);
		view.findViewById(R.id.v_lamp).startAnimation(turnOn);
		Animation in = AnimationUtils.loadAnimation(this, R.anim.popupwindow_slide_in_from_top);
		view.findViewById(R.id.ll_lv_sp).startAnimation(in);
		locations = new int[2];
		v.getLocationOnScreen(locations);
		View foldBtn = view.findViewById(R.id.ll_title_sp);
		RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) foldBtn.getLayoutParams();
		lp.leftMargin = locations[0];
		lp.topMargin = locations[1]-getStatusHeight(this);
		foldBtn.setLayoutParams(lp);
		popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, v.getLeft(), locations[1]);

	}

	private void initPopuWindow(View v) {
		view = getLayoutInflater().inflate(R.layout.select_choice, null);
		initListView(view);
		popupWindow = new PopupWindow(view, -1, -1);

		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);

		view.findViewById(R.id.v_lamp).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				exitAnim();
			}

		});
		view.findViewById(R.id.ll_lv_sp).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
	}
	private void exitAnim() {

        Animation turnOff = AnimationUtils.loadAnimation(this,R.anim.fade_out);
        view.findViewById(R.id.v_lamp).startAnimation(turnOff);
        Animation out = AnimationUtils.loadAnimation(this,R.anim.popupwindow_slide_out_to_top);
        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
            	popupWindow.dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.findViewById(R.id.ll_lv_sp).startAnimation(out);
    
		
	}

	private void initListView(View layout) {
		ListView menuLv = (ListView) layout.findViewById(R.id.lv_menu);
		ListView subjectLv = (ListView) layout.findViewById(R.id.lv_subject);
		menuAdapter = new DataAdapter(this,0);
		menuAdapter.setData(generateData());
		menuLv.setAdapter(menuAdapter);
//		menuLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//				menuAdapter.checked(i);
//				subjectAdapter.setData(allocateSubject());
//				subjectAdapter.notifyDataSetChanged();
//			}
//		});
		subjectAdapter = new DataAdapter(this,1);
		subjectAdapter.checked(-1);
		subjectAdapter.setData(allocateSubject());
		subjectLv.setAdapter(subjectAdapter);
	}

	private List<String> generateData() {
		// init test data
		List<String> mList = new ArrayList<String>();
		String g = "小学";
		mList.add(g);

		g = "初一";
		mList.add(g);

		g = "初二";
		mList.add(g);

		g = "初三";
		mList.add(g);

		g = "高一";
		mList.add(g);

		g = "高二";
		mList.add(g);

		g = "高三";
		mList.add(g);
		return mList;
	}

	private List<String> allocateSubject() {
		List<String> mList = new ArrayList<String>();
		String g = "语文";
		mList.add(g);

		g = "数学";
		mList.add(g);

		g = "英语";
		mList.add(g);

		g = "化学";
		mList.add(g);

		g = "政治";
		mList.add(g);

		g = "物理";
		mList.add(g);

		g = "英语";
		mList.add(g);
		for (int i = 0; i < 3; i++) {
			Random random = new Random();
			int d = random.nextInt(7 - i);
			mList.remove(d);
		}
		return mList;
	}
	public static int getStatusHeight(Activity activity) { 
		int statusHeight = 0; 
		Rect localRect = new Rect(); 
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect); 
		statusHeight = localRect.top; 
		if (0 == statusHeight) { 
		Class<?> localClass; 
		try { 
		localClass = Class.forName("com.android.internal.R$dimen"); 
		Object localObject = localClass.newInstance(); 
		int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString()); 
		statusHeight = activity.getResources().getDimensionPixelSize(i5); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		} 
		return statusHeight; 
		}
	
	 class DataAdapter extends BaseAdapter{
		    private List<String> mList;
		    private LayoutInflater inflater;
		    private int markPosition = 0;
		    private int type = -1;
		    public DataAdapter(Context context,int type) {
		        inflater = LayoutInflater.from(context);
		        mList = new ArrayList<String>();
		        this.type = type;
		    }

		    public void setData(List<String> mList){
		        if(mList == null){
		            this.mList.clear();
		        }else{
		            this.mList = mList;
		        }
		    }

		    @Override
		    public int getCount() {
		        return mList.size();
		    }

		    @Override
		    public Object getItem(int i) {
		        return mList.get(i);
		    }

		    @Override
		    public long getItemId(int i) {
		        return i;
		    }

		    @Override
		    public View getView(final int position, View convertView, ViewGroup parent) {
		        ViewHolder holder = null;
		        if(convertView == null){
		            holder = new ViewHolder();
		            convertView = inflater.inflate(R.layout.item_menu, null);
		            holder.mark = convertView.findViewById(R.id.v_line_vertical);
		            holder.znhs = (ViewGroup) convertView.findViewById(R.id.znhs);
		            holder.name = (TextView)convertView.findViewById(R.id.tv_name);
		            convertView.setTag(holder);
		        }else{
		            holder = (ViewHolder)convertView.getTag();
		        }
		        if (type==0) {
		        	 holder.znhs.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						checked(position);
						subjectAdapter.setData(allocateSubject());
						subjectAdapter.notifyDataSetChanged();
					}
				});
		        }
		        String s = mList.get(position);

		        holder.name.setText(s);
		        if(position == markPosition){
		            holder.name.setEnabled(true);
		            holder.mark.setVisibility(View.VISIBLE);
		        }else{
		            holder.name.setEnabled(false);
		            holder.mark.setVisibility(View.GONE);
		        }

		        return convertView;
		    }

		    class ViewHolder{
		         ViewGroup znhs;
				View mark;
		        TextView name;
		    }

		    public void checked(int markPosition){
		        this.markPosition = markPosition;
		        if(markPosition>=0 && markPosition < mList.size()){
		            notifyDataSetChanged();
		        }
		    }
}
}