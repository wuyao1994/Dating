package com.dating.base;

import com.dating.constants.UserResultConstant;

public class UserResult extends BaseResult{
    public UserResult(UserResultConstant userResultConstant, Object data) {
        super(userResultConstant.code, userResultConstant.message, data);
    }
}
