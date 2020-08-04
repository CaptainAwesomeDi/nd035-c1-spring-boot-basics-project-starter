package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int createOrUpdate(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setPassword(encryptedPassword);
        credential.setKey(encodedKey);

        if (credential.getCredentialid() != null) return this.credentialMapper.update(credential);
        else return this.credentialMapper.insert(credential);
    }

    public List<Credential> getUserCredentials(User user){
        return this.credentialMapper.findCredentials(user.getUserid());
    }

//    public Credential showOne(Integer credentialid){
//        Credential cred = this.credentialMapper.findCredential(credentialid);
//        String decryptedValue = this.encryptionService.decryptValue(cred.getPassword(),cred.getKey());
//        cred.setPassword(decryptedValue);
//        return cred;
//    }

    public void deleteCredential(Integer credentialid) {
        this.credentialMapper.deleteCredential(credentialid);
    }
}
