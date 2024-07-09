package com.example.myapplication.view_model;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.model.UserInfo;

public class UserViewModel extends ViewModel{
    private MutableLiveData<UserInfo> userInfo=userInfo = new MutableLiveData<>();

    public LiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo.setValue(userInfo);
    }

}
