package com.example.Student.service.impl;

import com.example.Student.api.request.StudentRequest;
import com.example.Student.api.request.StudentUpdateRequest;
import com.example.Student.api.request.UpdatePasswordRequest;
import com.example.Student.model.Student;
import com.example.Student.api.request.SetPasswordRequest;
import com.example.Student.service.OktaUserService;
import com.okta.sdk.client.Client;
import com.okta.sdk.resource.user.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.BadRequestException;

@Service
public class OktaUserServiceImpl implements OktaUserService {

  private final Client client;

  @Value("${okta.usersGroupId}")
  private String groupId;

  private final String NOT_UPDATED = "Okta User did not update";

  public OktaUserServiceImpl(Client client) {
    this.client = client;
  }

  @Override
  public String createUser(StudentRequest studentRequest) {
    char[] passwordChar = studentRequest.getPassword().toCharArray();

    User oktaUser = UserBuilder.instance()
            .setEmail(studentRequest.getEmail())
            .setFirstName(studentRequest.getFirstName())
            .setLastName(studentRequest.getLastName())
            .setPassword(passwordChar)
            .addGroup(groupId)
            .setActive(true)
            .buildAndCreate(client);

    return oktaUser.getId();
  }

  @Override
  public void updateStudentOktaProfile(Student student, StudentUpdateRequest studentRequest) {

    User oktaUser = getOktaUser(student.getExternalId());
    oktaUser.getProfile().setLogin(studentRequest.getEmail());
    oktaUser.getProfile().setEmail(studentRequest.getEmail());
    oktaUser.getProfile().setFirstName(studentRequest.getFirstName());
    oktaUser.getProfile().setLastName(studentRequest.getLastName());

    User updatedUser = oktaUser.update();

    if (!checkOktaProfileUpdated(updatedUser.getProfile(), studentRequest)) {
      throw new BadRequestException(NOT_UPDATED);
    }

  }

  @Override
  public void changePassword(Student student, UpdatePasswordRequest updatePasswordRequest) {

    User oktaUser = getOktaUser(student.getExternalId());

    ChangePasswordRequest oktaChangePasswordRequest = client.instantiate(ChangePasswordRequest.class);

    PasswordCredential oldPasswordCredential = client.instantiate(PasswordCredential.class);
    oldPasswordCredential.setValue(updatePasswordRequest.getOldPassword().toCharArray());
    oktaChangePasswordRequest.setOldPassword(oldPasswordCredential);

    PasswordCredential newPasswordCredential = client.instantiate(PasswordCredential.class);
    newPasswordCredential.setValue(updatePasswordRequest.getNewPassword().toCharArray());
    oktaChangePasswordRequest.setNewPassword(newPasswordCredential);

    oktaUser.changePassword(oktaChangePasswordRequest);

  }

  @Override
  public void setPassword(Student student, SetPasswordRequest setPasswordRequest) {

    User oktaUser = getOktaUser(student.getExternalId());
    PasswordCredential passwordCredential = oktaUser.getCredentials().getPassword();
    char[] password = setPasswordRequest.getPassword().toCharArray();
    passwordCredential.setValue(password);

    oktaUser.update();
  }

  public User getOktaUser(String externalId) {

    return client.getUser(externalId);
  }

  private Boolean checkOktaProfileUpdated(UserProfile profile, StudentUpdateRequest request) {

    return profile.getEmail().equals(request.getEmail())
            && profile.getFirstName().equals(request.getFirstName())
            && profile.getLastName().equals(request.getLastName());
  }
}
