package com.dj.mvvm;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.view.View;
import android.widget.Toast;

import com.dj.collection.BaseActivity;
import com.dj.collection.R;
import com.dj.collection.databinding.ActivityMvvmBinding;
import com.dj.mvvm.bean.StudentBean;

/**
 * Created by wangjing4 on 2017/8/28.
 */

public class MvvmActivity extends BaseActivity {
    private ActivityMvvmBinding binding;
    StudentBean student = new StudentBean("Hensen", "handsome");
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm);
        binding.setStudent(student);
        binding.setController(new Controller());
    }

    public class Controller {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            student.setName(s.toString());
            binding.setStudent(student);
        }

        public void onClick(View view) {
            Toast.makeText(MvvmActivity.this, "clickMe", Toast.LENGTH_SHORT).show();
        }

        public void onClickListenerBinding(StudentBean student) {
            Toast.makeText(MvvmActivity.this, student.getNickName(), Toast.LENGTH_SHORT).show();
        }
    }
}
