package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credential findCredential(Integer credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credential> findCredentials(Integer userid);

   @Insert("INSERT INTO CREDENTIALS (url, username, password, key, userid) VALUES (#{url}, #{username}, #{password}, #{key}, #{userid})")
   @Options(useGeneratedKeys = true, keyProperty = "credentialid")
   Integer insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET username = #{username}, password = #{password}, url = #{url}, key = #{key} WHERE credentialid = #{credentialid}")
    Integer update(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void deleteCredential(Integer credentialid);
}
