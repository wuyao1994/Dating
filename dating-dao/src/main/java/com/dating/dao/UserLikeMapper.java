package com.dating.dao;

import com.dating.domain.UserLike;
import com.dating.domain.UserLikeExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserLikeMapper {
    long countByExample(UserLikeExample example);

    int deleteByExample(UserLikeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserLike record);

    int insertSelective(UserLike record);

    List<UserLike> selectByExample(UserLikeExample example);

    UserLike selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserLike record, @Param("example") UserLikeExample example);

    int updateByExample(@Param("record") UserLike record, @Param("example") UserLikeExample example);

    int updateByPrimaryKeySelective(UserLike record);

    int updateByPrimaryKey(UserLike record);
}