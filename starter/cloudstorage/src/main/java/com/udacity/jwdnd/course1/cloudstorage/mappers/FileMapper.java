package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File findfile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{fileId}")
    List<File> findfiles(Integer userid);

    @Insert("INSERT INTO FILES (filename, contenttype, fizesize, filedata, userid) VALUES (#{filename}, )")
    @Options(useGeneratedKeys = true , keyProperty = "fileId")
    Integer insert(File file);

    @Update("UPDATE FILES SET  WHERE fileId = #{fileId}")
    Integer update(File file);

    @Delete("DELETE * FROM FILES WHERE fileId = #{fileId}")
    void deletefile(Integer fileId);
}
