package com.example.pulllist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MListView extends ListView {



		public MListView(Context context) {
		super(context);
		init();
	}

		public MListView(Context context, AttributeSet attrs, int defStyleAttr) {
			super(context, attrs, defStyleAttr);
			init();
		}

		public MListView(Context context, AttributeSet attrs) {
			super(context, attrs);
			init();
		}

		@Override
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2
	                , MeasureSpec.AT_MOST);
	        super.onMeasure(widthMeasureSpec, expandSpec);
	    }
		private void init() {
		}

}
		
		
		
		
		
		
		
		
