4.19 18:29 问题：添加swipe删除按钮的监听事件，删除数据库记录完成后不能动态刷新listview
		   解决：对adapter注册DataSetObserver观察者监听重写onChange方法进行重新绘制，在删除按钮的onClick方法中调用notifyDataSetChanged方法，抛出dataChanged事件。

4.20 19:32 问题：点击swipe删除按钮后数据库中删除的总是最后一条记录
		   解决：在dbWriter删除之前用moveToPosition方法，这样从cursor中取到的ID值才是本Item对应的。

4.20 22:03 问题：toolbar左侧menu按钮无法设置
		   解决：默认id为home，默认图标为左箭头可使用

5.11 22:40 问题：备忘录列表不是按时间排序
		   解决： cursor查询方法最后一个参数是排序参数		 