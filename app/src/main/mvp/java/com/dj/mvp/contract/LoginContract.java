package com.dj.mvp.contract;

import com.dj.mvp.presenter.BasePresenter;
import com.dj.mvp.view.BaseView;

/**
 * Created by wangjing4 on 2017/8/28.
 */

public interface LoginContract {

    interface Presenter extends BasePresenter {
        void login();
        void reset();
    }

    interface View extends BaseView<Presenter> {
        String getUserEmail();
        String getPassword();

        boolean isEmailValid(String email);
        boolean isPasswordValid(String password);

        boolean setEmailError(String error);
        boolean setPasswordError(String error);

        void showLoginProgress(boolean show);
        void resetEditView();
        void toMainAct();
        void showFailedError();
    }
}
