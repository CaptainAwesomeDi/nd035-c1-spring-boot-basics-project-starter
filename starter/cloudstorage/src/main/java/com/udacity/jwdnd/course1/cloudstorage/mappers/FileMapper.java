package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findfile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<File> findfiles(Integer userid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File findFileByName(String filename);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, filedata, userid) VALUES (#{filename}, #{contenttype}, #{filesize}, #{filedata}, #{userid} )")
    @Options(useGeneratedKeys = true , keyProperty = "fileId")
    Integer insert(File file);

    @Update("UPDATE FILES SET  WHERE fileId = #{fileId}")
    Integer update(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void deletefile(Integer fileId);
}
