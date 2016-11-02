package com.example.testandroid.mvp;

public interface MvpContract {

	public interface TestMvpPresenter extends BasePresenter{
		public void getTitle();
	}
	
	public interface TestMvpView extends BaseView{
		public void updateTitle(String title);
	}
}
